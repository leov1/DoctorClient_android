package com.hxqydyl.app.ys.bean.follow.plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 药品
 * [
 {
 "frequency": "1,2",
 "dosage": "34|mg,22|粒",
 "medicineUuid": "Fff",
 "directions": "1,2,3",
 "food": "饭后服用"
 },
 {
 "frequency": "1,2",
 "dosage": "1|ml,2|mg",
 "medicineUuid": "Ggg",
 "directions": "2,3",
 "food": "饭前服用"
 }
 ]
 */
public class Medicine implements Serializable {

    public static final String[] items = {"饭前服用", "饭后服用", "不与餐同服", "随餐服用"};

    private String uuid;
    private String name;
    private boolean timeMorning;
    private boolean timeNoon;
    private boolean timeNight;
    private String food;
    private List<MedicineDosage> mdList;

    public Medicine() {

    }

    public Medicine(String name, boolean timeMorning, boolean timeNoon, boolean timeNight,
                    String food, List<MedicineDosage> mdList) {
        this.name = name;
        this.timeMorning = timeMorning;
        this.timeNoon = timeNoon;
        this.timeNight = timeNight;
        this.food = food;
        this.mdList = mdList;
    }

    public Medicine copyNew() {
        Medicine m = new Medicine();
        m.setUuid(getUuid());
        m.setName(getName());
        m.setTimeMorning(isTimeMorning());
        m.setTimeNight(isTimeNight());
        m.setTimeNoon(isTimeNoon());
        m.setFood(getFood());
        m.setMdList(getMdList());
        return m;
    }

    public static String toJson(List<Medicine> medicineList) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Medicine m : medicineList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("food", m.getFood());
            jsonObject.put("medicineUuid", m.getUuid());
            StringBuffer sb = new StringBuffer();
            if (m.isTimeMorning()) {
                sb.append("早");
            }
            if (m.isTimeNoon()) {
                if (sb.length() > 0) sb.append(",");
                sb.append("中");
            }
            if (m.isTimeNight()) {
                if (sb.length() > 0) sb.append(",");
                sb.append("晚");
            }
            jsonObject.put("directions", sb.toString());
            StringBuffer sbFrequency = new StringBuffer();
            StringBuffer sbDosage = new StringBuffer();
            for (MedicineDosage md : m.getMdList()) {
                if (sbFrequency.length() > 0)  {
                    sbFrequency.append(",");
                    sbDosage.append(",");
                }
                sbFrequency.append(md.getDay());
                sbDosage.append(md.getSize()).append("|").append(md.getUnit());
            }

            jsonObject.put("frequency", sbFrequency);
            jsonObject.put("dosage", sbDosage);

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public List<MedicineDosage> getMdList() {
        return mdList;
    }

    public void setMdList(List<MedicineDosage> mdList) {
        this.mdList = mdList;
    }
}
