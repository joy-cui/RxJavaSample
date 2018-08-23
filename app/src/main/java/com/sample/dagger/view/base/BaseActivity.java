package com.sample.dagger.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sample.AppApplication;
import com.sample.dagger.component.AppComponent;

/**
 * Created by cui on 2018/8/23.
 */

public class BaseActivity extends AppCompatActivity {

    public AppComponent getAppComponent(){
        return ((AppApplication)getApplication()).getAppComponent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
