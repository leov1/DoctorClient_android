package com.hxqydyl.app.ys.http;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Patient;
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
     * 获取患者私人信息
     * @param patientId
     */
    public void getPatientPersionalInfo(final String patientId){
        final String shortUrl = UrlConstants.GET_PATIENT_PERSIONAL_INFO;
        String version = "1.0";
        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("uuid", patientId)
                .build()
                .execute(new Callback<Patient>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public Patient parseNetworkResponse(Response response) throws Exception {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        return jsonToPatient(jsonObject);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(Patient response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });

    }

    private Patient jsonToPatient(JSONObject jsonObject) throws Exception{
        Patient patient = new Patient();
        if(jsonObject!=null){
            patient.setPhoneNumber(jsonObject.optString("mobile")); //手机
            patient.setNick(jsonObject.optString("nickName"));//昵称
            patient.setName(jsonObject.optString("customerName"));//名字
            patient.setSex("1".equals(jsonObject.optString("sex"))?"男":"女");//性别 “1”男，“2”女
            patient.setBirthday(jsonObject.optString("birthday"));//生日
            patient.setCard(jsonObject.optString("certCode"));//身份证
            patient.setEmail(jsonObject.optString("email"));//email
            patient.setMarriage(jsonObject.optString("marryState"));//婚姻
            patient.setVocation(jsonObject.optString("industry"));//职业
            patient.setAddress(jsonObject.optString("city"));//地址
            patient.setDiseaseProcess(jsonObject.optString("diseaseTime"));//病程
            patient.setFirstSeeDoctorTime(jsonObject.optString("firstDiagnosis"));//首次就诊时间
            patient.setRelapse("0".equals(jsonObject.optString("ifStart"))?"否":"是");//是否复发 “0”否 “1”是
            patient.setRelapseTimes(jsonObject.optString("seizureTimes"));//复发次数
            patient.setHeight(jsonObject.optString("height"));//身高
            patient.setWeight(jsonObject.optString("weight"));//体重
            patient.setUseCondition(jsonObject.optString("nearlyDrugs"));//近三个月使用药物
            patient.setDescription(jsonObject.optString("illnessDescription"));//病情描述
        }
        return patient;
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
                .addHeader("Accept","application/json")
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
