package com.hxqydyl.app.ys.http;

import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;

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
                .build()
                .execute(callback);
    }

    /**
     * 保存
     * @param visitUuid
     * @param callback
     */
    public static void adviceSave(String visitUuid, FollowCallback callback) {
        String json = "";
        OkHttpUtils.postString().content(json)
                .url(FollowApplyNet.baseURL + "mobile/doctor/visit/advice/1.0/save")
                .build()
                .execute(callback);
    }

}
