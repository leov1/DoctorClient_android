package com.hxqydyl.app.ys.http.homepage;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.homepage.PageIconResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by hxq on 2016/4/5.
 */
public class PagerNet {

    private OnPagerNetListener listener;

    public void setPagerNetListener(OnPagerNetListener listener){
        this.listener = listener;
    }

    public interface OnPagerNetListener{
        void PagerNetSuccess(PageIconResult pageIconResult,String str);
        void PagerNetFail(int statueCode);
    }

    public void getPager(){
        OkHttpUtils.get().url(Constants.GET_PLATFORMPIC)
                .addParams("adUuid","customerLunBoTuId")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.PagerNetFail(1);
            }

            @Override
            public void onResponse(String response) {
                try {
                    listener.PagerNetSuccess(JsonUtils.JsonPageIconResult(response),response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
