package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.OfficeResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

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
        OkHttpUtils
                .get()
                .url(Constants.GET_DEPARTMENT)
                .addParams("callback", Constants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestOfficeFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response---->" + response);
                        OfficeResultBean officeResultBean = null;
                        try {
                            officeResultBean = JsonUtils.JsonOfficeResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.requestOfficeSuc(officeResultBean);
                    }
                });
    }
}
