package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * Created by hxq on 2016/3/21.
 */
public class HospitalResultBean {
    private Query query;
    private List<HospitalsBean> list;

    public HospitalResultBean() {
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<HospitalsBean> getList() {
        return list;
    }

    public void setList(List<HospitalsBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HospitalResultBean{" +
                "query=" + query +
                ", list=" + list +
                '}';
    }
}
