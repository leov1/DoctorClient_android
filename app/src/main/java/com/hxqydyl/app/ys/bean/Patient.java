package com.hxqydyl.app.ys.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class Patient extends BaseBean implements Serializable {
    //患者id
    public String customerUuid;
    //    头像
    private String customerImg;
    //    电话
    private String mobile;
    //    昵称
    private String nickname;
    //    姓名
    private String realName;
    //    年龄
    private String age;
    //    性别
    private String sex;
    //    建立随访关系的时间
    private String followTime;
    //    生日
    private String birthday;
    //    身份证号
    private String certCode;
    //    email地址
    private String email;
    //    婚姻情况
    private String marryState;
    //    职业
    private String industry;
    //    地址
    private String address;
    //    病程
    private String diseaseProcess;
    //    首次就诊时间
    private String firstSeeDoctorTime;
    //    是否复发
    private String relapse;
    //    复发次数
    private String relapseTimes;
    //    身高
    private String height;
    //    体重
    private String weight;
    //    近3个月使用药物
    private String useCondition;
    //    病情描述
    private String description;
    //群组患者
    private String customerName;
    //患者提问
    private String customerMessage;
    //关系时间
    private String applyTime;
    //患者描述
    private String illnessDescription;

    private String visitPreceptUuid;

    @Override
    public String getId() {
        if (!TextUtils.isEmpty(customerUuid)){
            return customerUuid;
        }
        return super.getId();
    }

    public String getIllnessDescription() {
        return illnessDescription;
    }

    public void setIllnessDescription(String illnessDescription) {
        this.illnessDescription = illnessDescription;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    private ArrayList<PatientTreatInfo> patientTreatInfos = new ArrayList<PatientTreatInfo>();

    public String getCustomerImg() {
        return customerImg;
    }

    public void setCustomerImg(String customerImg) {
        this.customerImg = customerImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarryState() {
        return marryState;
    }

    public void setMarryState(String marryState) {
        this.marryState = marryState;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiseaseProcess() {
        return diseaseProcess;
    }

    public void setDiseaseProcess(String diseaseProcess) {
        this.diseaseProcess = diseaseProcess;
    }

    public String getFirstSeeDoctorTime() {
        return firstSeeDoctorTime;
    }

    public void setFirstSeeDoctorTime(String firstSeeDoctorTime) {
        this.firstSeeDoctorTime = firstSeeDoctorTime;
    }

    public String getRelapse() {
        return relapse;
    }

    public void setRelapse(String relapse) {
        this.relapse = relapse;
    }

    public String getRelapseTimes() {
        return relapseTimes;
    }

    public void setRelapseTimes(String relapseTimes) {
        this.relapseTimes = relapseTimes;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(String useCondition) {
        this.useCondition = useCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPatientTreatInfo(PatientTreatInfo patientTreatInfo) {
        patientTreatInfos.add(patientTreatInfo);
    }

    public void removePatientTreatInfo(PatientTreatInfo patientTreatInfo) {
        patientTreatInfos.remove(patientTreatInfo);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList<PatientTreatInfo> getPatientTreatInfos() {
        return patientTreatInfos;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getVisitPreceptUuid() {
        return visitPreceptUuid;
    }

    public void setVisitPreceptUuid(String visitPreceptUuid) {
        this.visitPreceptUuid = visitPreceptUuid;
    }
}
