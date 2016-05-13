package com.hxqydyl.app.ys.push.listener;

import com.hxqydyl.app.ys.push.bean.BasePushBean;
import com.hxqydyl.app.ys.push.bean.TestPushBean;

/**
 * Created by wangxu on 2016/5/13.
 */
public class TestPushListener extends BasePushListener{
    @Override
    public BasePushBean setReciveBean(String beanJson) {
        return gson.fromJson(beanJson, TestPushBean.class);
    }

    @Override
    public String getAction() {
        return "TestPushListener";
    }

    @Override
    public String getBeanName() {
        return "TestPushBean";
    }
}
