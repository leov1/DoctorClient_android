package com.hxqydyl.app.ys.bean.video;

/**
 * Created by alice_company on 2016/5/31.
 */
public class CommentBean {
    private String name;
    private String date;
    private String content;

    public CommentBean(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
