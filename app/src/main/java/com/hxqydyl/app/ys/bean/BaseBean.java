package com.hxqydyl.app.ys.bean;

import java.io.Serializable;

/**
 * Created by hxq on 2016/2/27.
 */
public abstract class BaseBean implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(BaseBean baseBean) {
        if (baseBean != null && baseBean.getId() != null && baseBean.getId().equals(this.getId()))
            return true;
        return false;
    }
}
