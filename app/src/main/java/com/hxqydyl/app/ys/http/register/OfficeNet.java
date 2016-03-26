package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.OfficeResultBean;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
          //              mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });


//        OkHttpClientManager.getAsyn(Constants.GET_DEPARTMENT+"?callback=hxq", new ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                listener.requestOfficeFail();
//            }
//
//            @Override
//            public void onResponse(String response) throws JSONException {
//                System.out.println("response---->" + response);
//                OfficeResultBean officeResultBean = JsonUtils.JsonOfficeResult(response);
//                listener.requestOfficeSuc(officeResultBean);
//            }
//        });
    }
}
