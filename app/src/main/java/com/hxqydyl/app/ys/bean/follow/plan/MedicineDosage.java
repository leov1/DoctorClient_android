package com.hxqydyl.app.ys.bean.follow.plan;

import java.io.Serializable;

/**
 * Created by wangchao36 on 16/3/22.
 * 药品用量
 */
public class MedicineDosage implements Serializable {

    private String day;
    private String size;
    private String unit;

    public MedicineDosage() {
    }

    public MedicineDosage(String day, String size, String unit) {
        this.day = day;
        this.size = size;
        this.unit = unit;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
