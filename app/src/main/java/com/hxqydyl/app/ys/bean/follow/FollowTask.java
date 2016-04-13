package com.hxqydyl.app.ys.bean.follow;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by wangchao36 on 16/4/13.
 * 随访任务
 */
public class FollowTask {

    private String imgUrl;
    private String realName;
    private String applyUuid;
    private String customerUuid;
    private String illnessDescription;
    private String createTime;
    private String doctorUuid;
    private String sex;
    private String age;

    public static List<FollowTask> parseList(String string) {
        return JSONArray.parseArray(string, FollowTask.class);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getIllnessDescription() {
        return illnessDescription;
    }

    public void setIllnessDescription(String illnessDescription) {
        this.illnessDescription = illnessDescription;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
}
