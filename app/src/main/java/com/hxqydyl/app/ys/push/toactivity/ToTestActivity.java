package com.hxqydyl.app.ys.push.toactivity;

import android.content.Intent;

import com.hxqydyl.app.ys.activity.LoginActivity;
import com.hxqydyl.app.ys.push.bean.BasePushBean;

/**
 * Created by wangxu on 2016/5/12.
 */
public class ToTestActivity extends BaseToactvitiy{
    @Override
    protected Class setActivity() {
        return LoginActivity.class;
    }

    @Override
    protected int setFlag() {
        return Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP;
    }



}
