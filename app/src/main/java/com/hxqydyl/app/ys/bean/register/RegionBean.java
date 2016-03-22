package com.hxqydyl.app.ys.bean.register;

/**
 * 区县
 * Created by hxq on 2016/3/21.
 */
public class RegionBean {

    private String regionName;
    private String code;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "regionName='" + regionName + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
