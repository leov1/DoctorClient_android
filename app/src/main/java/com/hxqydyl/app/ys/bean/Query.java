package com.hxqydyl.app.ys.bean;

/**
 * Created by hxq on 2016/3/1.
 */
public class Query {
    private String success;
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Query{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
