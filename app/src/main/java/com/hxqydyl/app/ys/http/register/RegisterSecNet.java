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
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
         //               mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

      /*  Map<String,String> params = new HashMap<>();
        params.put("uuid",uuid);
        params.put("email",email);
        params.put("sex",sex);
        params.put("icon",icon);
        params.put("doctorName",doctorName);
        params.put("callback","hxq");
        System.out.println("params--->"+params.toString());
        OkHttpClientManager.postAsyn(Constants.REGISTER_TWO, params, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
              listener.requestRegisterSecFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response---->"+response);
                listener.requestRegisterSecSuc();
            }
        });*/
    }
}
