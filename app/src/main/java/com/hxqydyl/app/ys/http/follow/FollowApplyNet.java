package com.hxqydyl.app.ys.http.follow;

import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by wangchao36 on 16/4/3.
 * 随访申请相关接口医生id：2979745c9af9459289fd6fafa3af8898、
 * 35223f71d7bc4484bc3a677b35b5f07b、
 * 5617d2fd94514ed98fb908f0ef15a859、
 * 566dec740c404ffa813253b8a03f0c1b、 * 739e9e64edb24720aff2a89de967ec19、
 * a9bedf064707480eaaa79388b227adb4
 */
public class FollowApplyNet {

//    public static String baseURL = "http://101.201.154.86:8080/";
//    public static String baseURL = "http://172.168.1.233/";
    public static String baseURL = UrlConstants.BASE_IP + ":" + UrlConstants.BASE_PORT + "/";
    public static boolean myDev = false;

    /**
                * 随访申请列表
                * @param callback
                */
        public static void getVisitApplyList(FollowCallback callback) {
            OkHttpUtils.get().url(FollowApplyNet.baseURL
                    + "app/pub/doctor/1.0/getVisitApplyList")
                    .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 获取随访详细信息
     * @param applyUuid 随访申请的uuid
     * @param callback
     */
    public static void getApplyDetail(String applyUuid, FollowCallback callback) {
        OkHttpUtils.get().url(baseURL + "app/pub/doctor/1.0/getApplyDetail")
                .addParams("applyUuid", applyUuid)
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 医生同意并关联患者
     *
     * @param visitUuid        随访记录的uuid
     * @param visitPreceptUuid 随访方案的uuid
     */
    public static void addVisitRecord(String visitUuid, String visitPreceptUuid, FollowCallback callback) {
        OkHttpUtils.get().url(baseURL + "app/pub/doctor/2.0/addVisitRecord")
//        OkHttpUtils.get().url("http://172.168.1.53/app/pub/doctor/2.0/addVisitRecord")
                .addHeader("Accept","application/json")
                .addParams("visitUuid", visitUuid)
                .addParams("visitPreceptUuid", visitPreceptUuid)
                .build()
                .execute(callback);
    }

    public static void updateVisitRecord(String customerUuid, String visitPreceptUuid, FollowCallback callback) {
        OkHttpUtils.post().url(baseURL + "app/pub/doctor/1.0/updateVisitRecord")
                .addParams("customerUuid", customerUuid)
                .addParams("visitPreceptUuid", visitPreceptUuid)
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 医生拒绝关联患者
     *
     * @param applyUuid    随访申请的uuid
     * @param refuseReason 拒绝理由
     * @param callback
     */
    public static void refuseVivistApply(String applyUuid, String refuseReason,
                                  FollowCallback callback) {
        OkHttpUtils.post().url(baseURL + "app/public/refuseapply/1.0/refuseVivistApply")
                .addParams("applyUuid", applyUuid)
                .addParams("refuseReason", refuseReason)
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

    /**
     * 待处理随访任务-查询列表
     * @param callback
     */
    public static void getProcessedVisitList(FollowCallback callback) {
        OkHttpUtils.get().url(baseURL + "app/pub/doctor/1.0/getProcessedVisitList")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .build()
                .execute(callback);
    }

}
