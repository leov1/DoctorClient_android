package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class EatMedRecord extends FollowUpFormOneRecord{

//    药名
    private String productName;
//    服药开始时间
    private String medicalDateBegin;
//    服药结束时间
    private String medicalDateEnd;
//    单次服用量
    private String singleAmount;
    //服药数量
    public String dosage;
    //单位
    public String unit;
//    频率
    private String frequency;
//    服用方法
    private String directions;

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMedicalDateBegin() {
        return medicalDateBegin;
    }

    public void setMedicalDateBegin(String medicalDateBegin) {
        this.medicalDateBegin = medicalDateBegin;
    }

    public String getMedicalDateEnd() {
        return medicalDateEnd;
    }

    public void setMedicalDateEnd(String medicalDateEnd) {
        this.medicalDateEnd = medicalDateEnd;
    }

    public String getSingleAmount() {
        return dosage+"/"+unit;
    }

    public void setSingleAmount(String singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
