package com.sample.data;

import javax.inject.Inject;

/**
 * Created by cui on 2018/8/23.
 */

public class GetUserData {
    @Inject
    public GetUserData(){

    }

    public UserData getUser(){
        UserData data = new UserData();
        data.mUserName = "测试Dagger";
        return data;
    }
}
