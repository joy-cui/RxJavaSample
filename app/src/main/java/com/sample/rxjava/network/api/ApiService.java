package com.sample.rxjava.network.api;

import android.util.Log;
import android.widget.Toast;

import com.sample.rxjava.R;
import com.sample.rxjava.entry.HttpResult;
import com.sample.rxjava.entry.Image;
import com.sample.rxjava.entry.Subject;
import com.sample.rxjava.network.NetworkUtil;
import com.sample.rxjava.subscriber.ProgressSubscriber;

import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
/**
 * Created by cui on 2018/8/20.
 */

/**
 * RXJava2封装
 */
public class ApiService {
    private static RefulAPI SERVICE;
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    private static final String BASE_URL="https://api.douban.com/v2/movie/";//https://api.douban.com/v2/movie/      http://www.zhuangbi.info/


    public ApiService() {
        if (SERVICE == null) {
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            /**
             * 对所有请求添加请求头
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    okhttp3.Response originalResponse = chain.proceed(request);
                    return originalResponse.newBuilder().header("key1", "value1").addHeader("key2", "value2").build();
                }
            });
            SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(RefulAPI.class);

        }

//
//        //手动创建一个OkHttpClient并设置超时时间
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        SERVICE = new Retrofit.Builder()
//                .client(httpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BASE_URL)
//                .build().create(RefulAPI.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final ApiService INSTANCE = new ApiService();

    }

    //获取单例
    public static ApiService getInstance(){

        return SingletonHolder.INSTANCE;
    }

    public void getTopMovie(ProgressSubscriber<List<Subject>> subscriber, int start, int count){
        Log.e("","SubscriberOnNextListener...getLogin :"+count);
        Observable observable=  SERVICE.getTopMovie(start, count)
                .map(new HttpResultFunc<HttpResult<List<Subject>>>());
        toSubscribe(observable,subscriber);
//        SERVICE
//                .getTopMovie(start, count)
//                .map(new HttpResultFunc<HttpResult<List<Subject>>>())
////                .map(new Function<HttpResult<List<Subject>>, List<Subject>>() {
////                    @Override
////                    public List<Subject> apply(HttpResult<List<Subject>> httpResult) throws Exception {
////                        Log.e("","数据加载成功...apply: ");
////                        return httpResult.getSubjects();
////                    }
////                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<Subject>>() {
//                    @Override
//                    public void accept(@NonNull List<Subject> httpResult) throws Exception {
//                        Log.e(TAG,"search....数据加载成功......"+httpResult.get(0).getTitle());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.e(TAG,"search....数据加载失败");
//
//                    }
//                });

    }

    private <T> void toSubscribe(Observable o, ProgressSubscriber s){

        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
    public static class HttpResultFunc<T> implements Function<HttpResult<List<Subject>>, List<Subject>> {
        //这个第一个泛型为接收参数的数据类型，第二个泛型为转换后要发射的数据类型
        @Override
        public List<Subject> apply(HttpResult<List<Subject>> httpResult) throws Exception {
            Log.e(TAG,"search....apply.."+httpResult.getTitle());

            return httpResult.getSubjects();
        }
    }
}
