package com.hxqydyl.app.ys.bean.followupform;

import com.hxqydyl.app.ys.bean.BaseBean;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/27.
 */
public class IllnessChangeRecord extends BaseBean {
    private String description;
    private String status;
    private String time;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
