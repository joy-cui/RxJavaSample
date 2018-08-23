package com.sample.dagger.component;

import android.app.Activity;

import com.sample.dagger.modules.ActivityModule;
import com.sample.dagger.scope.PerActivity;

import dagger.Component;

/**
 * Created by cui on 2018/8/23.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();
}
