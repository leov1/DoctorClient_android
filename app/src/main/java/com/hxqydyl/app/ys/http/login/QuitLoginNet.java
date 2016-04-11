package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 退出登陆
 * Created by hxq on 2016/4/1.
 */
public class QuitLoginNet {

    private OnQuitLoginListener listener;

    public void setListener(OnQuitLoginListener listener) {
        this.listener = listener;
    }

    public interface OnQuitLoginListener {
        void requestQuitSuc(Query query);

        void requestQuitFail();
    }

    public void quit() {
        OkHttpUtils.post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.STAFF_OUT_OF))
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestQuitFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response-quit-->" + response);
                        try {
                            listener.requestQuitSuc(JsonUtils.JsonQuery(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
