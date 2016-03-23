package com.hxqydyl.app.ys.bean;

import java.io.Serializable;

/**
 * Created by white_ash on 2016/3/24.
 */
public class Pic extends BaseBean implements Serializable{
    private String url;
    private String thumbUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
