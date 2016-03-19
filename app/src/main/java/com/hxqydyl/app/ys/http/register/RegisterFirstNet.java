package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册第一步
 * Created by hxq on 2016/3/18.
 */
public class RegisterFirstNet {

    private OnRegisterFirstListener listener;

    public void setListener(OnRegisterFirstListener listener){
        this.listener = listener;
    }

    public interface OnRegisterFirstListener{
        void requestRegisterFirstNetSuccess(RegisterFirst query);
        void requestRegisterFirstNetFail();
    }

    public void registerFirst(String mobile,String password,String captcha){
        Map<String,String> params = new HashMap<String,String>();
        params.put("mobile",mobile);
        params.put("password",password);
        params.put("captcha",captcha);
        params.put("callback","hxq");
        OkHttpClientManager.postAsyn(Constants.REGISTER_ONE, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestRegisterFirstNetFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response--->"+response);
                listener.requestRegisterFirstNetSuccess(JsonUtils.JsonQuery(response));
            }
        });
    }

}
