package com.hxqydyl.app.ys.http.sendMsg;

import com.hxqydyl.app.ys.bean.article.ArticleResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by hxq on 2016/4/5.
 */
public class PatientGroupNet {

    private OnPatientGroupListener listener;

    public void setListener(OnPatientGroupListener listener) {
        this.listener = listener;
    }

    public interface OnPatientGroupListener{
        void patientGroupSuc(ArticleResult articleResult);
        void patientGroupFail();
    }

    public void getPatientGroup(String doctorUuid) {
        OkHttpUtils.get().url(UrlConstants.getWholeApiUrl(UrlConstants.GET_PATIENT_GROUP))
                .addParams("doctorUuid", "6d3f252bc13e432f9fdc8a81a2ff425a")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                System.out.println("onError--->" + LoginManager.getDoctorUuid());
                listener.patientGroupFail();
            }

            @Override
            public void onResponse(String str) {
                System.out.println("doctorUuid--->" + str);
                try {
                    listener.patientGroupSuc(JsonUtils.JsonArticleResult(str));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
