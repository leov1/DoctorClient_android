package com.hxqydyl.app.ys.bean.response;

import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.request.BaseRequest;

import java.util.List;

/**
 * Created by wangxu on 2016/4/20.
 */
public class PatientGroupResponse extends BaseResponse{
    private  List<PatientGroup> relist;

    public List<PatientGroup> getRelist() {
        return relist;
    }

    public void setRelist(List<PatientGroup> relist) {
        this.relist = relist;
    }
}
