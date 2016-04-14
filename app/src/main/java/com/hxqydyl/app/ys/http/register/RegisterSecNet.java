package com.hxqydyl.app.ys.http.register;

import android.text.TextUtils;

import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 注册第二步
 * Created by hxq on 2016/3/18.
 */
public class RegisterSecNet {

    private OnRegisterSecListener listener;

    public void setListener(OnRegisterSecListener listener) {
        this.listener = listener;
    }

    public interface OnRegisterSecListener {
        void requestRegisterSecSuc(RegisterFirst registerFirst);

        void requestRegisterSecFail();
    }

    public void registerSec(String uuid, String email, String sex, String icon, String doctorName) {
        System.out.println("response---->");
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.REGISTER_TWO))
                .addParams("uuid", uuid)
                .addParams("email", email)
                .addParams("sex", sex)
                .addParams("doctorName", doctorName)
                .addParams("callback", UrlConstants.CALLBACK);
        if (!TextUtils.isEmpty(icon)) {
            postFormBuilder = postFormBuilder.addParams("icon", icon);
        }
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.println("onError---->");
                        listener.requestRegisterSecFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->" + response);
                        try {
                            listener.requestRegisterSecSuc(JsonUtils.JsonRegisterSec(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
