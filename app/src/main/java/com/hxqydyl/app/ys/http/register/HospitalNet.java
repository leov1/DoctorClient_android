package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

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

    private void obtainHospitals(String cityUuid,String provinceUuid,String regionUuid){

        OkHttpClientManager.getAsyn(Constants.GET_HOSPITAL+"?cityUuid="+cityUuid+"&provinceUuid="+provinceUuid+"&regionUuid="+regionUuid, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
             listener.requestHospitalFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
             HospitalResultBean hospitalResultBean = JsonUtils.JsonHospitalResult(response);
             listener.requestHospitalSuc(hospitalResultBean);
            }
        });
    }
}
