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

    private static String url = /*"http://101.201.154.86:8080";*//*"www.hxqydyl.com";*/"http://192.168.1.38:8080";/*"http://192.168.0.150:8080";*//*"http://172.168.1.30";*//*"http://172.168.1.9";*//*"http://172.168.1.63";*//*"http://172.168.1.10";*/

    //获取阅读列表
    public static String GET_READING = "http://172.168.1.41/html/thedoctorinformation/index.shtml";/*"http://admin.hxqydyl.com/html/thedoctorinformation/index.shtml";*/
    //获取讲堂列表信息
    public static String GET_VIDEOS = "http://172.168.1.41/html/lecture/all_course.shtml";
    //获取诊所
    public static String GET_CLINIC = "http://172.168.1.41/html/clinic/index.shtml";
    //我的患者
    public static String MY_PATIENT = "http://172.168.1.41/html/mypatient/index.shtml";
    //我的任务
    public static String MY_TASK = "http://172.168.1.41/html/task/my_task.shtml";
    //个人中心
    public static String USER_INFO = "http://172.168.1.41/html/user/index.shtml?doctorUuid=";
   //患教库
    public static String PATIENT_EDUCATION = "http://172.168.1.41/html/follow_up/toolbox/patient_education.shtml";
    //二维码页面
    public static String CURPAGE="http://172.168.1.41/html/user/personal_curpage.shtml";
    //退出登陆
    public static String USER_SETTING ="http://172.168.1.41/html/user/set.shtml";

}
