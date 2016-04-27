package com.hxqydyl.app.ys.bean.follow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/4/3.
 * 随访申请信息
 * "ifStart": 是否首发,
 * "applyUuid": 申请uuid,
 * "imgs": 图片信息,
 * "address": 住址,
 * "firstDiagnosis": 首次就诊时间,
 * "certCode": 出生年月,
 * "illnessDescription": 病情描述,
 * "doctorUuid": 医生uuid,
 * "sex": 性别 ：1是男；2是女,
 * "mobile": 手机号,
 * "weight": 体重,
 * "industry": 职业,
 * "marryState": 婚姻状况,
 * "realName": 真实姓名,
 * "diseaseTime": 病程,
 * "customerUuid": 患者uuid,
 * "diagnose": 诊断描述,
 * "nickname": 昵称,
 * "email": 电子邮箱,
 * "age": 年龄,
 * "seizureTimes": 复发次数,
 * "height": 身高,
 * "nearlyDrugs": 近3个月使用药物
 */
public class FollowApply {

    private String doctorUuid;      //医生的uuid
    private String sex;         //性别 ：1是男；2是女
    private String age;     //年龄
    private String customerUuid;    //患者的uuid'
    private String realName;        //患者的真实姓名
    private String applyUuid;       //随访申请的uuid
    private String imgUrl;
    private String createTime;
    private String illnessDescription;
    private String symptoms;    //症状
    private ArrayList<String> imgs;
    private String ifStart;
    private String address;// 北京市,
    private String firstDiagnosis;// null,
    private String certCode;// 11231321213,
    private String mobile;// 13671050634,
    private String weight;// null,
    private String industry;// 123,
    private String marryState;// 1,
    private String diseaseTime;// null,
    private String diagnose;// ,
    private String nickname;// ,
    private String email;// 123123@123213.123123,
    private String seizureTimes;// null,
    private String height;// null,
    private String nearlyDrugs;// null


    public static FollowApply parse(String string) {
        return JSONObject.parseObject(string, FollowApply.class);
    }

    public static List<FollowApply> parseList(String string) {
        return JSONArray.parseArray(string, FollowApply.class);
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDoctorUuid() {
        return doctorUuid;
    }

    public void setDoctorUuid(String doctorUuid) {
        this.doctorUuid = doctorUuid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getApplyUuid() {
        return applyUuid;
    }

    public void setApplyUuid(String applyUuid) {
        this.applyUuid = applyUuid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIllnessDescription() {
        return illnessDescription;
    }

    public void setIllnessDescription(String illnessDescription) {
        this.illnessDescription = illnessDescription;
    }

    public String getIfStart() {
        return ifStart;
    }

    public void setIfStart(String ifStart) {
        this.ifStart = ifStart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstDiagnosis() {
        return firstDiagnosis;
    }

    public void setFirstDiagnosis(String firstDiagnosis) {
        this.firstDiagnosis = firstDiagnosis;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMarryState() {
        return marryState;
    }

    public void setMarryState(String marryState) {
        this.marryState = marryState;
    }

    public String getDiseaseTime() {
        return diseaseTime;
    }

    public void setDiseaseTime(String diseaseTime) {
        this.diseaseTime = diseaseTime;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeizureTimes() {
        return seizureTimes;
    }

    public void setSeizureTimes(String seizureTimes) {
        this.seizureTimes = seizureTimes;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNearlyDrugs() {
        return nearlyDrugs;
    }

    public void setNearlyDrugs(String nearlyDrugs) {
        this.nearlyDrugs = nearlyDrugs;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
}
