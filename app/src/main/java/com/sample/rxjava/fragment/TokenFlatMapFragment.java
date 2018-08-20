package com.sample.rxjava.fragment;

import android.graphics.Color;
import android.net.Network;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.rxjava.R;
import com.sample.rxjava.entry.TokenEntry;
import com.sample.rxjava.entry.TokenThing;
import com.sample.rxjava.network.NetworkUtil;
import com.sample.rxjava.network.api.FakeApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cui on 2018/8/17.
 */

public class TokenFlatMapFragment extends BaseFragment {
    private String TAG="TokenFlatMapFragment";


    @BindView(R.id.tokenTv) TextView tokenTv;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    final TokenEntry cachedFakeToken = new TokenEntry(true);
    boolean tokenUpdated;

    @OnClick(R.id.invalidateTokenBt)
    void invalidateToken() {
        cachedFakeToken.expired = true;
        Toast.makeText(getActivity(), R.string.token_destroyed, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.requestBt)
    void upload() {
        tokenUpdated = false;
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        final FakeApi fakeApi = NetworkUtil.getTokenApi();
        disposable = Observable.just(1)
                .flatMap(new Function<Object, Observable<TokenThing>>() {
                    @Override
                    public Observable<TokenThing> apply(Object o) {
                        Log.e(TAG,"just11111: "+cachedFakeToken.token);
                        return cachedFakeToken.token == null
                                ? Observable.<TokenThing>error(new NullPointerException("Token is null!"))
                                : fakeApi.getFakeData(cachedFakeToken);
                    }
                })
                .retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Observable<? extends Throwable> observable) {
                        Log.e(TAG,"just apply: "+cachedFakeToken.token);

                        return observable.flatMap(new Function<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> apply(Throwable throwable) {
                                if (throwable instanceof IllegalArgumentException || throwable instanceof NullPointerException) {
                                    return fakeApi.getFakeToken("fake_auth_code")
                                            .doOnNext(new Consumer<TokenEntry>() {
                                                @Override
                                                public void accept(TokenEntry fakeToken) {

                                                    tokenUpdated = true;
                                                    cachedFakeToken.token = fakeToken.token;
                                                    cachedFakeToken.expired = fakeToken.expired;
                                                    Log.e(TAG,"just accept: "+cachedFakeToken.token);
                                                }
                                            });
                                }
                                return Observable.error(throwable);//Consumer
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TokenThing>() {
                    @Override
                    public void accept(TokenThing fakeData) {
                        Log.e(TAG,"subscribe accept: "+cachedFakeToken.token);
                        swipeRefreshLayout.setRefreshing(false);
                        String token = cachedFakeToken.token;
                        if (tokenUpdated) {
                            token += "(" + getString(R.string.updated) + ")";
                        }
                        tokenTv.setText(getString(R.string.got_token_and_data, token, fakeData.id, fakeData.name));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG,"Consumer accept: "+cachedFakeToken.token);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }
                });
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_token_advanced, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    protected int getDialogRes() {
        return R.layout.dialog_token_advanced;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_token_advanced;
    }
}
