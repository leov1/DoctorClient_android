package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxu on 2016/4/26.
 */
public class PlanInfoListResponse extends BaseResponse<ArrayList<Plan>>{
    private List<Plan> relist;

    public List<Plan> getRelist() {
        return relist;
    }

    public void setRelist(List<Plan> relist) {
        this.relist = relist;
    }
}
