package com.hxqydyl.app.ys.bean.followupform;


/**
 * Created by white_ash on 2016/3/27.
 */
public class OtherCheckRecord extends FollowUpFormOneRecord{
//    检查项目名称
    private String name;
//    检查结果
    private CheckItemResult result = new CheckItemResult();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckItemResult getResult() {
        return result;
    }
}
