package com.hxqydyl.app.ys.push.toactivity;

import com.hxqydyl.app.ys.push.bean.BasePushBean;
import com.hxqydyl.app.ys.push.bean.TestPushBean;

/**
 * Created by wangxu on 2016/5/13.
 */
public enum PushType {
    Test(TestPushBean.class,"1","ToTestActivity");

     PushType(Class bean, String id,String toActivity) {
        this.bean = bean;
        this.id = id;
        this.toActivity=toActivity;
    }

    private Class<TestPushBean> bean;
    private String id;
    private String toActivity;

    public String getToActivity() {
        return toActivity;
    }

    public void setToActivity(String toActivity) {
        this.toActivity = toActivity;
    }

    public Class<TestPushBean> getBean() {
        return bean;
    }

    public void setBean(Class<TestPushBean> bean) {
        this.bean = bean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    }
