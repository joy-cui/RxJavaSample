package com.sample;

import android.app.Application;

import com.sample.dagger.component.AppComponent;
//import com.sample.dagger.component.DaggerAppComponent;
import com.sample.dagger.component.DaggerAppComponent;
import com.sample.dagger.modules.AppModule;

/**
 * Created by cui on 2018/8/17.
 */

public class AppApplication extends Application {

    private static AppApplication INSTANCE;
    AppComponent mAppComponent;

    public static AppApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
