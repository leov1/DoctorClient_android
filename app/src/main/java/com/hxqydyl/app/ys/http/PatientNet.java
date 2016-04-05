package com.hxqydyl.app.ys.http;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Query;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by white_ash on 2016/4/4.
 */
public class PatientNet extends BaseNet {
    public PatientNet(NetRequestListener listener) {
        super(listener);
    }

    /**
     * 删除患者信息
     * @param customerUuid 患者id
     * @param groupId 患者所属的分组ID
     */
    public void deletePatient(final String customerUuid, final String groupId) {
        final String shortUrl = UrlConstants.DELETE_PATIENT;
        String version = "1.0";
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("groupId", groupId)
                .addParams("customerUuid", customerUuid)
                .build()
                .execute(new Callback<Query>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public Query parseNetworkResponse(Response response) throws Exception {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Query query = new Gson().fromJson(jsonObject.optJSONObject("query").toString(), Query.class);
                        return query;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(Query response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }
}
