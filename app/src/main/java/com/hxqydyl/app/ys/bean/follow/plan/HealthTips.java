package com.hxqydyl.app.ys.bean.follow.plan;

/**
 * Created by wangchao36 on 16/3/22.
 * 健康小贴士
 */
public class HealthTips {

    private int day = 1;
    private String food;
    private String sport;
    private String sleep;
    private String other;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
