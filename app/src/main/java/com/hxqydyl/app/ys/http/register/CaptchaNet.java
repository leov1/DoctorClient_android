package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

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
        System.out.println("request--->");
        OkHttpUtils
                .get()
                .url(Constants.GET_VERIFICATION_CODE)
                .addParams("mobile", mobile)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.println("onError--->");
                        listener.requestCaptchaNetFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("request--->"+response);
                        try {
                            listener.requestCaptchaNetSuc(JsonUtils.JsonCaptchResult(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

    }
}
