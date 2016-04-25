package com.hxqydyl.app.ys.bean.follow;

import com.hxqydyl.app.ys.bean.followupform.BadReactionRecord;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;

import java.util.List;

/**
 * Created by wangxu on 2016/4/21.
 */
public class VisitRecord {
    private Other other;//其他情况
    private String applyState;//前段无用值
    private IllnessRecord illnessRecord;//病情变化
    private Weight weight;//体重记录
    private List<CheckResult> checkResult;//其他检查及结果
    private String uuid;//本次表单uuid
    private SleepOrEat sleep;//睡眠情况
    private String preceptUuid;//？uuid
    private String customerUuid;//??
    private List<EatMedRecord> doctorAdvice;//服药记录
    private BadReactionRecord drugReaction;//不良药物反应
    private String createTime;//创建时间
    private String serviceStaffUuid;
    private String id;//id
    private String visitState;//?
    private SleepOrEat eat;//吃饭情况

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public IllnessRecord getIllnessRecord() {
        return illnessRecord;
    }

    public void setIllnessRecord(IllnessRecord illnessRecord) {
        this.illnessRecord = illnessRecord;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public List<CheckResult> getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(List<CheckResult> checkResult) {
        this.checkResult = checkResult;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public SleepOrEat getSleep() {
        return sleep;
    }

    public void setSleep(SleepOrEat sleep) {
        this.sleep = sleep;
    }

    public String getPreceptUuid() {
        return preceptUuid;
    }

    public void setPreceptUuid(String preceptUuid) {
        this.preceptUuid = preceptUuid;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public List<EatMedRecord> getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(List<EatMedRecord> doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public BadReactionRecord getDrugReaction() {
        return drugReaction;
    }

    public void setDrugReaction(BadReactionRecord drugReaction) {
        this.drugReaction = drugReaction;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getServiceStaffUuid() {
        return serviceStaffUuid;
    }

    public void setServiceStaffUuid(String serviceStaffUuid) {
        this.serviceStaffUuid = serviceStaffUuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitState() {
        return visitState;
    }

    public void setVisitState(String visitState) {
        this.visitState = visitState;
    }

    public SleepOrEat getEat() {
        return eat;
    }

    public void setEat(SleepOrEat eat) {
        this.eat = eat;
    }

    public class IllnessRecord {
        private String newCondition;
        private String previons;

        public String getNewCondition() {
            return newCondition;
        }

        public void setNewCondition(String newCondition) {
            this.newCondition = newCondition;
        }

        public String getPrevions() {
            return previons;
        }

        public void setPrevions(String previons) {
            this.previons = previons;
        }
    }

    public class Other {//其他情况
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public class Weight {//体重
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public class SleepOrEat {
        private String result;
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

}
