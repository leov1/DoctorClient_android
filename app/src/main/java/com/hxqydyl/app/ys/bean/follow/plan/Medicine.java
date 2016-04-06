package com.hxqydyl.app.ys.bean.follow.plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 药品
 */
public class Medicine {

    public static final String[] items = {"饭前服用", "饭后服用", "不与餐同服", "随餐服用"};

    private String name = "";
    private boolean timeMorning = false;
    private boolean timeNoon = false;
    private boolean timeNight = false;
    private int foodRelation;
    private List<MedicineDosage> mdList;

    public Medicine() {
        foodRelation = 0;
        mdList = new ArrayList<>();
        mdList.add(new MedicineDosage());
    }

    public Medicine(String name, boolean timeMorning, boolean timeNoon, boolean timeNight,
                    int foodRelation, List<MedicineDosage> mdList) {
        this.name = name;
        this.timeMorning = timeMorning;
        this.timeNoon = timeNoon;
        this.timeNight = timeNight;
        this.foodRelation = foodRelation;
        this.mdList = mdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getFoodRelation() {
        return foodRelation;
    }

    public void setFoodRelation(int foodRelation) {
        this.foodRelation = foodRelation;
    }

    public List<MedicineDosage> getMdList() {
        return mdList;
    }

    public void setMdList(List<MedicineDosage> mdList) {
        this.mdList = mdList;
    }
}
