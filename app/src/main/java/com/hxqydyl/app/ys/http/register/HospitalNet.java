package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

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
                .url(UrlConstants.getWholeApiUrl(UrlConstants.GET_HOSPITAL))
                .addParams("cityUuid", cityUuid)
                .addParams("provinceUuid", provinceUuid)
                .addParams("regionUuid", regionUuid)
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestHospitalFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Hospitalresponse---->" + response);
                        HospitalResultBean hospitalResultBean = null;
                        try {
                            hospitalResultBean = JsonUtils.JsonHospitalResult(StringUtils.cutoutBracketToString(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.requestHospitalSuc(hospitalResultBean);
                    }
                });

    }
}
