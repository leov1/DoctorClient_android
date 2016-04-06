package com.hxqydyl.app.ys.bean.homepage;

import com.hxqydyl.app.ys.bean.Query;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/4/5.
 */
public class PageIconResult {
    private Query query;
    private ArrayList<PageIconBean> pageIconBeans;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public ArrayList<PageIconBean> getPageIconBeans() {
        return pageIconBeans;
    }

    public void setPageIconBeans(ArrayList<PageIconBean> pageIconBeans) {
        this.pageIconBeans = pageIconBeans;
    }

    @Override
    public String toString() {
        return "PageIconResult{" +
                "query=" + query +
                ", pageIconBeans=" + pageIconBeans +
                '}';
    }
}
