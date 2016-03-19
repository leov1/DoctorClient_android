package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

/**
 * 注册第一步
 * Created by hxq on 2016/3/19.
 */
public class RegisterFirst {

    private Query query;
    private String doctorUuid;
    private String mobile;

    public RegisterFirst() {
    }

    public RegisterFirst(Query query, String doctorUuid, String mobile) {
        this.query = query;
        this.doctorUuid = doctorUuid;
        this.mobile = mobile;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getDoctorUuid() {
        return doctorUuid;
    }

    public void setDoctorUuid(String doctorUuid) {
        this.doctorUuid = doctorUuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "RegisterFirst{" +
                "query=" + query +
                ", doctorUuid='" + doctorUuid + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
