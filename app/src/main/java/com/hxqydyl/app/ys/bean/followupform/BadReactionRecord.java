package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class BadReactionRecord extends FollowUpFormOneRecord{

//    开始时间
    private String occurrenceTime;
//    持续时间
    private String dosageTime;
//    症状描述
    private String frequency;
//    影响
    private String impact;

    public String getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(String occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public String getDosageTime() {
        return dosageTime;
    }

    public void setDosageTime(String dosageTime) {
        this.dosageTime = dosageTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }
}
