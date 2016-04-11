package com.hxqydyl.app.ys.bean.js;

import android.support.v4.app.FragmentActivity;

/**
 * Created by wangxu on 2016/4/11.
 */
public abstract class BaseJsBean {
    public abstract void doJs(FragmentActivity activity,String functionname,String  parameters);
    public static String getPackgeName(String s){
        return "com.hxqydyl.app.ys.bean.js."+s;
    }
}
