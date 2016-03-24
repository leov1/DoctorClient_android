package com.hxqydyl.app.ys.utils;

/**
 * Created by hxq on 2016/3/3.
 */
public class LoginManager {

    /**
     * 判断是否登陆
     * @return
     */
    public static Boolean isHasLogin(){
        return SharedPreferences.getInstance().getBoolean(SharedPreferences.KEY_LOGIN_TYPE, false);
    }

    /**
     * 设置登陆状态
     * @param b
     */
    public static void setLoginStatus(Boolean b){
        SharedPreferences.getInstance().putBoolean(SharedPreferences.KEY_LOGIN_TYPE, b);
    }

    /**
     * 获取医生uuid
     * @return
     */
    public static String getDoctorUuid(){
        return SharedPreferences.getInstance().getString("doctorUuid", "");
    }

    /**
     * 存储医生uuid
     * @param doctorUuid
     */
    public static void setDoctorUuid(String doctorUuid){
        SharedPreferences.getInstance().putString("doctorUuid", doctorUuid);
    }
}
