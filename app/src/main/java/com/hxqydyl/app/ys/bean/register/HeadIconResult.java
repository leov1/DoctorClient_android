package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

/**
 * 上传头像
 * Created by hxq on 2016/3/18.
 */
public class HeadIconResult {

    private Query query;
    private String imageUrl;
    private String smallUrl;
    private String icon;
    private String smallImage;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    @Override
    public String toString() {
        return "HeadIconResult{" +
                "query=" + query +
                ", imageUrl='" + imageUrl + '\'' +
                ", smallUrl='" + smallUrl + '\'' +
                ", icon='" + icon + '\'' +
                ", smallImage='" + smallImage + '\'' +
                '}';
    }
}
