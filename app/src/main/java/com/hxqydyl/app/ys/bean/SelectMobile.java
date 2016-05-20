package com.hxqydyl.app.ys.bean;

/**
 * Created by wangxu on 2016/5/19.
 */
public class SelectMobile {
    private String msg;
    private int id;
    private Patient data;

    public Patient getData() {
        return data;
    }

    public void setData(Patient data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
