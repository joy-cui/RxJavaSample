package com.sample.dagger.modules;

import com.sample.data.GetUserData;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cui on 2018/8/23.
 */
@Module
public class MainModule {

    @Provides
    public GetUserData provideUserData(){
        return new GetUserData();
    }
}
