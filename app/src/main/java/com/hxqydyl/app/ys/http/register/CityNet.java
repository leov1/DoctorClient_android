package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.CityResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 注册时获取市
 * Created by hxq on 2016/3/22.
 */
public class CityNet {

    private OnCityListener listener;

    public void setListener(OnCityListener listener){
        this.listener = listener;
    }

    public interface OnCityListener{
        void requestCitySuc(CityResultBean cityResultBean);
        void RequestCityFail();
    }

    public void obtainCity( String code){
        OkHttpUtils
                .get()
                .url(Constants.GET_CITY)
                .addParams("provinceUuid", code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.RequestCityFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        CityResultBean cityResultBean = null;
                        try {
                            cityResultBean = JsonUtils.JsonCityResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.requestCitySuc(cityResultBean);
                    }
                });

    }
}
