package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册第二步
 * Created by hxq on 2016/3/18.
 */
public class RegisterSecNet {

    private OnRegisterSecListener listener;

    public void setListener(OnRegisterSecListener listener){
        this.listener = listener;
    }

    public interface OnRegisterSecListener{
        void requestRegisterSecSuc();
        void requestRegisterSecFail();
    }

    public void registerSec(String uuid,String email,String sex,String icon,String doctorName){
        Map<String,String> params = new HashMap<>();
        params.put("uuid",uuid);
        params.put("email",email);
        params.put("sex",sex);
        params.put("icon",icon);
        params.put("doctorName",doctorName);
        params.put("callback","hxq");
        OkHttpClientManager.postAsyn(Constants.REGISTER_TWO, params, new OkHttpClientManager.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Object response) throws JSONException {

            }
        });
    }
}
