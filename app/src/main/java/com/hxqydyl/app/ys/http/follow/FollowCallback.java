package com.hxqydyl.app.ys.http.follow;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by wangchao36 on 16/4/3.
 */
public class FollowCallback extends StringCallback {

    private BaseTitleActivity baseTitleActivity;

    public FollowCallback(BaseTitleActivity baseTitleActivity) {
        this.baseTitleActivity = baseTitleActivity;
    }

    @Override
    public void onError(Call call, Exception e) {
        Log.e("xx", e.getMessage());
        e.printStackTrace();
        baseTitleActivity.dismissDialog();
        onFail("999999", "请求出错啦，重新刷新下吧");
    }

    @Override
    public void onResponse(String response) {
        try{  JSONObject object = JSONObject.parseObject(response);
            JSONObject queryObj = object.getJSONObject("query");
            String status = queryObj.getString("success");
            if ("1".equals(status)) {
                String relList = object.getString("relist");
                onResult(relList);
            } else {
                String msg = queryObj.getString("message");
                Log.e("doctorClient", msg);
                UIHelper.ToastMessage(baseTitleActivity, msg);
                baseTitleActivity.dismissDialog();
                onFail(status, msg);
            }}catch (Exception e){
            onFail("999999", "解析出错啦，重新刷新下吧");        }

    }

    public void onResult(String result) {
        // 各业务实现
    }

    public void onFail(String status, String msg) {
        // 状态码非成功的处理
    }

}
