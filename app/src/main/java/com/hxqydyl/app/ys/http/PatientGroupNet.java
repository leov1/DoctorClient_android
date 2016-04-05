package com.hxqydyl.app.ys.http;

import android.util.Log;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.Query;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shs.cn on 2016/4/2.
 */
public class PatientGroupNet extends BaseNet {
    public PatientGroupNet(NetRequestListener listener) {
        super(listener);
    }

    /**
     * 修改患者分组的名字
     * @param groupId 待修改的患者分组id
     * @param groupName 将要修改为的名字
     */
    public void renamePatientGroup(final String groupId, final String groupName) {
        final String shortUrl = UrlConstants.RENAME_PATIENT_GROUP;
        String version = "1.0";
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("groupId", groupId)
                .addParams("groupName", groupName)
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

    /**
     * 移动患者所在的分组
     *
     * @param groupId      移向的分组id
     * @param customerUuid 患者id
     */
    public void movePatientToOtherGroup(final String groupId, final String customerUuid) {
        final String shortUrl = UrlConstants.MOVE_PATIENT_TO_OTHER_GROUP;
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

    /**
     * 获取所有的患者分组(不包括分组下的患者信息)
     *
     * @param doctorUuid
     */
    public void getPatientGroups(final String doctorUuid) {
        final String shortUrl = UrlConstants.GET_ALL_PATIENT_GROUP;
        String version = "1.0";
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("doctorUuid", doctorUuid)
                .build()
                .execute(new Callback<ArrayList<PatientGroup>>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public ArrayList<PatientGroup> parseNetworkResponse(Response response) throws Exception {
                        ArrayList<PatientGroup> groups = new ArrayList<PatientGroup>();
                        JSONObject obj = new JSONObject(response.body().string());
                        JSONArray array = obj.getJSONArray("relist");
                        if (array != null && array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                groups.add(jsonToPatientGroup(array.getJSONObject(i)));
                            }
                        }
                        return groups;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(ArrayList<PatientGroup> response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    /**
     * 添加患者分组
     *
     * @param doctorUuid
     * @param groupName
     */
    public void addPatientGroup(final String doctorUuid, final String groupName) {
        final String shortUrl = UrlConstants.ADD_PATIENT_GROUP;
        String version = "1.0";
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("doctorUuid", doctorUuid)
                .addParams("groupName", groupName)
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

    /**
     * 删除患者分组
     *
     * @param groupId
     */
    public void deletePatientGroup(final String groupId) {
        final String shortUrl = UrlConstants.DELETE_PATIENT_GROUP;
        String version = "1.0";
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("groupId", groupId)
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

    /**
     * 获取所有的患者分组与患者数据
     *
     * @param docUuid
     */
    public void getAllPatientGroupAndPatient(final String docUuid) {
        final String shortUrl = UrlConstants.GET_ALL_PATIENT_AND_GROUP_INFO;
        String version = "1.0";
        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("doctorUuid", docUuid)
                .build()
                .execute(new Callback<ArrayList<PatientGroup>>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public ArrayList<PatientGroup> parseNetworkResponse(Response response) throws Exception {
                        ArrayList<PatientGroup> groups = new ArrayList<PatientGroup>();
                        JSONObject obj = new JSONObject(response.body().string());
                        JSONArray array = obj.getJSONArray("relist");
                        if (array != null && array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                groups.add(jsonToPatientGroup(array.getJSONObject(i)));
                            }
                        }
                        return groups;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(ArrayList<PatientGroup> response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    /**
     * 将json对象转化成为Patient对象
     *
     * @param obj
     * @return
     */
    private Patient jsonToPatient(JSONObject obj) {
        if (obj != null) {
            Patient patient = new Patient();
            patient.setId(obj.optString("customerUuid"));
            patient.setAge(obj.optString("age"));
            patient.setSex(obj.optString("sex"));
            patient.setName(obj.optString("customerName"));
            patient.setDescription(obj.optString("customerMessage"));
            return patient;
        } else {
            return null;
        }
    }

    /**
     * 将json对象转化成为PatientGroup对象
     *
     * @param obj
     * @return
     * @throws JSONException
     */
    private PatientGroup jsonToPatientGroup(JSONObject obj) throws JSONException {
        if (obj != null) {
            PatientGroup patientGroup = new PatientGroup();
            patientGroup.setId(obj.optString("groupId"));
            patientGroup.setGroupName(obj.optString("groupName"));
            JSONArray array = obj.optJSONArray("customers");
            if (array != null && array.length() != 0) {
                for (int i = 0; i < array.length(); i++) {
                    patientGroup.addPatient(jsonToPatient(array.getJSONObject(i)));
                }
            }
            return patientGroup;
        } else {
            return null;
        }
    }
}
