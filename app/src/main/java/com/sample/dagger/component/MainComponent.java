package com.sample.dagger.component;

import com.sample.dagger.modules.ActivityModule;
import com.sample.dagger.modules.MainModule;
import com.sample.dagger.scope.PerActivity;
import com.sample.dagger.view.DaggerMainActivity;

import dagger.Component;

/**
 * MainComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by cui on 2018/8/23.
 */

@PerActivity
@Component(dependencies = AppComponent.class,modules = {MainModule.class, ActivityModule.class})
public interface MainComponent extends ActivityComponent{
    //对MainActivity进行依赖注入
    void inject(DaggerMainActivity mainActivity);


    MainFragmentComponent mainFragmentComponent();
}
