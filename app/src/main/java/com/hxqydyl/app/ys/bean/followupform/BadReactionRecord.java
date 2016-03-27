package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class BadReactionRecord extends FollowUpFormOneRecord{
//    开始时间
    private String firstTime;
//    持续时间
    private String durationTime;
//    症状描述
    private String symptomsDecription;
//    影响
    private String effect;

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getSymptomsDecription() {
        return symptomsDecription;
    }

    public void setSymptomsDecription(String symptomsDecription) {
        this.symptomsDecription = symptomsDecription;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
