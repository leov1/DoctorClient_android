package com.hxqydyl.app.ys.http.login;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.BaseBean;
import com.hxqydyl.app.ys.bean.DoctorInfo;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

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

        /**
         * 注册第一步
         * @param doctorInfo 医生信息bean
         */
        void  registerOneSuccess(DoctorInfo doctorInfo);

        /**
         * 注册第二步
         * @param uuid 医生id
         */
        void  registerTwoSuccess(String uuid);

        /**
         * 注册第三步
         * @param doctorInfo
         */
        void  registerThreeSuccess(DoctorInfo doctorInfo);

        /**
         * 请求失败通用方法
         * @param statusCode 返回代码
         */
        void  requestFail(int statusCode);
    }

    public void loginData(String mobile,String password){
        Map<String,String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password",password);
        params.put("callback", Constants.CALLBACK);
        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
            }

            @Override
            public void onResponse(String response){
                mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
            }
        });
    }

    /**
     * 注册 第一步
     * @param map {
     *            mobile ——request：true，
     *            captcha ——request：true，
     *            password ——request：true
     *            }
     *  @return map ｛uuid，mobile｝
     */
    public void registerOne(Map<String,String> map) {
        Map<String,String> params = new HashMap<>();
        params.put("mobile", map.get("mobile"));
        params.put("password",map.get("password"));
        params.put("captcha", map.get("captcha"));
        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mListener.requestFail(Constants.REQUEST_FAIL);
            }

            @Override
            public void onResponse(String response){
                mListener.registerOneSuccess(JsonUtils.JsonLoginData(response));
            }
        });
    }

    /**
     * 注册 第二步
     * @param map {
     *            uuid ——request：true，
     *            email ——request：true，
     *            sex ——request：true，
     *            doctorName ——request：true，
     *            callback ——request：true
     *            }
     * @return
     */
    public void registerTwo(Map<String,String> map) {
        Map<String,String> params = new HashMap<>();
        params.put("uuid", map.get("uuid"));
        params.put("email",map.get("email"));
        params.put("sex",map.get("sex"));
        params.put("doctorName",map.get("doctorName"));
        params.put("callback", Constants.CALLBACK);
        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mListener.requestFail(Constants.REQUEST_FAIL);
            }

            @Override
            public void onResponse(String response){
                mListener.registerTwoSuccess(response);
            }
        });
    }

    /**
     * 注册 第三步
     * @param map {
     *            doctorUuid ——request：true，
     *            province ——request：true，
     *            city ——request：true，
     *            area ——request：true，
     *            infirmary ——request：true，
     *            departments ——request：true，
     *            speciality ——request：true，
     *            callback ——request：true，
     *            }
     @return
     */
    public void registerThree(Map<String,String> map) {
        Map<String,String> params = new HashMap<>();
        params.put("doctorUuid", map.get("doctorUuid"));
        params.put("province",map.get("province"));
        params.put("city",map.get("city"));
        params.put("area",map.get("area"));
        params.put("infirmary", map.get("infirmary"));
        params.put("departments",map.get("departments"));
        params.put("speciality",map.get("speciality"));
        params.put("callback", Constants.CALLBACK);
        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mListener.requestFail(Constants.REQUEST_FAIL);
            }

            @Override
            public void onResponse(String response){
                mListener.registerThreeSuccess(JsonUtils.JsonLoginData(response));
            }
        });
    }

}
