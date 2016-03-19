package com.hxqydyl.app.ys.bean;

/**
 * Created by white_ash on 2016/3/18.
 */
public class PatientGroup {
    private String groupId;
    private String groupName;

    public PatientGroup() {
    }

    public PatientGroup(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
