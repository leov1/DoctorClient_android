package com.hxqydyl.app.ys.bean.register;

/**
 * 省份信息
 * Created by hxq on 2016/3/21.
 */
public class ProvinceInfo {

    private String code;
    private String provinceName;

    public ProvinceInfo() {
    }

    public ProvinceInfo(String code, String provinceName) {
        this.code = code;
        this.provinceName = provinceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "privenceInfo{" +
                "code='" + code + '\'' +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
