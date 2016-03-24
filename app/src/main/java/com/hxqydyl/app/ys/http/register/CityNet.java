package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.CityResultBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

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
        System.out.println("provinceUuid-->"+code);
        OkHttpClientManager.getAsyn(Constants.GET_CITY+"?provinceUuid="+code, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.RequestCityFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                CityResultBean cityResultBean = JsonUtils.JsonCityResult(response);
                listener.requestCitySuc(cityResultBean);
            }
        });
    }
}
