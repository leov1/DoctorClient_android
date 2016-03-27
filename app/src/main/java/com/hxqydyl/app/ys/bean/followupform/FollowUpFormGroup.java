package com.hxqydyl.app.ys.bean.followupform;

import com.hxqydyl.app.ys.bean.BaseBean;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/27.
 */
public class FollowUpFormGroup extends BaseBean {
    //    记录组类型
    private int formGroupType;
    //    该类型下的记录
    private ArrayList<FollowUpFormOneRecord> records = new ArrayList<FollowUpFormOneRecord>();

    public FollowUpFormGroup() {
    }

    public FollowUpFormGroup(int formGroupType) {
        this.formGroupType = formGroupType;
    }


    public int getFormGroupType() {
        return formGroupType;
    }

    public void setFormGroupType(int formGroupType) {
        this.formGroupType = formGroupType;
    }

    public ArrayList<FollowUpFormOneRecord> getRecords() {
        return records;
    }

    public void addRecord(FollowUpFormOneRecord record){
        records.add(record);
    }

    public static class Type{
        public static final int ILLNESS_CHANGE = 1;
        public static final int WEIGHT_RECORD = 2;
        public static final int OTHER_CHECK_RECORD = 3;
        public static final int EAT_MED_RECORD = 4;
        public static final int MEASURE_SELF_RECORD = 5;
        public static final int DOC_MEASURE_RECORD = 6;
    }
}
