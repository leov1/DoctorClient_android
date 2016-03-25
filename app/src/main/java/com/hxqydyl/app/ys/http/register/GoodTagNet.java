package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.AddressParamBean;
import com.hxqydyl.app.ys.bean.register.GoodTagBean;
import com.hxqydyl.app.ys.bean.register.GoodTagResultBean;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxq on 2016/3/24.
 */
public class GoodTagNet {

    private OnGoodTagListener listener;

    public void setListener(OnGoodTagListener listener){
        this.listener = listener;
    }

    public interface OnGoodTagListener{
        void requestGoodTagSuc(GoodTagResultBean goodTagResultBean);
        void requestGoodTagFail();
    }

    public void obtainTagResult(AddressParamBean addressParamBean){
        Map<String,String> params = new HashMap<>();
        params.put("doctorUuid",addressParamBean.getDoctorUuid());
        params.put("province",addressParamBean.getProvinceCode());
        params.put("city",addressParamBean.getCityCode());
        params.put("area",addressParamBean.getAreaCode());
        params.put("infirmary",addressParamBean.getInfirmaryCode());
        params.put("departments",addressParamBean.getDepartments());
        params.put("speciality",addressParamBean.getSpeciality());
        params.put("professional",addressParamBean.getProfessional());
        params.put("synopsis",addressParamBean.getSynopsis());
        params.put("telephone",addressParamBean.getTelephone());
        params.put("otherhospital",addressParamBean.getOtherhospital());
        System.out.println("map---->"+params.toString());
        OkHttpClientManager.postAsyn(Constants.REGISTER_THREE, params, new ResultCallback<GoodTagResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestGoodTagFail();
            }

            @Override
            public void onResponse(GoodTagResultBean response) throws JSONException {
                System.out.println("response--->"+response.toString());
               listener.requestGoodTagSuc(response);
            }
        });
    }
}
