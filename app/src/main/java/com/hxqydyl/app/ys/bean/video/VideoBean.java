package com.hxqydyl.app.ys.bean.video;

/**
 * Created by alice_company on 2016/5/31.
 */
public class VideoBean {

    private String img;
    private String name;
    private String date;
    private String num;
    private String isStore;

    public VideoBean(String img, String name, String date, String num, String isStore) {
        this.img = img;
        this.name = name;
        this.date = date;
        this.num = num;
        this.isStore = isStore;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }
}
