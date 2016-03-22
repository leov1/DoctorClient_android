package com.hxqydyl.app.ys.bean.plan;

/**
 * Created by wangchao36 on 16/3/22.
 * 药品用量
 */
public class MedicineDosage {

    private int day;
    private int size;
    private String unit;

    public MedicineDosage() {
        unit = "mg";
    }

    public MedicineDosage(int day, int size, String unit) {
        this.day = day;
        this.size = size;
        this.unit = unit;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
