package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 注册第一步
 * Created by hxq on 2016/3/18.
 */
public class RegisterFirstNet {

    private OnRegisterFirstListener listener;

    public void setListener(OnRegisterFirstListener listener) {
        this.listener = listener;
    }

    public interface OnRegisterFirstListener {
        void requestRegisterFirstNetSuccess(RegisterFirst query);

        void requestRegisterFirstNetFail();
    }

    public void registerFirst(String mobile, String password, String captcha) {

        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.REGISTER_ONE))
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("captcha", captcha)
                .addParams("callback", UrlConstants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestRegisterFirstNetFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            listener.requestRegisterFirstNetSuccess(JsonUtils.JsonRegisterFirst(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
