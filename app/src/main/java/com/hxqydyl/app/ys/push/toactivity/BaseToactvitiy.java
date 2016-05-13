package com.hxqydyl.app.ys.push.toactivity;

import android.content.Context;
import android.content.Intent;

import com.hxqydyl.app.ys.push.bean.BasePushBean;

/**
 * Created by wangxu on 2016/5/12.
 */
public abstract class BaseToactvitiy {
    protected abstract Class setActivity();
    protected abstract int setFlag();
    public void toActivity(Context context,String json) {
        Intent i = new Intent(context, setActivity());
        i.setFlags(setFlag());
        context.startActivity(i);
    }
}