package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
                .url(Constants.GET_PROVINCE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
           //             mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });


       /* OkHttpClientManager.getAsyn(Constants.GET_PROVINCE, new ResultCallback<String>() {
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
        });*/
    }
}
