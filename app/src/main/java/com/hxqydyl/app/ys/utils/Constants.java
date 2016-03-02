package com.hxqydyl.app.ys.utils;

/**
 * 常量
 * Created by hxq on 2016/3/1.
 */
public class Constants {

    private static String url = "http://172.168.1.54";
    public static String phone = "10000000000";
    public static String password = "000000";
    public static String LOGIN_URL = url+"/app/pub/doctor/gotoLogin";

    //获取医生基本信息（新添加）
    public static  String GET_DOCTOR_INFO = url+"/app/service/doctor/getDoctorInfo";
    //获取医院
    public static String GET_HOSPITAL = url+"/app/service/doctor/getHospital";
}
