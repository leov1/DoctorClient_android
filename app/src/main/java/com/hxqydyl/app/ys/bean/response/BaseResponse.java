package com.hxqydyl.app.ys.bean.response;

/**
 * Created by wangxu on 2016/4/20.
 */
public class BaseResponse {
    public String value;
    public String mesage;
    public Query query;
    public class Query {
        public String success;
        public String message;
    }



}
