package com.hxqydyl.app.ys.bean.follow.plan;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 * 检查周期
 */
public class CheckSycle implements Serializable {

    public static final String[] cycleItem1 = {"1周", "2周", "3周", "4周"};
    public static final String[] cycleItem2 = {"1周", "2周", "4周", "8周"};

    private String uuid;
    private String name;
    private String period;   //周期

    public CheckSycle() {
    }

    public CheckSycle(String name, String sycle) {
        this.name = name;
        this.period = sycle;
    }

    public static ArrayList<CheckSycle> parse(org.json.JSONArray jsonArray) throws JSONException {
        ArrayList<CheckSycle> list = new ArrayList<>();
        if (jsonArray == null) return list;
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            CheckSycle cs = new CheckSycle();
            cs.setUuid(obj.getString("uuid"));
            cs.setName(obj.getString("name"));
            cs.setPeriod(obj.getString("period"));
        }
        return list;
    }

    public static String list2json(List<CheckSycle> list) {
        if (list == null || list.size() == 0) {
            return  "[]";
        }
        return JSONArray.toJSONString(list);
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
