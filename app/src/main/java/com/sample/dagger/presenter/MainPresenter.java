package com.sample.dagger.presenter;

import com.sample.data.GetUserData;
import com.sample.data.UserData;

import javax.inject.Inject;

/**
 * Created by cui on 2018/8/23.
 */

public class MainPresenter {
    public GetUserData mUserData;
    private IUserView mUserView;



    @Inject
    public MainPresenter(GetUserData userData){
        this.mUserData = userData;
    }

    public void getUser(){
        UserData userData = this.mUserData.getUser();
        this.mUserView.setUserName(userData.mUserName);
    }
    public void setUserView(IUserView userView){
        this.mUserView = userView;
    }

    public static interface IUserView{
        void setUserName(String name);
    }
}
