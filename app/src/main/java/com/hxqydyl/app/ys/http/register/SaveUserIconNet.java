package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by hxq on 2016/3/30.
 */
public class SaveUserIconNet {

    private OnSaveUserIconListener listener;

    public void setListener(OnSaveUserIconListener listener){
        this.listener = listener;
    }

    public interface OnSaveUserIconListener{
        void requestSaveIconSuc(Query query);
        void requestSaveIconFail();
    }

    public void saveUserIcon(String doctorUuid,String userIconList){
        System.out.println("response---->"+userIconList);
        OkHttpUtils.get()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.SAVE_USER_ICON_LIST))
                .addParams("doctorUuid",doctorUuid)
                .addParams("userIconList",userIconList)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                System.out.println("onError---->");
                listener.requestSaveIconFail();
            }

            @Override
            public void onResponse(String response) {
               System.out.println("response---->"+response);
                try {
                    listener.requestSaveIconSuc(JsonUtils.JsonQuery(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
