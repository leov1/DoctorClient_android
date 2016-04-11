package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.AddressParamBean;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * Created by hxq on 2016/3/24.
 */
public class GoodTagNet {

    private OnGoodTagListener listener;

    public void setListener(OnGoodTagListener listener) {
        this.listener = listener;
    }

    public interface OnGoodTagListener {
        void requestGoodTagSuc(RegisterFirst registerFirst);

        void requestGoodTagFail();
    }

    public void obtainTagResult(AddressParamBean addressParamBean) {
        System.out.println("request--->"+addressParamBean.toString());
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.REGISTER_THREE))
                .addParams("doctorUuid", addressParamBean.getDoctorUuid())
                .addParams("province", addressParamBean.getProvinceCode())
                .addParams("city", addressParamBean.getCityCode())
                .addParams("area", addressParamBean.getAreaCode())
                .addParams("infirmary", addressParamBean.getInfirmaryCode())
                .addParams("departments", addressParamBean.getDepartments())
                .addParams("speciality", addressParamBean.getSpeciality())
                .addParams("professional", addressParamBean.getProfessional())
                .addParams("synopsis", addressParamBean.getSynopsis())
                .addParams("telephone", addressParamBean.getTelephone())
                .addParams("otherhospital", addressParamBean.getOtherhospital())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.println("onError--->");
                        listener.requestGoodTagFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("request--->"+response);

                        try {
                            listener.requestGoodTagSuc(JsonUtils.JsonRegisterThree(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
