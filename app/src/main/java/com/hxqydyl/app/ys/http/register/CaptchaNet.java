package com.hxqydyl.app.ys.http.register;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

/**
 * Created by hxq on 2016/3/18.
 */
public class CaptchaNet {

    private OnCaptchaNetListener listener;

    public void setListener(OnCaptchaNetListener listener){
        this.listener = listener;
    }

    public interface OnCaptchaNetListener{
        void requestCaptchaNetSuc(CaptchaResult captchaResult);
        void requestCaptchaNetFail();
    }

    public void obtainCaptcha(String mobile){
        OkHttpClientManager.getAsyn(Constants.GET_VERIFICATION_CODE + "?mobile=" + mobile+"&callback=hxq", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
               listener.requestCaptchaNetFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                listener.requestCaptchaNetSuc(JsonUtils.JsonCaptchaResult(response));
            }
        });
    }
}
