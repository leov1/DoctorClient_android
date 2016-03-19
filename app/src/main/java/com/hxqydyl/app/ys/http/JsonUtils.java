package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * 验证码
     * @param string
     * @return
     */
    public static CaptchaResult JsonCaptchaResult(String string){
        if (TextUtils.isEmpty(string)) return null;
        CaptchaResult captchaResult = new Gson().fromJson(StringUtils.cutoutBracketToString(string),CaptchaResult.class);
        return captchaResult;
    }

    /**
     * 注册第一步
     * @param string
     * @return
     */
    public static RegisterFirst JsonQuery(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        RegisterFirst registerFirst = new RegisterFirst();
        if (jsonObject.has("doctorUuid")) registerFirst.setDoctorUuid(jsonObject.getString("doctorUuid"));
        if (jsonObject.has("mobile")) registerFirst.setMobile(jsonObject.getString("mobile"));
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setMessage(queryJs.getString("message"));
        query.setSuccess(queryJs.getString("success"));
        registerFirst.setQuery(query);
        return registerFirst;
    }

    /**
     * 上传头像返回
     * @param string
     * @return
     */
    public static HeadIconResult JsonHeadIconResult(String string){
        if (TextUtils.isEmpty(string)) return null;
        HeadIconResult headIconResult = new Gson().fromJson(string,HeadIconResult.class);
        return headIconResult;
    }
}
