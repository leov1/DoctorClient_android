package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class MeasureFormRecord extends FollowUpFormOneRecord{
    private int type=2;
//    量表名字
    private String subject;
//    测量所得分数
    private String score;
//    测试结果
    private String analys;
//    结果说明
    private String resultId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAnalys() {
        return analys;
    }

    public void setAnalys(String analys) {
        this.analys = analys;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public static class Type{
        public static final int SELF = 1;  // 自评
        public static final int DOCTOR = 2; // 医评
    }
}
