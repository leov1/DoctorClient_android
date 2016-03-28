package com.hxqydyl.app.ys.bean.followupform;

/**
 * Created by white_ash on 2016/3/27.
 */
public class IllnessChange extends FollowUpFormOneRecord{
    private int type;
    private int status;
    private String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Status{
//        无效
        public static final int INVALID = 1;
//        好转
        public static final int BETTER = 2;
//        痊愈
        public static final int BEST = 3;
    }

    public static class Type{
//        疾病
        public static final int ILL = 1;
//        睡眠
        public static final int SLEEP = 2;
//        饮食
        public static final int FOOD = 3;
//        其他
        public static final int OTHER = 4;
    }

}
