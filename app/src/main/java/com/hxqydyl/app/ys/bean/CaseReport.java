package com.hxqydyl.app.ys.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class CaseReport extends BaseBean {
    //    病历类型
    private String type;
    //    就诊时间
    private String seeDoctorTime;
    //    住院时间
    private String inHospitalTime;
    //    出院时间
    private String outHospitalTime;
    //    医院名
    private String hospitalName;
    //    医生名
    private String doctorName;
    //    照片
    private ArrayList<Pic> pics = new ArrayList<Pic>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeeDoctorTime() {
        return seeDoctorTime;
    }

    public void setSeeDoctorTime(String seeDoctorTime) {
        this.seeDoctorTime = seeDoctorTime;
    }

    public String getInHospitalTime() {
        return inHospitalTime;
    }

    public void setInHospitalTime(String inHospitalTime) {
        this.inHospitalTime = inHospitalTime;
    }

    public String getOutHospitalTime() {
        return outHospitalTime;
    }

    public void setOutHospitalTime(String outHospitalTime) {
        this.outHospitalTime = outHospitalTime;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public ArrayList<Pic> getPics() {
        return pics;
    }

    public void addPic(Pic pic) {
        this.pics.add(pic);
    }
}
