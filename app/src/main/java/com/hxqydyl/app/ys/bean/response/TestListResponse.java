package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.follow.plan.Scale;

import java.util.List;

/**
 * Created by wangxu on 2016/4/27.
 */
public class TestListResponse extends BaseResponse{
    private List<Scale> relist;

    public List<Scale> getRelist() {
        return relist;
    }

    public void setRelist(List<Scale> relist) {
        this.relist = relist;
    }
}
