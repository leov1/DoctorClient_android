package com.hxqydyl.app.ys.bean.register;

/**
 * 标签
 * Created by hxq on 2016/3/21.
 */
public class TagsBean {

    private String tagName;
    private String tagUuid;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagUuid() {
        return tagUuid;
    }

    public void setTagUuid(String tagUuid) {
        this.tagUuid = tagUuid;
    }

    @Override
    public String toString() {
        return "TagsBean{" +
                "tagName='" + tagName + '\'' +
                ", tagUuid='" + tagUuid + '\'' +
                '}';
    }
}
