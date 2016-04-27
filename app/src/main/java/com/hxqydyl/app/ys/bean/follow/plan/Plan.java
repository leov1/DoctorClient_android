package com.hxqydyl.app.ys.bean.follow.plan;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 *
 */
public class Plan extends PlanBaseInfo implements Serializable {

    private String drugTherapy; //药物不良反应处理
    private String sideEffects; //其他治疗
    private String doctorAdvice;    //药物信息

    private String period;      //随访周期
    private String electrocardiogram;       //心电图检查周期
    private String bloodRoutine;        //血常规周期
    private String hepatic;         //肝功能周期
    private String weight;  //

    private ArrayList<ImportantAdviceChild> medicineList;        //药品信息
    private ArrayList<CheckSycle> otherMap;   //其他自定义随访周期
    private ArrayList<Scale> selfTest;   //自评量表
    private ArrayList<Scale> doctorTest; //医评量表
    private ArrayList<HealthTips> healthGuide;        //健康小贴士

    public Plan(String doctorUuid,
                String visitUuid,
                String customerUuid,
                String preceptName, int num) {
        super(doctorUuid, visitUuid, customerUuid, preceptName, num);
    }

    public Plan(String preceptName) {
        setPreceptName(preceptName);
    }

    public Plan() {

    }


    public static Plan parseDetailJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject query = jsonObject.getJSONObject("query");
            if (!"1".equals(query.getString("success"))) {
                Log.e("client", "API返回失败， success 不为1");
                return null;
            }
            Plan plan = new Plan();
            plan.setPreceptName(jsonObject.getString("preceptName"));
            plan.setVisitUuid(jsonObject.getString("visitUuid"));
            plan.setHepatic(jsonObject.getString("hepatic"));
            plan.setPeriod(jsonObject.getString("period"));
            plan.setElectrocardiogram(jsonObject.getString("electrocardiogram"));
            plan.setWeight(jsonObject.getString("weight"));
            plan.setDrugTherapy(jsonObject.getString("drugTherapy"));
            plan.setSideEffects(jsonObject.getString("sideEffects"));
            plan.setBloodRoutine(jsonObject.getString("bloodRoutine"));
            plan.setSelfTest(Scale.parse(jsonObject.optJSONArray("selfTest")));
            plan.setDoctorTest(Scale.parse(jsonObject.optJSONArray("doctorTest")));
            plan.setOtherMap(CheckSycle.parse(jsonObject.optJSONArray("otherMap")));
//            plan.setMedicineList(Medicine.parse(jsonObject.optJSONArray("doctorAdvice")));
            plan.setHealthGuide(HealthTips.parse(jsonObject.optJSONArray("healthGuide")));
            return plan;
        } catch (Exception e) {
            Log.e("client", e.getMessage());
            return null;
        }
    }

    public void setMedicineList(ArrayList<ImportantAdviceChild> medicineList) {
        this.medicineList = medicineList;
    }

    public void setOtherMap(ArrayList<CheckSycle> otherMap) {
        this.otherMap = otherMap;
    }

    public void setSelfTest(ArrayList<Scale> selfTest) {
        this.selfTest = selfTest;
    }

    public ArrayList<Scale> getDoctorTest() {
        return doctorTest;
    }

    public void setDoctorTest(ArrayList<Scale> doctorTest) {
        this.doctorTest = doctorTest;
    }

    public ArrayList<HealthTips> getHealthGuide() {
        return healthGuide;
    }

    public void setHealthGuide(ArrayList<HealthTips> healthGuide) {
        this.healthGuide = healthGuide;
    }

    public static List<Plan> parseList2(String string) {
        return JSONArray.parseArray(string, Plan.class);
    }

    public List<CheckSycle> getOtherMap() {
        return otherMap;
    }

    public List<ImportantAdviceChild> getMedicineList() {
        return medicineList;
    }

    public String getDrugTherapy() {
        return drugTherapy;
    }

    public void setDrugTherapy(String drugTherapy) {
        this.drugTherapy = drugTherapy;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getElectrocardiogram() {
        return electrocardiogram;
    }

    public void setElectrocardiogram(String electrocardiogram) {
        this.electrocardiogram = electrocardiogram;
    }

    public String getBloodRoutine() {
        return bloodRoutine;
    }

    public void setBloodRoutine(String bloodRoutine) {
        this.bloodRoutine = bloodRoutine;
    }

    public String getHepatic() {
        return hepatic;
    }

    public void setHepatic(String hepatic) {
        this.hepatic = hepatic;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<Scale> getSelfTest() {
        return selfTest;
    }


}
