package com.hxqydyl.app.ys.bean.register;

/**
 * Created by hxq on 2016/3/1.
 * 医生信息bean
 */
public class DoctorInfo {

    private int income;
    private String address;
    private String doctorUuid;
    private String sex;
    private int visitNum;
    private int customerNum;
    private String sate;
    private String synopsis;
    private String doctorIcon;
    private String professional;
    private String doctorName;
    private String departmentLine;
    private String hospital;
    private String department;
    private String territory;

    public DoctorInfo() {
    }

    public DoctorInfo(int income, String address, String doctorUuid, String sex, int visitNum, int customerNum, String sate, String synopsis, String doctorIcon, String professional, String doctorName, String departmentLine, String territory, String department, String hospital) {
        this.income = income;
        this.address = address;
        this.doctorUuid = doctorUuid;
        this.sex = sex;
        this.visitNum = visitNum;
        this.customerNum = customerNum;
        this.sate = sate;
        this.synopsis = synopsis;
        this.doctorIcon = doctorIcon;
        this.professional = professional;
        this.doctorName = doctorName;
        this.departmentLine = departmentLine;
        this.territory = territory;
        this.department = department;
        this.hospital = hospital;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartmentLine() {
        return departmentLine;
    }

    public void setDepartmentLine(String departmentLine) {
        this.departmentLine = departmentLine;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getDoctorIcon() {
        return doctorIcon;
    }

    public void setDoctorIcon(String doctorIcon) {
        this.doctorIcon = doctorIcon;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSate() {
        return sate;
    }

    public void setSate(String sate) {
        this.sate = sate;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDoctorUuid() {
        return doctorUuid;
    }

    public void setDoctorUuid(String doctorUuid) {
        this.doctorUuid = doctorUuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }


    @Override
    public String toString() {
        return "DoctorInfo{" +
                "income=" + income +
                ", address='" + address + '\'' +
                ", doctorUuid='" + doctorUuid + '\'' +
                ", sex='" + sex + '\'' +
                ", visitNum=" + visitNum +
                ", customerNum=" + customerNum +
                ", sate='" + sate + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", doctorIcon='" + doctorIcon + '\'' +
                ", professional='" + professional + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", departmentLine='" + departmentLine + '\'' +
                ", hospital='" + hospital + '\'' +
                ", department='" + department + '\'' +
                ", territory='" + territory + '\'' +
                '}';
    }
}
