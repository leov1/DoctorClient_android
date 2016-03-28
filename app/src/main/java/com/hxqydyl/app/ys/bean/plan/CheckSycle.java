package com.hxqydyl.app.ys.bean.plan;

/**
 * Created by wangchao36 on 16/3/23.
 * 检查周期
 */
public class CheckSycle {

    public static final String[] cycleItem1 = {"1周", "2周", "3周", "4周"};
    public static final String[] cycleItem2 = {"1周", "2周", "4周", "8周"};

    private String name;
    private String sycle;   //周期

    public CheckSycle(String name, String sycle) {
        this.name = name;
        this.sycle = sycle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSycle() {
        return sycle;
    }

    public void setSycle(String sycle) {
        this.sycle = sycle;
    }
}
