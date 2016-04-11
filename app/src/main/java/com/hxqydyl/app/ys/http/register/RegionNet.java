package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

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
                .url(UrlConstants.getWholeApiUrl(UrlConstants.GET_REGION))
                .addParams("cityUuid", cityUuid)
                .addParams("callback", Constants.CALLBACK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestRegionFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response-->"+response);
                        try {
                            listener.requestRegionSuc(JsonUtils.JsonRegionResult(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
