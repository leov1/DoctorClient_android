package com.hxqydyl.app.ys.bean.register;

/**
 * tag提交后返回
 * Created by hxq on 2016/3/24.
 */
public class GoodTagBean {
    private String mobile;
    private String uuid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "GoodTagBean{" +
                "mobile='" + mobile + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
