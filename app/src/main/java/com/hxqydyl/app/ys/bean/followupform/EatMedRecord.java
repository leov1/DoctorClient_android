package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class EatMedRecord extends FollowUpFormOneRecord{
//    药名
    private String medName;
//    服药开始时间
    private String startTime;
//    服药结束时间
    private String endTime;
//    单次服用量
    private String singleAmount;
//    频率
    private String rate;
//    服用方法
    private String eatMethod;

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSingleAmount() {
        return singleAmount;
    }

    public void setSmpleAmount(String singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getEatMethod() {
        return eatMethod;
    }

    public void setEatMethod(String eatMethod) {
        this.eatMethod = eatMethod;
    }
}
