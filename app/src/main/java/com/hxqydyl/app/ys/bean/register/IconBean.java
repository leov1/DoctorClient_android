package com.hxqydyl.app.ys.bean.register;

/**
 * Created by hxq on 2016/3/31.
 */
public class IconBean {

    private String id;
    private String name;
    private int size;
    private String url;
    private String thumbnail;
    private String error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "IconBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
