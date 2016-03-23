package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * 科室
 * Created by hxq on 2016/3/22.
 */
public class OfficeResultBean {

    private Query query;
    private List<OfficeBean> officeBeans;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<OfficeBean> getOfficeBeans() {
        return officeBeans;
    }

    public void setOfficeBeans(List<OfficeBean> officeBeans) {
        this.officeBeans = officeBeans;
    }

    @Override
    public String toString() {
        return "OfficeResultBean{" +
                "query=" + query +
                ", officeBeans=" + officeBeans +
                '}';
    }
}
