package com.sample.dagger.component;

import android.content.Context;

import com.sample.dagger.modules.AppModule;
import com.sample.dagger.util.Navigator;
import com.sample.dagger.util.ToastUtil;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cui on 2018/8/21.
 */
@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
    Context getContext();

    Navigator getNavigator();
    ToastUtil getToastUtil();
}
