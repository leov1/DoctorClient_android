package com.hxqydyl.app.ys.utils;

import android.text.TextUtils;

/**
 * Created by hxq on 2016/3/3.
 */
public class LoginManager {

    /**
     * 判断是否登陆
     *
     * @return
     */
    public static Boolean isHasLogin() {
        return TextUtils.isEmpty(getDoctorUuid()) ? false : true;
    }

    /**
     * 获取医生uuid
     *
     * @return
     */
    public static String getDoctorUuid() {
        return SharedPreferences.getInstance().getString("doctorUuid", "");
    }

    /**
     * 存储医生uuid
     *
     * @param doctorUuid
     */
    public static void setDoctorUuid(String doctorUuid) {
        SharedPreferences.getInstance().putString("doctorUuid", doctorUuid);
    }

    /**
     * 退出登陆时，清空
     */
    public static void quitLogin(){
        SharedPreferences.getInstance().putString("doctorUuid","");
        SharedPreferences.getInstance().putString(SharedPreferences.HOME_DOCTOR_INFO_CACHE,"");
    }
}
