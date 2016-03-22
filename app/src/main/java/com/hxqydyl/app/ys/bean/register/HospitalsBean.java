package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.BaseBean;

/**
 * 医院列表
 * Created by hxq on 2016/3/21.
 */
public class HospitalsBean{

    private String id;
    private String hospitalName;

    public HospitalsBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return "HospitalsBean{" +
                "id='" + id + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                '}';
    }
}
