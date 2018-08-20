package com.sample.rxjava;

import android.app.Application;

/**
 * Created by cui on 2018/8/17.
 */

public class RxJavaApplication extends Application {

    private static RxJavaApplication INSTANCE;

    public static RxJavaApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
