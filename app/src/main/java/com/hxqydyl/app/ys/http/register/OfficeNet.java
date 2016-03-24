package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.OfficeResultBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

/**
 * 科室
 * Created by hxq on 2016/3/22.
 */
public class OfficeNet {

    private OnOfficeListener listener;

    public void setListener(OnOfficeListener listener){
        this.listener = listener;
    }

    public interface OnOfficeListener{
        void requestOfficeSuc(OfficeResultBean officeResultBean);
        void requestOfficeFail();
    }

    public void obtainOffice(){
        //获取科室
        OkHttpClientManager.getAsyn(Constants.GET_DEPARTMENT+"?callback=hxq", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestOfficeFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response---->" + response);
                OfficeResultBean officeResultBean = JsonUtils.JsonOfficeResult(response);
                listener.requestOfficeSuc(officeResultBean);
            }
        });
    }
}
