package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by hxq on 2016/3/21.
 */
public class HospitalNet {

    private OnHospitalListener listener;

    public void setListener(OnHospitalListener listener){
        this.listener = listener;
    }

    public interface OnHospitalListener{
        void requestHospitalSuc(HospitalResultBean hospitalResultBean);
        void requestHospitalFail();
    }

    public void obtainHospitals(String provinceUuid,String cityUuid,String regionUuid){

        OkHttpUtils
                .get()
                .url(Constants.GET_HOSPITAL)
                .addParams("cityUuid", cityUuid)
                .addParams("provinceUuid", provinceUuid)
                .addParams("regionUuid", regionUuid)
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
 //                       mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

//        System.out.println("Hospitalresponse---->"+provinceUuid+"---"+cityUuid+"---"+regionUuid);
//        OkHttpClientManager.getAsyn(Constants.GET_HOSPITAL+"?cityUuid="+cityUuid+"&provinceUuid="+provinceUuid+"&regionUuid="+regionUuid+"&callback=hxq", new
//                ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//             listener.requestHospitalFail();
//            }
//
//            @Override
//            public void onResponse(String response) throws JSONException {
//                System.out.println("Hospitalresponse---->" + response);
//             HospitalResultBean hospitalResultBean = JsonUtils.JsonHospitalResult(response);
//             listener.requestHospitalSuc(hospitalResultBean);
//            }
//        });
    }
}
