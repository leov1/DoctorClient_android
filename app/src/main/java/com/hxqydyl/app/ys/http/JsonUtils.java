package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.utils.StringUtils;

import org.json.JSONException;

/**
 * Created by hxq on 2016/3/3.
 * 解析网络请求返回的数据
 */
public class JsonUtils {

    /**
     * 登陆完成返回
     * @param string
     * @return
     * @throws JSONException
     */
    public static DoctorInfo JsonLoginData(String string){
        if (TextUtils.isEmpty(string)) return  null;
        DoctorResult doctorResult = new Gson().fromJson(StringUtils.cutoutBracketToString(string), DoctorResult.class);
        return doctorResult.getServiceStaff();
    }

    /**
     * 主页获取医生信息
     * @param string
     * @return
     */
    public static DoctorInfoNew JsonDoctorInfoNew(String string){
        if (TextUtils.isEmpty(string)) return null;
        DoctorResultNew doctorResultNew = new Gson().fromJson(StringUtils.cutoutBracketToString(string), DoctorResultNew.class);
        return doctorResultNew.getDoctorInfo();
    }
}
