package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * 区县
 * Created by hxq on 2016/3/21.
 */
public class RegionResultBean {

    private Query query;
    private List<RegionBean> regionBeans;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<RegionBean> getRegionBeans() {
        return regionBeans;
    }

    public void setRegionBeans(List<RegionBean> regionBeans) {
        this.regionBeans = regionBeans;
    }

    @Override
    public String toString() {
        return "RegionResultBean{" +
                "query=" + query +
                ", regionBeans=" + regionBeans +
                '}';
    }
}
