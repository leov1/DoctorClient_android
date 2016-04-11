package com.hxqydyl.app.ys.http.follow;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by wangchao36 on 16/4/3.
 */
public class FollowCallback extends StringCallback {

    @Override
    public void onError(Call call, Exception e) {
        Log.e("xx", e.getMessage());
        e.printStackTrace();
        onFail("999999", "");
    }

    @Override
    public void onResponse(String response) {
        JSONObject object = JSONObject.parseObject(response);
        JSONObject queryObj = object.getJSONObject("query");
        String status = queryObj.getString("success");
        if ("1".equals(status)) {
            String relList = object.getString("relist");
            onResult(relList);
        } else {
            String msg = queryObj.getString("message");
            Log.e("doctorClient", msg);
            onFail(status, msg);
        }
    }

    public void onResult(String result) {
        // 各业务实现
    }

    public void onFail(String status, String msg) {
        // 状态码非成功的处理
    }

}
