package com.sample.dagger.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cui on 2018/8/21.
 */

public class ToastUtil {

    private Context mContext;

    public ToastUtil(Context context){
        this.mContext = context;
    }

    public void showToast(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
    }

}
