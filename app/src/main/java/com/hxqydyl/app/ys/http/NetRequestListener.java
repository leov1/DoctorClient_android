package com.hxqydyl.app.ys.http;

/**
 * Created by shs.cn on 2016/4/2.
 */
public interface NetRequestListener {
    void onSend(String url);
    void onResponse(String url, Object result);
    void onError(String url, Exception exception);
    void onProgress(String url,Float progress);
}
