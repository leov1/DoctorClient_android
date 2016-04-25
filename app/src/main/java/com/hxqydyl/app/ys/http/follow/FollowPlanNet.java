package com.hxqydyl.app.ys.http.follow;

import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

/**
 * Created by wangchao36 on 16/4/4.
 */
public class FollowPlanNet {

    /**
     * 获取推荐的随访方案列表
     *
     * @param callback
     */
    public static void getRecommendVisitpreceptByDoctorid(FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/getRecommendVisitpreceptByDoctorid")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 获取我的随访方案列表
     *
     * @param callback
     */
    public static void getMyVisitPreceptList(FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/getMyVisitPreceptList")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    public static void addVisitPrecept(Plan plan, FollowCallback callback) throws JSONException {
//        OkHttpUtils.post().url(FollowApplyNet.baseURL
//                + "app/pub/doctor/1.0/addVisitPrecept")
//                .addParams("doctorUuid", LoginManager.getDoctorUuid())        //医生ID
//                .addParams("preceptName", plan.getPreceptName())       //方案名称
//                .addParams("drugTherapy", plan.getDrugTherapy())       //药物不良反应处理
//                .addParams("sideEffects", plan.getSideEffects())   //其他治疗
//                .addParams("doctorAdvice", Medicine.toJson(plan.getMedicineList()))  //药物信息
//                .addParams("ortherMap", CheckSycle.list2json(plan.getOtherCheckSycle())) //其他自定义随访周期
//                .addParams("period", plan.getPeriod())                //随访周期
//                .addParams("electrocardiogram", plan.getElectrocardiogram()) //心电图检查周期
//                .addParams("hepatic", plan.getHepatic())       //肝功能周期
//                .addParams("bloodRoutine", plan.getBloodRoutine())  //血常规周期
//                .addParams("weight", plan.getWeight())        //体重功能周期
//                .addParams("selfTest", Scale.parseIdStr(plan.getSelfTestList()))      ///自评量表
//                .addParams("doctorTest", Scale.parseIdStr(plan.getDoctorTestList()))    //医评量表
//                .addParams("healthGuide", HealthTips.toJson(plan.getHealthTipsList()))    //健康小贴士
//                .build()
//                .execute(callback);
    }

    public static void editVisitPrecept(Plan plan, String doctorAdviceDelete,
                                        String ortherMapDelete, String healthGuideDelete,
                                        FollowCallback callback) throws JSONException {
//        OkHttpUtils.post().url(FollowApplyNet.baseURL
//                + "app/pub/doctor/1.0/editVisitPrecept")
//                .addParams("doctorUuid", LoginManager.getDoctorUuid())        //医生ID
//                .addParams("visitUuid", plan.getVisitUuid())        //医生ID
//                .addParams("preceptName", plan.getPreceptName())       //方案名称
//                .addParams("drugTherapy", plan.getDrugTherapy())       //药物不良反应处理
//                .addParams("sideEffects", plan.getSideEffects())   //其他治疗
//                .addParams("doctorAdvice", Medicine.toJson(plan.getMedicineList()))  //药物信息
//                .addParams("ortherMap", CheckSycle.list2json(plan.getOtherCheckSycle())) //其他自定义随访周期
//                .addParams("period", plan.getPeriod())                //随访周期
//                .addParams("electrocardiogram", plan.getElectrocardiogram()) //心电图检查周期
//                .addParams("hepatic", plan.getHepatic())       //肝功能周期
//                .addParams("bloodRoutine", plan.getBloodRoutine())  //血常规周期
//                .addParams("weight", plan.getWeight())        //体重功能周期
//                .addParams("selfTest", Scale.parseIdStr(plan.getSelfTestList()))      ///自评量表
//                .addParams("doctorTest", Scale.parseIdStr(plan.getDoctorTestList()))    //医评量表
//                .addParams("healthGuide", HealthTips.toJson(plan.getHealthTipsList()))    //健康小贴士
//                .addParams("healthGuideDelete", healthGuideDelete)    //
//                .addParams("doctorAdviceDelete", doctorAdviceDelete)    //
//                .addParams("ortherMapDelete", ortherMapDelete)    //
//                .build()
//                .execute(callback);
    }

    /**
     * 随访方案管理-删除随访方案
     * @param visitUuid 随访id
     * @param callback
     */
    public static void delPreceptDetail(String visitUuid, FollowCallback callback) {
        OkHttpUtils.post().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/delPreceptDetail")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("visitUuid", visitUuid)
                .build()
                .execute(callback);
    }

    /**
     * 查看随访方案
     * @param visitUuid
     * @param callback
     */
    public static void visitPreceptDetail(String visitUuid, FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/visitPreceptDetail")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("visitUuid", visitUuid)
                .build()
                .execute(callback);
    }

    /**
     * 获取医评和自评列表
     *
     * @param type     0——患者自评，1——医生评测
     * @param callback
     */
    public static void selectPreceptDetail(String type, FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/selectPreceptDetail")
                .addParams("type", type)
                .build()
                .execute(callback);
    }

    /**
     * 查询方案已关联患者列表
     * @param preceptUuid
     * @param callback
     */
    public static void getCustomerVisitRecordByUuid(String preceptUuid, FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/pub/doctor/1.0/getCustomerVisitRecordByUuid")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("preceptUuid", preceptUuid)
                .build()
                .execute(callback);
    }

}
