package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

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

        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.GET_PROVINCE))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.RequestProvinceFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->" + response);
                        ProvinceInfoResult provinceInfoResult = null;
                        try {
                            provinceInfoResult = JsonUtils.JsonProvinceInfoResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.requestProvinceSuc(provinceInfoResult);
                    }
                });
    }
}
