package com.sample.rxjava.network;

import com.sample.rxjava.network.api.FakeApi;
import com.sample.rxjava.network.api.ImageAPI;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cui on 2018/8/17.
 */

public class NetworkUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static ImageAPI image=null;
    private static FakeApi fakeApi=null;
    public static ImageAPI getImageApi(){
        if (image == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            image = retrofit.create(ImageAPI.class);
        }
        return image;

    }
    public static FakeApi getTokenApi(){
        if (fakeApi == null) {
            fakeApi = new FakeApi();
        }
        return fakeApi;
    }

}
