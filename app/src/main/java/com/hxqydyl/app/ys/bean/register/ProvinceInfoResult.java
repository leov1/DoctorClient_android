package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * 省份bean
 * Created by hxq on 2016/3/21.
 */
public class ProvinceInfoResult {

    private Query query;
    private List<ProvinceInfo> provinceInfos;

    public ProvinceInfoResult() {
    }

    public ProvinceInfoResult(Query query, List<ProvinceInfo> provinceInfos) {
        this.query = query;
        this.provinceInfos = provinceInfos;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<ProvinceInfo> getProvinceInfos() {
        return provinceInfos;
    }

    public void setProvinceInfos(List<ProvinceInfo> provinceInfos) {
        this.provinceInfos = provinceInfos;
    }

    @Override
    public String toString() {
        return "ProvinceInfoResult{" +
                "query=" + query +
                ", provinceInfos=" + provinceInfos +
                '}';
    }
}
