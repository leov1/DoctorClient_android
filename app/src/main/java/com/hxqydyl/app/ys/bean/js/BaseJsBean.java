package com.hxqydyl.app.ys.bean.js;

import android.support.v4.app.FragmentActivity;

import com.hxqydyl.app.ys.activity.BaseWebActivity;

/**
 * Created by wangxu on 2016/4/11.
 */
public abstract class BaseJsBean {
    public abstract void doJs(BaseWebActivity activity,String functionname,String  parameters);
    public static String getPackgeName(String s){
        return "com.hxqydyl.app.ys.bean.js."+s;
    }
}
