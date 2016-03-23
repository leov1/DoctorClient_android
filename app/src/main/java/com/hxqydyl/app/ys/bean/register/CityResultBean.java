package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * 市
 * Created by hxq on 2016/3/22.
 */
public class CityResultBean {
    private Query query;
    private List<CityBean> cityBeans;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<CityBean> getCityBeans() {
        return cityBeans;
    }

    public void setCityBeans(List<CityBean> cityBeans) {
        this.cityBeans = cityBeans;
    }

    @Override
    public String toString() {
        return "CityResultBean{" +
                "query=" + query +
                ", cityBeans=" + cityBeans +
                '}';
    }
}
