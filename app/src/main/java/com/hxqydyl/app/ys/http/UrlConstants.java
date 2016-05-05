package com.hxqydyl.app.ys.http;

/**
 * Created by white_ash on 2016/4/1.
 */
public class UrlConstants {
    /**
     * 开发，打包app时需要关注这两个参数
     * 总体支持四个环境：线下开发,线下测试，线上测试，线上发布
     * 线下开发 isOnline = false,isTest = false;
     * 线下测试 isOnline = false,isTest = true;
     * 线上测试 isOnline = true,isTest = true;
     * 线上发布 isOnline = true,isTest = false;
     */
    public static final boolean isOnline = false;//    是否是线上环境
    public static final boolean isTest = true;//    是否是测试包


    //    内网开发ip
    public static final String BASE_IP_DEVELOP = "http://172.168.1.9";
    //    内网开发端口
    public static final String BASE_PORT_DEVELOP = "";
    //    内网测试ip
    public static final String BASE_IP_TEST = "http://172.168.1.46";
    //    内网测试端口
    public static final String BASE_PORT_TEST = "8080";
    //    线上测试ip
    public static final String BASE_IP_ONLINE_TEST = "http://101.201.154.86";
    //    线上测试端口
    public static final String BASE_PORT_ONLINE_TEST = "8080";
    //    线上发布ip
    public static final String BASE_IP_ONLINE_REALEASE = "http://admin.hxqydyl.com";
    //    线上发布端口
    public static final String BASE_PORT_ONLINE_REALEASE = "";

    //    ip地址
    public static final String BASE_IP = isOnline ? (isTest ? BASE_IP_ONLINE_TEST : BASE_IP_ONLINE_REALEASE) : (isTest ? BASE_IP_TEST : BASE_IP_DEVELOP);
    //    端口
    public static final String BASE_PORT = isOnline ? (isTest ? BASE_PORT_ONLINE_TEST : BASE_PORT_ONLINE_REALEASE) : (isTest ? BASE_PORT_TEST : BASE_PORT_DEVELOP);

    // 服务器端api路径
    public static final String SERVER_BASE_API_PATH = "";

    public static String CALLBACK = "xch";

    /**
     * 根据传入的api接口短地址，和參數返回拼装后的完整地址
     *
     * @param shortUrl api短地址
     * @param args     url參數
     * @return 完整地址
     */
    public static String getWholeApiUrl(String shortUrl, Object... args) {
        return BASE_IP + ":" + BASE_PORT + SERVER_BASE_API_PATH + String.format(shortUrl, args);
    }

    //上传图片
    public static final String UPLOAD_PIC = "/app/support/common/uploadimg";

    // 与患者沟通跳转的H5地址
    public static final String COMMUNICATE_WITH_PATIENT_H5 = "/html/task/consultation_page.shtml?customerUuid=%s";

    //    获取所有的患者与分组信息 (版本号)
    public static final String GET_ALL_PATIENT_AND_GROUP_INFO = "/app/service/customer/%s/getGroupAndCustomer";
    // 删除患者 (版本号)
    public static final String
            DELETE_PATIENT = "/app/service/customer/%s/deleteCustomerByCostomerUuidAndGid";
    //    修改患者所在的分组(版本号)
    public static final String MOVE_PATIENT_TO_OTHER_GROUP = "/app/service/customer/%s/updateCustomerGroup";
    //    获取所有的患者分组(版本号)
    public static final String GET_ALL_PATIENT_GROUP = "/app/service/customer/%s/getCaseGroupByDoctorId";
    //    删除患者分组(版本号)
    public static final String DELETE_PATIENT_GROUP = "/app/service/customer/%s/deleteCaseGroup";
    //    修改患者分组名(版本号)
    public static final String RENAME_PATIENT_GROUP = "/app/service/customer/%s/updateCaseGroup";
    //    添加患者分组(版本号)
    public static final String ADD_PATIENT_GROUP = "/app/service/customer/%s/addCaseGroup";
    //    通过分组查询患者 (版本号)
    public static final String GET_PATIENT_LIST_BY_GROUP = "/app/service/customer/%s/getCustomerListByDoctorUuidAndGroupId";


