package com.hxqydyl.app.ys.bean.register;

/**
 * 科室
 * Created by hxq on 2016/3/22.
 */
public class OfficeBean {

    private String departmentName;
    private String id;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OfficeBean{" +
                "departmentName='" + departmentName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
