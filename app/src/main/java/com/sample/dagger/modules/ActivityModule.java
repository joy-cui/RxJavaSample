package com.sample.dagger.modules;

import android.app.Activity;

import com.sample.dagger.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cui on 2018/8/23.
 */
@Module
public class ActivityModule {
    private final Activity activity;
    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides @PerActivity
    public Activity provideActivity(){
        return activity;
    }
}
