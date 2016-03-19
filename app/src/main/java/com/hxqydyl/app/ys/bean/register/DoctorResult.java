package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

/**
 * 登陆时返回的数据bean
 * Created by hxq on 2016/3/1.
 */
public class DoctorResult {
    private Query query;
    private DoctorInfo serviceStaff;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public DoctorInfo getServiceStaff() {
        return serviceStaff;
    }

    public void setServiceStaff(DoctorInfo serviceStaff) {
        this.serviceStaff = serviceStaff;
    }

    @Override
    public String toString() {
        return "DoctorResult{" +
                "query=" + query +
                ", serviceStaff=" + serviceStaff +
                '}';
    }
}
