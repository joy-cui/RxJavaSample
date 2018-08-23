package com.sample.dagger.view;

import android.net.Uri;
import android.os.Bundle;

import com.sample.dagger.component.DaggerMainComponent;
import com.sample.dagger.component.MainComponent;
import com.sample.dagger.modules.ActivityModule;
import com.sample.dagger.modules.MainModule;
import com.sample.dagger.view.base.BaseActivity;
import com.sample.rxjava.R;

/**
 * Created by cui on 2018/8/23.
 */

public class DaggerMainActivity extends BaseActivity implements MainFragment.OnFragmentInteractionListener{

    private MainComponent mMainComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger_activity_main);

        mMainComponent = DaggerMainComponent.builder().appComponent(getAppComponent())
                .mainModule(new MainModule())
                .activityModule(new ActivityModule(this)).build();
//        mMainComponent.inject(this);
    }

    public MainComponent getMainComponent(){
        return mMainComponent;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
