package com.hxqydyl.app.ys.http.follow;

import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by wangchao36 on 16/4/4.
 */
public class CustomerNet {

    /**
     * 添加患者-保存
     *
     * @param name               姓名
     * @param mobile             手机号
     * @param groupId            分组id
     * @param illnessDescription 详细信息
     * @param callback
     */
    public static void addCustomer(String name, String mobile, String groupId,
                            String illnessDescription, FollowCallback callback) {
        OkHttpUtils.post().url(FollowApplyNet.baseURL + "app/service/customer/1.0/addCustomer")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("name", name)
                .addParams("mobile", mobile)
                .addParams("groupId", groupId)
                .addParams("illnessDescription", illnessDescription)
                .build()
                .execute(callback);
    }

    /**
     * 手机号查询患者
     *
     * @param mobile
     * @param callback
     * 1、手机号是未注册的患者：不变，需医生手动输入数据，并在患者注册时提示，并发送短信提醒患者下载
     * 2、手机号是已经注册的患者端用户：拉取数据并填充
     * 3、手机号已经是医生的随访患者：弹出消息提示框“此手机号已经是您的随访患者！确认”跳转至患者详情页面
     * 4、手机号已经有其他随访医生，弹出提示信息“该患者已有随访医生，暂不能添加该患者
     */
    public static void getCustomerByMobile(String mobile, FollowCallback callback) {
        OkHttpUtils.get().url(FollowApplyNet.baseURL
                + "app/service/customer/1.0/getCustomerByMobile")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("mobile", mobile)
                .build()
                .execute(callback);
    }

    /**
     * 修改患者所在分组
     * @param groupId
     * @param customerUuid
     */
    public static void updateCustomerGroup(String groupId, String customerUuid,
                                           FollowCallback callback) {
        OkHttpUtils.post().url(FollowApplyNet.baseURL
                + "app/service/customer/1.0/updateCustomerGroup")
                .addParams("doctorUuid", LoginManager.getDoctorUuid())
                .addParams("groupId", groupId)
                .addParams("customerUuid", customerUuid)
                .build()
                .execute(callback);
    }

}
