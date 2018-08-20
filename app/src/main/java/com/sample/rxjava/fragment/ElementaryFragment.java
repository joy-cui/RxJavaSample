package com.sample.rxjava.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sample.rxjava.R;
import com.sample.rxjava.adapter.ElementListAdapter;
import com.sample.rxjava.entry.Image;
import com.sample.rxjava.network.NetworkUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by cui on 2018/8/17.
 */

public class ElementaryFragment extends BaseFragment {
    private String TAG="ElementaryFragment";
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.gridRv) RecyclerView gridRv;

    ElementListAdapter adapter = new ElementListAdapter();


    @OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
    void onTagChecked(RadioButton searchRb, boolean checked) {
        if (checked) {
//            Log.e(TAG,"onTagChecked....");
            unsubscribe();
            adapter.setImages(null);//清空
            swipeRefreshLayout.setRefreshing(true);
            search(searchRb.getText().toString());
        }
    }

    private void search(String key) {
        //原始数据
//        Call<ResponseBody> call=NetworkUtil.getImageApi().search2(key);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                   Log.e(TAG,"search:"+response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });


        disposable = NetworkUtil.getImageApi()
                .search(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Image>>() {
                    @Override
                    public void accept(@NonNull List<Image> images) throws Exception {
//                        Log.e(TAG,"search....数据加载成功");
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.setImages(images);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG,"search....数据加载失败");
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }
                });

    //采用 map 操作符进行网络数据解析
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                Request.Builder builder = new Request.Builder()
                        .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                        .get();
                Request request = builder.build();
                 OkHttpClient okHttpClient = new OkHttpClient();
                Call call =okHttpClient.newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, String>() {
            @Override
            public String apply(@NonNull Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody body = (ResponseBody) response.body();
                    if (body != null) {
                        String bodyStr=response.body().string();
                        Log.e(TAG, "map:转换前:" + bodyStr);
//                        return new Gson().fromJson(body.string(), MobileAddress.class);
                        return bodyStr;
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "doOnNext: 保存成功：" + s + "\n");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String data) throws Exception {
                        Log.e(TAG, "成功:" + data + "\n");
                    }
                    });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView....");
        View view = inflater.inflate(R.layout.fragment_elementary, container, false);
        ButterKnife.bind(this, view);

        gridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        //默认值
        swipeRefreshLayout.setRefreshing(true);
        search("可爱");
        return view;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.dialog_elementary;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_elementary;
    }
}
