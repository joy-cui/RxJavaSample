package com.sample.rxjava.network.api;

import com.sample.rxjava.entry.HttpResult;
import com.sample.rxjava.entry.Image;
import com.sample.rxjava.entry.Subject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cui on 2018/8/17.
 */

public interface RefulAPI {
    @GET("search")
    Observable<List<Image>> search(@Query("q") String query);

    @GET("search")
    Call<ResponseBody> search2(@Query("q") String query);

    @GET("/student/login")
    Observable<HttpResult> login(@Query("phone") String phone, @Query("password") String psw);

    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

}
