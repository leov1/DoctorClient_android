package com.hxqydyl.app.ys.bean.follow.plan;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 *
 */
public class Plan extends PlanBaseInfo {

    private String drugTherapy; //药物不良反应处理
    private String sideEffects; //其他治疗
    private String doctorAdvice;    //药物信息

    private String period;      //随访周期
    private String electrocardiogram;       //心电图检查周期
    private String bloodRoutine;        //血常规周期
    private String hepatic;         //肝功能周期
    private String renal;   //  肾功能周期
    private String weight;  //
    private List<CheckSycle> otherCheckSycle;   //其他自定义随访周期

    private List<Scale> selfTestList;   //自评量表
    private List<Scale> doctorTestList; //医评量表

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

    public static List<Plan> parseList2(String string) {
        return JSONArray.parseArray(string, Plan.class);
    }

    public List<CheckSycle> getOtherCheckSycle() {
        return otherCheckSycle;
    }

    public void setOtherCheckSycle(List<CheckSycle> otherCheckSycle) {
        this.otherCheckSycle = otherCheckSycle;
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

    public String getRenal() {
        return renal;
    }

    public void setRenal(String renal) {
        this.renal = renal;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<Scale> getSelfTestList() {
        return selfTestList;
    }

    public void setSelfTestList(List<Scale> selfTestList) {
        this.selfTestList = selfTestList;
    }

    public List<Scale> getDoctorTestList() {
        return doctorTestList;
    }

    public void setDoctorTestList(List<Scale> doctorTestList) {
        this.doctorTestList = doctorTestList;
    }
}
