package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 登陆请求网络
 * Created by hxq on 2016/3/3.
 */
public class LoginNet {

    private OnLoginNetListener mListener;

    public void setmListener(OnLoginNetListener mListener){
        this.mListener = mListener;
    }

    public interface  OnLoginNetListener{
        void  requestLoginNetSuccess(DoctorInfo doctorInfo);
        void  requestLoginNetFail(int statusCode);
    }

    public void loginData(String mobile,String password){
        OkHttpUtils
                .post()
                .url(Constants.LOGIN_URL)
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("callback",Constants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
                     mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

    }

}
