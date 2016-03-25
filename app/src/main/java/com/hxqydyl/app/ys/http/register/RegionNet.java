package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

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
        OkHttpClientManager.getAsyn(Constants.GET_REGION+"?cityUuid="+cityUuid+"&callback=hxq", new ResultCallback<String>() {
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
        });
    }
}