    // 查询患者的病历或者随访表单记录列表(版本号，医生id,患者id)(医患两端公用接口)(测试数据customerUuid=efec4e3969234184840e37033fc1d3fd，doctorUuid=88888888)
    public static final String GET_PATIENT_TREAT_RECORD = "/mobile/patient/medical/record/%s/getPatientMedicalRecordList";
    //    给随访患者添加病历 (版本号)（医生端随访模块）（测试数据customerUuid=efec4e3969234184840e37033fc1d3fd，doctorUuid=88888888）
    public static final String ADD_CASE_REPORT_FOR_PATIENT = "/mobile/doctor/visit/medicalrecord/%s/save";
    //    查询患者病历详情(版本号)(医患两端公用接口)(测试数据medicalRecordUuid = icalRecord0000000311)
    public static final String GET_PATIENT_CASE_REPORT_DETAILS = "/mobile/patient/medical/record/%s/getPatientMedicalRecord";
    // 查询患者所填写的随访表单详情 (版本号，表单uuid)（医生端随访模块）（1）Accept = application/json；
    public static final String GET_FOLLOW_UP_FORM_DETAILS = "/mobile/doctor/visit/visitrecord/%s/view/%s";

    // 获取病情变化列表（版本号）
    public static final String GET_ILLNESS_CHANGE_HISTORY_LIST = "/mobile/doctor/visit/illness/%s/search";
    // 根据id查询病情变化详情（版本号，变化记录id）
    public static final String GET_ILLNESS_CHANGE_DETAILS = "/mobile/doctor/visit/illness/%s/view/%s";

    // 获取患者详细个人信息（版本号）
    public static final String GET_PATIENT_PERSIONAL_INFO = "/mobile/doctor/visit/visitrecord/%s/getCustomerInfo";

    //患者分组
    public static String GET_PATIENT_GROUP = "/app/service/customer/1.0/getGroupAndCustomer";
    //医生群发消息给患者
    public static String ADD_INNER_MSG = "/app/service/customer/1.0/addInnerMessage";

    //登陆
    public static String LOGIN_URL = "/app/pub/doctor/gotoLogin";

    //获取轮播图
    public static String GET_PLATFORMPIC = "/app/pub/doctor/getPlatformPic";

    //手机获取验证码
    public static String GET_VERIFICATION_CODE = "/app/pub/doctor/getVerificationCode";
    //手机新用户第一步
    public static String REGISTER_ONE = "/app/pub/doctor/registerOne";
    //第二步
    public static String REGISTER_TWO = "/app/pub/doctor/registerTwo";
    //第三步
    public static String REGISTER_THREE = "/app/pub/doctor/registerThree";
    //修改密码
    public static String UPDATE_PASSWORD = "/app/pub/doctor/updatePassword";
    //退出登陆
    public static String STAFF_OUT_OF = "/app/pub/doctor/staffOutOf";

    //获取医生基本信息（新添加）
    public static String GET_DOCTOR_INFO = "/app/service/doctor/getDoctorInfo";

    //获取省
    public static String GET_PROVINCE = "/app/service/doctor/getProvince";
    //获取市
    public static String GET_CITY = "/app/service/doctor/getCity";
    //获取区县
    public static String GET_REGION = "/app/service/doctor/getRegion";
    //获取医院
    public static String GET_HOSPITAL = "/app/service/doctor/getHospital";
    //获取科室
    public static String GET_DEPARTMENT = "/app/service/doctor/getDepartment";
    //获取标签
    public static String GET_TAGS = "/app/service/doctor/getTags";

