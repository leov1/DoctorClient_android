package com.hxqydyl.app.ys.bean.article;

import com.hxqydyl.app.ys.bean.Query;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/4/5.
 */
public class ArticleResult {
    private Query query;
    private ArrayList<Group> groups;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "ArticleResult{" +
                "query=" + query +
                ", groups=" + groups +
                '}';
    }
}
