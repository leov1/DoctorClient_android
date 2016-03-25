package com.hxqydyl.app.ys.bean;

/**
 * Created by hxq on 2016/3/25.
 */
public class QueryResultBean {
    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "QueryResultBean{" +
                "query=" + query +
                '}';
    }
}
