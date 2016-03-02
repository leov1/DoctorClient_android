package com.hxq.hxq.bean;

/**
 * Created by hxq on 2016/3/1.
 */
public class DoctorResultNew {

    private Query query;
    private DoctorInfoNew doctorInfo;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public DoctorInfoNew getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfoNew doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    @Override
    public String toString() {
        return "DoctorResultNew{" +
                "query=" + query +
                ", doctorInfoNew=" + doctorInfo +
                '}';
    }
}
