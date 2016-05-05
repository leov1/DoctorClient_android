package com.hxqydyl.app.ys.bean.follow.plan;

import com.hxqydyl.app.ys.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 健康小贴士
 */
public class HealthTips implements Serializable {

    private String uuid;
    private String period = "";
    private String diet;
    private String sports;
    private String sleep;
    private String rest;

    public static ArrayList<HealthTips> parse(JSONArray jsonArray) throws JSONException {
        ArrayList<HealthTips> list = new ArrayList<>();
        if (jsonArray == null) return list;
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            HealthTips tips = new HealthTips();
            tips.setUuid(obj.getString("uuid"));
            tips.setPeriod(obj.getString("period"));
            tips.setDiet(obj.getString("diet"));
            tips.setSports(obj.getString("sports"));
            tips.setSleep(obj.getString("sleep"));
            tips.setRest(obj.getString("rest"));
            list.add(tips);
        }
        return list;
    }

    public static String toJson(List<HealthTips> list) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (HealthTips tmp : list) {
            JSONObject object = new JSONObject();
            if (StringUtils.isNotEmpty(tmp.getUuid())) {
                object.put("uuid", tmp.getUuid());
            }
            object.put("period", tmp.getPeriod());
            object.put("diet", tmp.getDiet());
            object.put("sports", tmp.getSports());
            object.put("sleep", tmp.getSleep());
            object.put("rest", tmp.getRest());
            jsonArray.put(object);
        }
        return jsonArray.toString();
    }



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }
}
