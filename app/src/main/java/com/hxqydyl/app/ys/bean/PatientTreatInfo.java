package com.hxqydyl.app.ys.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientTreatInfo extends BaseBean implements Serializable {
    public static final int TREAT_TYPE_MEN_ZHEN = 1;
    public static final int TREAT_TYPE_ZHU_YUAN = 2;
    public static final int TREAT_TYPE_BIAO_DAN = 3;
    private String dt;
    private int treatType;
    private boolean unread;
    private String uuid;
    private String type;
    private String visitState;
    private String caseCategoryType;

    public String getCaseCategoryType() {
        return caseCategoryType;
    }

    public void setCaseCategoryType(String caseCategoryType) {
        this.caseCategoryType = caseCategoryType;
    }

    public String getVisitState() {
        return visitState;
    }

    public void setVisitState(String visitState) {
        this.visitState = visitState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;

    }

    @Override
    public String getId() {
        if (!TextUtils.isEmpty(uuid))
            return uuid;
        return super.getId();
    }

    public PatientTreatInfo() {
    }

    public PatientTreatInfo(String dt, int treatType, boolean unread) {
        this.dt = dt;
        this.treatType = treatType;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getTreatType() {
        if(!TextUtils.isEmpty(type)){


        if (type.equals("0")) {
            if ("1".equals(caseCategoryType)) {
//                门诊
                setTreatType(PatientTreatInfo.TREAT_TYPE_MEN_ZHEN);
            } else if ("0".equals(caseCategoryType)) {
//                住院
                setTreatType(PatientTreatInfo.TREAT_TYPE_ZHU_YUAN);
            }
        } else if (type.equals("1")) {
            setTreatType(PatientTreatInfo.TREAT_TYPE_BIAO_DAN);
            if ("1".equals(caseCategoryType)) {
                //已读
                setUnread(false);
            } else if ("0".equals(caseCategoryType)) {
                //未读
                setUnread(true);
            }
        }
        }
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
