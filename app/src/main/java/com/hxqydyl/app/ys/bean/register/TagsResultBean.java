package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

import java.util.List;

/**
 * 标签
 * Created by hxq on 2016/3/21.
 */
public class TagsResultBean {

    private Query query;
    private List<TagsBean> tagsBeans;

    public List<TagsBean> getTagsBeans() {
        return tagsBeans;
    }

    public void setTagsBeans(List<TagsBean> tagsBeans) {
        this.tagsBeans = tagsBeans;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "TagsResultBean{" +
                "query=" + query +
                ", tagsBeans=" + tagsBeans +
                '}';
    }
}
