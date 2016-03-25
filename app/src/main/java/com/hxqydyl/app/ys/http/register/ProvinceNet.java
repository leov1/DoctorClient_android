package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

/**
 * Created by hxq on 2016/3/21.
 */
public class ProvinceNet {

    private OnProvinceListener listener;

    public void setListener(OnProvinceListener listener){
        this.listener = listener;
    }

    public interface OnProvinceListener{
        void requestProvinceSuc(ProvinceInfoResult provinceInfoResult);
        void RequestProvinceFail();
    }

    public void obtainProvince(){
        System.out.println("response---->");
        //获取医院
        OkHttpClientManager.getAsyn(Constants.GET_PROVINCE, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
              listener.RequestProvinceFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response---->" + response);
                ProvinceInfoResult provinceInfoResult = JsonUtils.JsonProvinceInfoResult(response);
                listener.requestProvinceSuc(provinceInfoResult);
            }
        });
    }
}
