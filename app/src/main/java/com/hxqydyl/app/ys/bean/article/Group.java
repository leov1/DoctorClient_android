package com.hxqydyl.app.ys.bean.article;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/3/28.
 */
public class Group {
    private ArrayList<Child> children;
    private boolean isChecked;
    private String groupName;
    private String groupId;

    public Group() {
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

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
                "children=" + children +
                ", isChecked=" + isChecked +
                ", groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
