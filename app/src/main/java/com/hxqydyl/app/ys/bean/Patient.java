package com.hxqydyl.app.ys.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class Patient extends BaseBean implements Serializable {
    private String phoneNumber;
    private String nick;
    private String name;
    private String age;
    private String sex;
    private String followTime;
    private String birthday;
    private String card;
    private String email;
    private String marriage;
    private String vocation;
    private String address;
    private String diseaseProcess;
    private String firstSeeDoctorTime;
    private String relapse;
    private String relapseTimes;
    private String height;
    private String weight;
    private String useCondition;
    private String description;

    private ArrayList<PatientTreatInfo> patientTreatInfos = new ArrayList<PatientTreatInfo>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
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
}
