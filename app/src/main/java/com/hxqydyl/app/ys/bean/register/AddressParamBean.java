package com.hxqydyl.app.ys.bean.register;

import java.io.Serializable;

/**
 * 注册时完善信息的参数
 * Created by hxq on 2016/3/24.
 */
public class AddressParamBean implements Serializable {

    private String doctorUuid;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String infirmaryCode;
    private String departments;
    private String speciality;
    private String professional;
    private String synopsis;
    private String telephone;
    private String otherhospital;

    public String getDoctorUuid() {
        return doctorUuid;
    }

    public void setDoctorUuid(String doctorUuid) {
        this.doctorUuid = doctorUuid;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getInfirmaryCode() {
        return infirmaryCode;
    }

    public void setInfirmaryCode(String infirmaryCode) {
        this.infirmaryCode = infirmaryCode;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOtherhospital() {
        return otherhospital;
    }

    public void setOtherhospital(String otherhospital) {
        this.otherhospital = otherhospital;
    }

    @Override
    public String toString() {
        return "AddressParamBean{" +
                "doctorUuid='" + doctorUuid + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", infirmaryCode='" + infirmaryCode + '\'' +
                ", departments='" + departments + '\'' +
                ", speciality='" + speciality + '\'' +
                ", professional='" + professional + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", telephone='" + telephone + '\'' +
                ", otherhospital='" + otherhospital + '\'' +
                '}';
    }
}
