package com.hxqydyl.app.ys.utils;

/**
 * 常量
 * Created by hxq on 2016/3/1.
 */
public class Constants {

    public static String CALLBACK = "xch";

    //请求网络成功
    public static int REQUEST_SUCCESS = 1100;
    //请求网络失败
    public static int REQUEST_FAIL = 1101;

    private static String url = /*"http://172.168.1.57";*/"http://172.168.1.30";
//  private static String url = "http://119.254.32.92:4097";
    public static String phone = "10000000000";
    public static String password = "000000";
    public static String LOGIN_URL = url+"/app/pub/doctor/gotoLogin";


    //手机获取验证码
    public static String GET_VERIFICATION_CODE=url+"/app/pub/doctor/getVerificationCode";
   //手机新用户第一步
    public static String REGISTER_ONE = url+"/app/pub/doctor/registerOne";
   //第二步
    public static String REGISTER_TWO = url+"/app/pub/doctor/registerTwo";
   //第三步
    public static String REGISTER_THREE = url+"/app/pub/doctor/registerThree";

    //获取医生基本信息（新添加）
    public static  String GET_DOCTOR_INFO = url+"/app/service/doctor/getDoctorInfo";
    //获取医院
    public static String GET_HOSPITAL = url+"/app/service/doctor/getHospital";
    //上传图片
    public static String UPLOAD_IMAGE = url+"/app/pub/doctor/uploadIcon";

    //获取阅读列表
    public static String GET_READING = url +"/html/thedoctorinformation/index.shtml";
    //获取讲堂列表信息
    public static String GET_VIDEOS = url+"/html/lecture/all_course.shtml";

    public static final String GET_PATIENT_GROUP = url + "/app/service/customer/%s/getCaseGroupByDoctorId";
}
