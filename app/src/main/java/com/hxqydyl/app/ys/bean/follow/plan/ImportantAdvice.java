package com.hxqydyl.app.ys.bean.follow.plan;

import com.google.gson.annotations.Expose;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangxu on 2016/4/22.
 * 重要医嘱
 */
public class ImportantAdvice implements Serializable {
    private String uuid;
    @Expose
    private String drugReaction;
    @Expose
    private String cureNote;
    @Expose
    private ArrayList<ImportantAdviceChild> child;
    @Expose
    private String serviceStaffUuid;
    @Expose
    private String customerUuid;
    @Expose
    private String visitPreceptUuid;

    public String getServiceStaffUuid() {
        return serviceStaffUuid;
    }

    public void setServiceStaffUuid(String serviceStaffUuid) {
        this.serviceStaffUuid = serviceStaffUuid;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getVisitPreceptUuid() {
        return visitPreceptUuid;
    }

    public void setVisitPreceptUuid(String visitPreceptUuid) {
        this.visitPreceptUuid = visitPreceptUuid;
    }

    public ImportantAdvice toJsonBean(){
        for (int i=0;i<child.size();i++){
            child.set(i,child.get(i).toJsonBean());
        }
        return this;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDrugReaction() {
        return drugReaction;
    }

    public void setDrugReaction(String drugReaction) {
        this.drugReaction = drugReaction;
    }

    public String getCureNote() {
        return cureNote;
    }

    public void setCureNote(String cureNote) {
        this.cureNote = cureNote;
    }

    public ArrayList<ImportantAdviceChild> getChild() {
        return child;
    }

    public void setChild(ArrayList<ImportantAdviceChild> child) {
        this.child = child;
    }
}
