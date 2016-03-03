package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.DoctorInfo;
import com.hxqydyl.app.ys.bean.DoctorResult;
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
}
