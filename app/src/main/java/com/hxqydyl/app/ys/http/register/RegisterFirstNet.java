package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

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

        OkHttpUtils
                .post()
                .url(Constants.REGISTER_ONE)
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("captcha", captcha)
                .addParams("callback","hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
       //                 mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

       /* Map<String,String> params = new HashMap<String,String>();
        params.put("mobile",mobile);
        params.put("password",password);
        params.put("captcha",captcha);
        params.put("callback","hxq");
        System.out.println("response--->");
        OkHttpClientManager.postAsyn(Constants.REGISTER_ONE, params, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestRegisterFirstNetFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response--->"+response);
                listener.requestRegisterFirstNetSuccess(JsonUtils.JsonQuery(response));
            }
        });*/
    }

}
