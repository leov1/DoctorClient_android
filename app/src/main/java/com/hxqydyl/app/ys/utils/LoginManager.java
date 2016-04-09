package com.hxqydyl.app.ys.utils;

import android.text.TextUtils;

import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.http.JsonUtils;

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
//        return SharedPreferences.getInstance().getString("doctorUuid", "");
        return "a9bedf064707480eaaa79388b227adb4";
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

    /**
     * 获取医生信息
     * @return
     */
    public static DoctorInfoNew getDoctorInfo(){
        String str = SharedPreferences.getInstance().getString(SharedPreferences.HOME_DOCTOR_INFO_CACHE,"");
        if (!TextUtils.isEmpty(str)){
            DoctorInfoNew doctorInfoNew = JsonUtils.JsonDoctorInfoNew(StringUtils.cutoutBracketToString(str)).getDoctorInfo();
            return doctorInfoNew;
        }
        return null;
    }
}
