package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by hxq on 2016/3/18.
 */
public class HeadIconNet {

    private OnHeadIconNetListener listener;

    public void setListener(OnHeadIconNetListener listener){
        this.listener = listener;
    }

    public interface OnHeadIconNetListener{
        void requestHeadIconNetSuc(HeadIconResult headIconResult);
        void requestHeadIconNetFail();
    }

    public void uploadHeadImg(Map<String,String> map){

        OkHttpUtils
                .post()
                .url(Constants.UPLOAD_IMAGE)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestHeadIconNetFail();
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });


       /* OkHttpClientManager.postAsyn(Constants.UPLOAD_IMAGE, map, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestHeadIconNetFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
               listener.requestHeadIconNetSuc(JsonUtils.JsonHeadIconResult(response));
            }
        });*/
    }
}
