package com.hxqydyl.app.ys.utils;

import android.text.TextUtils;

import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.http.JsonUtils;

/**
 * Created by hxq on 2016/3/3.
 */
public class LoginManager {

    public static boolean isQuit_home = false;
    public static boolean isQuit_myPatient = false;
    public static boolean isQuit_myTask = false;
    public static boolean isQuit_user = false;

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

    public static void setRegisterUuid(String doctorUuid){
        SharedPreferences.getInstance().putString("registerUuid", doctorUuid);
    }

    public static String getRegisterUuid() {
        return SharedPreferences.getInstance().getString("registerUuid", "");
    }

    /**
     * 退出登陆时，清空
     */
    public static void quitLogin() {
        SharedPreferences.getInstance().putString("doctorUuid", "");
        SharedPreferences.getInstance().putString(SharedPreferences.HOME_DOCTOR_INFO_CACHE, "");
        isQuit_home = true;
        isQuit_myPatient = true;
        isQuit_myTask = true;
        isQuit_user=true;
    }

    /**
     * 获取医生信息
     *
     * @return
     */
    public static DoctorInfoNew getDoctorInfo() {
        String str = SharedPreferences.getInstance().getString(SharedPreferences.HOME_DOCTOR_INFO_CACHE, "");
        if (!TextUtils.isEmpty(str)) {
            DoctorInfoNew doctorInfoNew = JsonUtils.JsonDoctorInfoNew(StringUtils.cutoutBracketToString(str)).getDoctorInfo();
            return doctorInfoNew;
        }
        return null;
    }
    public interface OnLoginSuccess {
        void onLoginSuccess(String doctorUuid);
        void onLoginfail();
    }

}
