package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class MeasureFormRecord extends FollowUpFormOneRecord{
    private int type;
//    量表名字
    private String name;
//    测量所得分数
    private String score;
//    测试结果
    private String result;
//    结果说明
    private String retDescription;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetDescription() {
        return retDescription;
    }

    public void setRetDescription(String retDescription) {
        this.retDescription = retDescription;
    }

    class Type{
        public static final int SELF = 1;  // 自评
        public static final int DOCTOR = 2; // 医评
    }
}
