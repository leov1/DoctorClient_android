package com.hxqydyl.app.ys.http.register;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 上传图片集
 * Created by hxq on 2016/3/22.
 */
public class UploadIconsNet {

    private OnUploadIconsListener listener;

    public void setListener(OnUploadIconsListener listener){
        this.listener = listener;
    }

    public interface OnUploadIconsListener{
        void requestUploadIconsSuc(Query query);
        void requestUploadIconsFail();
    }

    public void saveIcons(String doctorUuid,List<String> imgUris){
//        OkHttpClientManager.Param[] params = new OkHttpClientManager.Param[2];
//        params[0] =  new OkHttpClientManager.Param("doctorUuid",doctorUuid);
//        params[1] = new OkHttpClientManager.Param("userIconList",imgUris);
//
//        OkHttpClientManager.postAsyn(Constants.SAVE_USER_ICON_LIST, params, new OkHttpClientManager.ResultCallback<Query>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                System.out.println("fail---->"+request);
//                listener.requestUploadIconsFail();
//            }
//
//            @Override
//            public void onResponse(Query response) throws JSONException {
//               System.out.println("fail---->"+response.toString());
//               listener.requestUploadIconsSuc(response);
//            }
//        });
    }
}
