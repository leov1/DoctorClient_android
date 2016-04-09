package com.hxqydyl.app.ys.bean.follow.plan;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 * 检查周期
 */
public class CheckSycle {

    public static final String[] cycleItem1 = {"1周", "2周", "3周", "4周"};
    public static final String[] cycleItem2 = {"1周", "2周", "4周", "8周"};

    private String name;
    private String period;   //周期

    public CheckSycle() {
    }

    public CheckSycle(String name, String sycle) {
        this.name = name;
        this.period = sycle;
    }

    public static String list2json(List<CheckSycle> list) {
        if (list == null || list.size() == 0) {
            return  "[]";
        }
        return JSONArray.toJSONString(list);
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
