package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;

import java.util.List;

/**
 * Created by wangxu on 2016/4/20.
 */
public class BaseResponse<T> {
    public int code;
    public T value;
    public String message;
    public Query query;
    public class Query {
        public String success;
        public String message;
        public List<MeasureFormRecord> doctorList;
        public List<MeasureFormRecord> selfList;
    }



}
