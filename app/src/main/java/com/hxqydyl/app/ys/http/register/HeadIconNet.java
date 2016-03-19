package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.Map;

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
        OkHttpClientManager.postAsyn(Constants.UPLOAD_IMAGE, map, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestHeadIconNetFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
               listener.requestHeadIconNetSuc(JsonUtils.JsonHeadIconResult(response));
            }
        });
    }
}
