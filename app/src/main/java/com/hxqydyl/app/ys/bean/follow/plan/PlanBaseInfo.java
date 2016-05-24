package com.hxqydyl.app.ys.bean.follow.plan;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangchao36 on 16/4/4.
 * 随访方案的基本信息，供列表使用
 */
public class PlanBaseInfo implements Serializable {

    private String doctorUuid;      //医生的uuid
    private String visitUuid;       //随访方案的uuid
    private String customerUuid;    //患者的uuid
    private String preceptName;     //随访方案的名称
    private String delFlag;         //方案状态 1--已完成   0--未完成
    private int num;                //关联随访方案的患者人数

    public PlanBaseInfo() {
    }

    public PlanBaseInfo(String doctorUuid, String visitUuid, String customerUuid, String preceptName, String delFlag, int num) {
        this.doctorUuid = doctorUuid;
        this.visitUuid = visitUuid;
        this.customerUuid = customerUuid;
        this.preceptName = preceptName;
        this.delFlag = delFlag;
        this.num = num;
    }

    public PlanBaseInfo(String doctorUuid, String visitUuid, String customerUuid, String preceptName, int num) {
        this(doctorUuid,visitUuid,customerUuid,preceptName,"1",num);
    }

    public static List<PlanBaseInfo> parseList(String string) {
        return JSONArray.parseArray(string, PlanBaseInfo.class);
    }

    //是否已完成
    public boolean isComplete(){
        return this.delFlag != null && this.delFlag.equals("1");
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDoctorUuid() {
        return doctorUuid;
    }

    public void setDoctorUuid(String doctorUuid) {
        this.doctorUuid = doctorUuid;
    }

    public String getVisitUuid() {
        return visitUuid;
    }

    public void setVisitUuid(String visitUuid) {
        this.visitUuid = visitUuid;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getPreceptName() {
        return preceptName;
    }

    public void setPreceptName(String preceptName) {
        this.preceptName = preceptName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
