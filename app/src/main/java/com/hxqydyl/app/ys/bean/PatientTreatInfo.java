package com.hxqydyl.app.ys.bean;

import java.io.Serializable;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientTreatInfo extends BaseBean implements Serializable{
    public static final int TREAT_TYPE_MEN_ZHEN = 1;
    public static final int TREAT_TYPE_ZHU_YUAN = 2;
    public static final int TREAT_TYPE_BIAO_DAN = 3;
    private String time;
    private int treatType;
    private boolean unread;

    public PatientTreatInfo() {
    }

    public PatientTreatInfo(String time, int treatType, boolean unread) {
        this.time = time;
        this.treatType = treatType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTreatType() {
        return treatType;
    }

    public void setTreatType(int treatType) {
        this.treatType = treatType;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
