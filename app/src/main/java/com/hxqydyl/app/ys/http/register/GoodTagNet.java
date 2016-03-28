package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.AddressParamBean;
import com.hxqydyl.app.ys.bean.register.GoodTagResultBean;
import com.hxqydyl.app.ys.http.register.callback.GoodTagResultCallBack;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hxq on 2016/3/24.
 */
public class GoodTagNet {

    private OnGoodTagListener listener;

    public void setListener(OnGoodTagListener listener) {
        this.listener = listener;
    }

    public interface OnGoodTagListener {
        void requestGoodTagSuc(GoodTagResultBean goodTagResultBean);

        void requestGoodTagFail();
    }

    public void obtainTagResult(AddressParamBean addressParamBean) {

        OkHttpUtils
                .post()
                .url(Constants.REGISTER_THREE)
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
                .execute(new GoodTagResultCallBack() {

                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestGoodTagFail();
                    }

                    @Override
                    public void onResponse(GoodTagResultBean response) {
                        listener.requestGoodTagSuc(response);
                    }
                });

    }
}
