package com.sample.dagger.util;

import javax.inject.Inject;

/**
 * 该类用来测试多个构造函数同时用@Inject进行标注，看dagger2是否能正常工作
 * Created by cui on 2018/8/23.
 */

public class MultiConstruct {
    @Inject
    MultiConstruct(){

    }
}
