package com.hxqydyl.app.ys.bean;

import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/4.
 */
public class FollowUpForm extends BaseBean {
    private ArrayList<FollowUpFormGroup> recordGroups = new ArrayList<FollowUpFormGroup>();

    public ArrayList<FollowUpFormGroup> getRecordGroups() {
        return recordGroups;
    }

    public void addRecordGroup(FollowUpFormGroup group) {
        this.recordGroups.add(group);
    }
}