    //上传图片
    public static String UPLOAD_IMAGE = "/app/pub/doctor/uploadIcon";
    //上传完善用户图片信息
    public static String SAVE_USER_ICON_LIST = "/app/service/doctor/saveUserIconList";
    //上传图片集
    public static String UPLOAD_IMGS = "/app/support/common/uploadimg";

    //获取阅读列表
    public static String GET_READING = "/html/thedoctorinformation/index.shtml";/*"http://admin.hxqydyl.com/html/thedoctorinformation/index.shtml";*/
    //获取讲堂列表信息
    public static String GET_VIDEOS = "/html/lecture/all_course.shtml";
    //获取诊所
    public static String GET_CLINIC = "/html/clinic/index.shtml";
    //我的患者
    public static String MY_PATIENT = "/html/mypatient/index.shtml";
    //我的任务
    public static String MY_TASK = "/html/task/my_task.shtml";
    //个人中心
    public static String USER_INFO = "/html/user/index.shtml?doctorUuid=";
    //患教库
    public static String PATIENT_EDUCATION = "/html/follow_up/toolbox/patient_education.shtml";
    //二维码页面
    public static String CURPAGE = "/html/user/personal_curpage.shtml";
    //退出登陆
    public static String USER_SETTING = "/html/user/set.shtml";
    //版本更新
    public static String UPDATE = "/version/app/1";
    // 随访表单-重要医嘱-查询
    public static String GET_ADVICE_SEARCH = "/mobile/doctor/visit/advice/%s/search/%s/%s";
    // 随访表单-重要医嘱-添加
    public static String GET_ADVICE_SAVE = "/mobile/doctor/visit/advice/%s/save";
    //获取随访申请
    public static String GET_VISIT_APPLYLIST = "/app/pub/doctor/%s/getVisitApplyList";
    //获取随访申请详情
    public static String GET_APPLY_DETAIL = "/app/pub/doctor/%s/getApplyDetail";
    //拒绝随访申请
    public static String REFUSE_VIVIST_APPLY = "/app/public/refuseapply/%s/refuseVivistApply";
    //接受并关联
    public static String ADD_VISIT_RECORD = "/app/pub/doctor/%s/addVisitRecord";
    //更新用户分组
//    public static String UPDATE_CUSTOMER_GROUP = "/app/service/customer/%s/updateCustomerGroup";
    //获取我的随访列表
    public static String GET_MYVISIT_PRECEPTLIST = "/app/pub/doctor/%s/getMyVisitPreceptList";
    //更新关联信息
    public static String UPDATE_VISIT_RECORD = "/app/pub/doctor/%s/updateVisitRecord";
    //获取推荐的随访方案
    public static String GET_RECOMMEND_VISITPRECEPT_BY_DOCTORID = "/app/pub/doctor/%s/getRecommendVisitpreceptByDoctorid";
    //删除随访方案
    public static String DEL_PRECEPT_DETAIL = "/app/pub/doctor/%s/delPreceptDetail";
    //查看随访方案
    public static String VISIT_PRECEPT_DETAIL = "/app/pub/doctor/%s/visitPreceptDetail";
//增加随访方案
public static String ADD_VISIT_PRECEPT = "/app/pub/doctor/%s/addVisitPrecept";
    //编辑随访方案
    public static String EDIT_VISIT_PRECEPT = "/app/pub/doctor/%s/editVisitPrecept";
    // * 获取医评和自评列表
    public static String SELECT_PRECEPT_DETAIL = "/app/pub/doctor/%s/selectPreceptDetail";
    // * 获取医评和自评列表
    public static String GET_PROCESSED_VISITLIST = "/app/pub/doctor/%s/getProcessedVisitList";
    //添加患者
    public static String ADD_CUSTOMER = "/app/service/customer/%s/addCustomer";
   //
    public static String GET_CUSTOMER_VISIT = "app/pub/doctor/%s/getCustomerVisitRecordByUuid";
}
