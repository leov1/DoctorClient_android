package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 区县
 * Created by hxq on 2016/3/21.
 */
public class RegionNet {

    private OnRegionListener listener;

    public void setListener(OnRegionListener listener){
        this.listener = listener;
    }

    public interface OnRegionListener{
        void requestRegionSuc(RegionResultBean regionResultBean);
        void requestRegionFail();
    }

    public void obtainRegions(String cityUuid){
        System.out.println("provinceUuid-->"+cityUuid);

        OkHttpUtils
                .get()
                .url(Constants.GET_REGION)
                .addParams("cityUuid", cityUuid)
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
            //            mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

       /* OkHttpClientManager.getAsyn(Constants.GET_REGION+"?cityUuid="+cityUuid+"&callback=hxq", new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
               listener.requestRegionFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                System.out.println("response-->"+response);
                RegionResultBean regionResultBean = JsonUtils.JsonRegionResult(response);
                listener.requestRegionSuc(regionResultBean);
            }
        });*/
    }
}
