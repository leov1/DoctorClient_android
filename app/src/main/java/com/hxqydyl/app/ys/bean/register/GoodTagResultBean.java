package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

/**
 * Created by hxq on 2016/3/24.
 */
public class GoodTagResultBean {
    private Query query;
    private String mobile;
    private String uuid;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "GoodTagResultBean{" +
                "query=" + query +
                ", mobile='" + mobile + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
