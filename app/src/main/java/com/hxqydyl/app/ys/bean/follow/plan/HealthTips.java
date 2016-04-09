package com.hxqydyl.app.ys.bean.follow.plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 健康小贴士
 */
public class HealthTips {

    private String uuid;
    private String day = "";
    private String food;
    private String sport;
    private String sleep;
    private String other;

    public static List<HealthTips> parse(JSONArray jsonArray) throws JSONException {
        List<HealthTips> list = new ArrayList<>();
        if (jsonArray == null) return list;
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            HealthTips tips = new HealthTips();
            tips.setUuid(obj.getString("uuid"));
            tips.setDay(obj.getString("day"));
            tips.setFood(obj.getString("diet"));
            tips.setSport(obj.getString("sports"));
            tips.setSleep(obj.getString("sleep"));
            tips.setOther(obj.getString("rest"));
            list.add(tips);
        }
        return list;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
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
