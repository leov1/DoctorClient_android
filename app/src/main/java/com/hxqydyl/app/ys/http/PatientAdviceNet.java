package com.hxqydyl.app.ys.http;

import com.hxqydyl.app.ys.bean.Advice;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangchao36 on 16/4/10.
 * 重要医嘱
 */
public class PatientAdviceNet {

    /**
     * 随访表单-重要医嘱-查询
     * @param customerUuid
     * @param callback
     */
    public static void adviceSearch(String customerUuid, FollowCallback callback) {
        String url = FollowApplyNet.baseURL
                + "mobile/doctor/visit/advice/1.0/search/"
                + LoginManager.getDoctorUuid() + "/"
                + customerUuid;
        OkHttpUtils.get().url(url)
                .addHeader("Accept","application/json")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 保存
     * @param json
     * @param callback
     */
    public static void adviceSave(String json, FollowCallback callback)  {
        OkHttpUtils.postString().content(json)
                .addHeader("Accept","application/json")
                .url(FollowApplyNet.baseURL + "mobile/doctor/visit/advice/1.0/save")
                .build()
                .execute(callback);
    }

}
