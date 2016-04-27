package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.follow.FollowApply;

import java.util.List;

/**
 * Created by wangxu on 2016/4/25.
 * 随访申请列表
 */
public class FollowUserApplyResponse extends  BaseResponse{
    private List<FollowApply> relist;

    public List<FollowApply> getRelist() {
        return relist;
    }

    public void setRelist(List<FollowApply> relist) {
        this.relist = relist;
    }
}
