package com.hxqydyl.app.ys.bean.register;

/**
 * 市
 * Created by hxq on 2016/3/22.
 */
public class CityBean {

    private String cityName;
    private String code;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "cityName='" + cityName + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
