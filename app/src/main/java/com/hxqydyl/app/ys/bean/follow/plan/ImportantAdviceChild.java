package com.hxqydyl.app.ys.bean.follow.plan;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxu on 2016/4/22.
 */
public class ImportantAdviceChild implements Serializable {
    @Expose
    private String medicineUuid;//药物名称
    @Expose
    private String uuid;//方案用药物名称
    @Expose
    private String directions;//时间
    @Expose
    private String dosage;//量剂
    @Expose
    private String frequency;//频率
    @Expose
    private String unit;//暂无作用
    @Expose
    private String food;//服用时间
    private boolean timeMorning;
    private boolean timeNoon;
    private boolean timeNight;

    public ImportantAdviceChild initDate() {
        if (!TextUtils.isEmpty(directions)) {
            setTimeMorning(directions.contains("早"));
            setTimeNoon(directions.contains("中"));
            setTimeNight(directions.contains("晚"));
        }
        if (md == null) {

            md = new ArrayList<>();
            if (TextUtils.isEmpty(dosage) || TextUtils.isEmpty(frequency))
                return this;
            String[] dosages = dosage.split(",");
            String[] frequencys = frequency.split(",");
            for (int j = 0; j < dosages.length; j++) {
                MedicineDosage md = new MedicineDosage();
                md.setDay(frequencys[j]);
                String[] tmp = dosages[j].split("\\|");
                md.setSize(tmp[0]);
                md.setUnit(tmp[1]);
                this.md.add(md);
            }

        }
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ImportantAdviceChild toJsonBean() {
        directions="";
        if (isTimeMorning()) {
            directions = directions + "早";
        }
        if (isTimeNoon()) {
            if (directions.length() > 0) directions = directions + ",中";
            else

                directions = directions + "中";
        }
        if (isTimeNight()) {
            if (directions.length() > 0) directions = directions + ",晚";
            else
            directions = directions + "晚";
        }
        frequency = "";
        dosage = "";
        if (md != null && md.size() >= 0) {
            for (MedicineDosage mds : md) {
                if (frequency.length() > 0) {
                    frequency = frequency + ",";
                    dosage = dosage + ",";
                }
                frequency = frequency + mds.getDay();
                dosage = mds.getSize() + "|" + mds.getUnit();
            }
        }
        return this;
    }

    public boolean isTimeMorning() {
        return timeMorning;
    }

    public void setTimeMorning(boolean timeMorning) {
        this.timeMorning = timeMorning;
    }

    public boolean isTimeNoon() {
        return timeNoon;
    }

    public void setTimeNoon(boolean timeNoon) {
        this.timeNoon = timeNoon;
    }

    public boolean isTimeNight() {
        return timeNight;
    }

    public void setTimeNight(boolean timeNight) {
        this.timeNight = timeNight;
    }

    public List<MedicineDosage> md;

    public List<MedicineDosage> getMd() {
        return md;
    }

    public void setMd(List<MedicineDosage> md) {
        this.md = md;
    }

    public String getMedicineUuid() {
        return medicineUuid;
    }

    public void setMedicineUuid(String medicineUuid) {
        this.medicineUuid = medicineUuid;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;

    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
