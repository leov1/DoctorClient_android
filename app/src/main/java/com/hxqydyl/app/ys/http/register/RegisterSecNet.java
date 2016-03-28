package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

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

        OkHttpUtils
                .post()
                .url(Constants.REGISTER_TWO)
                .addParams("uuid", uuid)
                .addParams("email", email)
                .addParams("sex", sex)
                .addParams("icon", icon)
                .addParams("doctorName", doctorName)
                .addParams("callback", Constants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestRegisterSecFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->"+response);
                        listener.requestRegisterSecSuc();
                    }
                });
    }

}
