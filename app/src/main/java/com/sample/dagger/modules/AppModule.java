package com.sample.dagger.modules;

import android.content.Context;

import com.sample.dagger.util.Navigator;
import com.sample.dagger.util.ToastUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cui on 2018/8/21.
 */
@Module
public class AppModule {

    Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides @Singleton
    public Navigator provideNavigator(){
        return new Navigator();
    }

    @Provides @Singleton
    public ToastUtil provideToastUtil(){
        return new ToastUtil(context);
    }
}
