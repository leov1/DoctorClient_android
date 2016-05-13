package com.hxqydyl.app.ys.push.listener;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.push.OnMessageGet;
import com.hxqydyl.app.ys.push.bean.BasePushBean;

/**
 * Created by wangxu on 2016/5/13.
 */
public abstract class BasePushListener {
    public Gson gson=new Gson();
    public abstract BasePushBean setReciveBean(String beanJson);
    public abstract String getAction();
    public abstract String getBeanName();
    public void sendMessage(Context context,String beanJson){
        Intent intent = new Intent();
        intent.setAction(getAction());
        intent.putExtra(getBeanName(),setReciveBean(beanJson));
        context.sendBroadcast(intent);
    }

}
