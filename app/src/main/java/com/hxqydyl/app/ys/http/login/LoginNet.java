package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> params = new HashMap<>();
//        params.put("mobile", mobile);
//        params.put("password",password);
//        params.put("callback", Constants.CALLBACK);

        params.put("","");

        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
            }

            @Override
            public void onResponse(String response){
                System.out.println("request---->"+response);
 //               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
            }
        });

    }

}
