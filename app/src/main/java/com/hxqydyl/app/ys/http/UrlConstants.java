package com.hxqydyl.app.ys.http;

import java.util.Objects;

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
    public static final boolean isTest = false;//    是否是测试包


    //    内网开发ip
    public static final String BASE_IP_DEVELOP = "http://101.201.154.86";
    //    内网开发端口
    public static final String BASE_PORT_DEVELOP = "8080";
    //    内网测试ip
    public static final String BASE_IP_TEST = "";
    //    内网测试端口
    public static final String BASE_PORT_TEST = "";
    //    线上测试ip
    public static final String BASE_IP_ONLINE_TEST = "";
    //    线上测试端口
    public static final String BASE_PORT_ONLINE_TEST = "";
    //    线上发布ip
    public static final String BASE_IP_ONLINE_REALEASE = "";
    //    线上发布端口
    public static final String BASE_PORT_ONLINE_REALEASE = "";

    //    ip地址
    public static final String BASE_IP = isOnline ? (isTest ? BASE_IP_ONLINE_TEST : BASE_IP_ONLINE_REALEASE) : (isTest ? BASE_IP_TEST : BASE_IP_DEVELOP);
    //    端口
    public static final String BASE_PORT = isOnline ? (isTest ? BASE_PORT_ONLINE_TEST : BASE_PORT_ONLINE_REALEASE) : (isTest ? BASE_PORT_TEST : BASE_PORT_DEVELOP);

    // 服务器端api路径
    public static final String SERVER_BASE_API_PATH = "";


    /**
     * 根据传入的api接口短地址，和參數返回拼装后的完整地址
     *
     * @param shortUrl api短地址
     * @param args  url參數
     * @return 完整地址
     */
    public static String getWholeApiUrl(String shortUrl, Object... args) {
        return BASE_IP + ":" + BASE_PORT + SERVER_BASE_API_PATH + String.format(shortUrl,args);
    }

    //上传图片
    public static final String UPLOAD_PIC = "/app/support/common/uploadimg";

//    获取所有的患者与分组信息 (版本号)
    public static final String GET_ALL_PATIENT_AND_GROUP_INFO = "/app/service/customer/%s/getGroupAndCustomer";
    // 删除患者 (版本号)
    public static final String DELETE_PATIENT = "/app/pub/doctor/%s/deleteCustomerByCostomerUuidAndGid";
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

    // 获取医评与自评列表
    public static final String GET_MEASURE_FORM_SELF = "";
    public static final String GET_MEASURE_FORM_DOCTOR = "";

    // 获取病情变化列表（暂缺）
    // 根据id查询病情变化详情（暂缺）

    // 获取患者详细个人信息（暂缺）
    public static final String GET_PATIENT_PERSIONAL_INFO = "/app/service/customer/getCustomerByCostomerUuid";

}
