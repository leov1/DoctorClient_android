package com.hxqydyl.app.ys.http.commen;

import com.hxqydyl.app.ys.bean.request.BaseRequest;
import com.hxqydyl.app.ys.utils.CommonUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangxu on 2016/4/7.
 */
public class RequestModel <T> {
    public  BaseNetHttp.OnResumeListener onResumeListener;
    public <T> RequestModel(BaseRequest baseRequest, String url,Class<T> t,BaseNetHttp.OnResumeListener onResumeListener) {
        this.url = url;
        this.baseRequest=baseRequest;
        this.t=t;
        this.onResumeListener=onResumeListener;
    }

    public BaseNetHttp.OnResumeListener getOnResumeListener() {
        return onResumeListener;
    }

    public void setOnResumeListener(BaseNetHttp.OnResumeListener onResumeListener) {
        this.onResumeListener = onResumeListener;
    }

    private String url;
    private BaseRequest baseRequest;
    private Class t;

    public Class getT() {
        return t;
    }

    public void setT(Class t) {
        this.t = t;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public LinkedHashMap<String, String> getMaps() throws Exception {
        return CommonUtils.getUtilInstance().objectToMap(baseRequest);
    }
}
