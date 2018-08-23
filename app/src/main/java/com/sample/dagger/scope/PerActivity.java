package com.sample.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by cui on 2018/8/23.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)//注意注解范围是Runtime级别
public @interface PerActivity {
}
