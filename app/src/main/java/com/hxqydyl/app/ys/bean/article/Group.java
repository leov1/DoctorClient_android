package com.hxqydyl.app.ys.bean.article;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/3/28.
 */
public class Group {
    private String id;
    private String title;
    private ArrayList<Child> children;
    private boolean isChecked;

    public Group(String id, String title) {
        this.title = title;
        children = new ArrayList<Child>();
        this.isChecked = false;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void addChildrenItem(Child child) {
        children.add(child);
    }

    public int getChildrenCount() {
        return children.size();
    }

    public Child getChildItem(int index) {
        return children.get(index);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", children=" + children +
                ", isChecked=" + isChecked +
                '}';
    }
}
