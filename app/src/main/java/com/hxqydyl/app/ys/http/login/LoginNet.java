package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;


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
        void  requestLoginNetSuccess(DoctorResult doctorResult);
        void  requestLoginNetFail();
    }

    public void loginData(String mobile,String password){
      OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.LOGIN_URL))
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("callback",UrlConstants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mListener.requestLoginNetFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->"+response);
                        try {
                            mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

       /* OkHttpUtils
                .post()
                .url("http://172.168.1.9/mobile/patient/apply/1.0/applyVisit")
                .addParams("doctorUuid",  "中国")
                .addParams("customerUuid",  "中国")
                .addParams("symptoms", "中国")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->"+response);
                        try {
                            mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
    }

}
