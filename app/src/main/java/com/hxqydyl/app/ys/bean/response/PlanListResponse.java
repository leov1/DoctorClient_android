package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;

import java.util.List;

/**
 * Created by wangxu on 2016/4/26.
 */
public class PlanListResponse extends BaseResponse {
    private List<PlanBaseInfo> relist;

    public List<PlanBaseInfo> getRelist() {
        return relist;
    }

    public void setRelist(List<PlanBaseInfo> relist) {
        this.relist = relist;
    }
}
