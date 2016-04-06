package com.hxqydyl.app.ys.bean.homepage;

/**
 * 导航轮播图
 * Created by hxq on 2016/4/5.
 */
public class PageIconBean {

    private String note;
    private String imageUrl;
    private String imageNote;
    private String imageUuid;
    private String url;
    private int position;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImageNote() {
        return imageNote;
    }

    public void setImageNote(String imageNote) {
        this.imageNote = imageNote;
    }

    public String getImageUuid() {
        return imageUuid;
    }

    public void setImageUuid(String imageUuid) {
        this.imageUuid = imageUuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PageIconBean{" +
                "note='" + note + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageNote='" + imageNote + '\'' +
                ", imageUuid='" + imageUuid + '\'' +
                ", url='" + url + '\'' +
                ", position=" + position +
                '}';
    }
}
