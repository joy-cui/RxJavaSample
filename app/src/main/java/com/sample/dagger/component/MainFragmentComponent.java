package com.sample.dagger.component;

import com.sample.dagger.view.MainFragment;
import com.sample.dagger.scope.PerActivity;

import dagger.Subcomponent;

/**
 * Created by cui on 2018/8/23.
 */

@Subcomponent
public interface MainFragmentComponent {

    void inject(MainFragment mainFragment);
}
