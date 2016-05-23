package com.hxqydyl.app.ys.bean.follow.plan;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 */
public class Plan extends PlanBaseInfo implements Serializable {

    private String drugTherapy; //药物不良反应处理
    private String sideEffects; //其他治疗
    private String period;      //随访周期
    private String electrocardiogram;       //心电图检查周期
    private String bloodRoutine;        //血常规周期
    private String hepatic;         //肝功能周期
    private String weight;  //
    private String selfPeriod;//自评量表周期
    private String doctorPeriod;//医评量表周期
    private ArrayList<ImportantAdviceChild> doctorAdvice;        //药品信息
    private ArrayList<CheckSycle> otherMap;   //其他自定义随访周期
    private ArrayList<Scale> selfTest;   //自评量表
    private ArrayList<Scale> doctorTest; //医评量表
    private ArrayList<HealthTips> healthGuide;        //健康小贴士
    private String delFlag;//0未完成 1已完成

    public String getSelfPeriod() {
        return selfPeriod;
    }

    public void setSelfPeriod(String selfPeriod) {
        this.selfPeriod = selfPeriod;
    }

    public String getDoctorPeriod() {
        return doctorPeriod;
    }

    public void setDoctorPeriod(String doctorPeriod) {
        this.doctorPeriod = doctorPeriod;
    }

    public String getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setDoctorAdvice(ArrayList<ImportantAdviceChild> doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
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


    public List<CheckSycle> getOtherMap() {
        return otherMap;
    }

    public List<ImportantAdviceChild> getDoctorAdvice() {
        return doctorAdvice;
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
