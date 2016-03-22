package com.hxqydyl.app.ys.bean.plan;

import java.io.Serializable;

/**
 * Created by wangchao36 on 16/3/23.
 */
public class Plan implements Serializable {

    private String name;

    public Plan(String name) {
        this.name = name;
    }

    public Plan() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
