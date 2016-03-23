package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.CityBean;
import com.hxqydyl.app.ys.bean.register.CityResultBean;
import com.hxqydyl.app.ys.bean.register.DoctorInfo;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.register.OfficeBean;
import com.hxqydyl.app.ys.bean.register.OfficeResultBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfo;
import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.bean.register.RegionBean;
import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.bean.register.TagsBean;
import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 省
     * @param string
     * @return
     * @throws JSONException
     */
    public static ProvinceInfoResult JsonProvinceInfoResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return  null;
        JSONObject jsonObject = new JSONObject(string);
        ProvinceInfoResult provinceInfoResult = new ProvinceInfoResult();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        provinceInfoResult.setQuery(query);

        if (jsonObject.has("relist")){
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<ProvinceInfo> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                ProvinceInfo provinceInfo = new ProvinceInfo();
                provinceInfo.setCode(object.getString("code"));
                provinceInfo.setProvinceName(object.getString("provinceName"));
                list.add(provinceInfo);
            }
            provinceInfoResult.setProvinceInfos(list);
        }
        return  provinceInfoResult;
    }

    /**
     * 市
     * @param string
     * @return
     * @throws JSONException
     */
    public static CityResultBean JsonCityResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return  null;
        JSONObject jsonObject = new JSONObject(string);
        CityResultBean cityResultBean = new CityResultBean();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        cityResultBean.setQuery(query);

        if (jsonObject.has("relist")){
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<CityBean> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                CityBean cityBean = new CityBean();
                cityBean.setCode(object.getString("code"));
                cityBean.setCityName(object.getString("cityName"));
                list.add(cityBean);
            }
            cityResultBean.setCityBeans(list);
        }
        return  cityResultBean;
    }

    /**
     * 医院列表
     * @param string
     * @return
     * @throws JSONException
     */
    public static HospitalResultBean JsonHospitalResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        HospitalResultBean hospitalResultBean = new HospitalResultBean();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        hospitalResultBean.setQuery(query);

        if (jsonObject.has("relist")){
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<HospitalsBean> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                HospitalsBean hospitalsBean = new HospitalsBean();
                hospitalsBean.setId(object.getString("id"));
                hospitalsBean.setHospitalName(object.getString("hospitalName"));
                list.add(hospitalsBean);
            }
            hospitalResultBean.setList(list);
        }
        return hospitalResultBean;
    }

    /**
     * 区县
     * @param string
     * @return
     * @throws JSONException
     */
    public static RegionResultBean JsonRegionResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        RegionResultBean regionResultBean = new RegionResultBean();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        regionResultBean.setQuery(query);

        if (jsonObject.has("relist")){
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<RegionBean> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                RegionBean regionBean = new RegionBean();
                regionBean.setCode(object.getString("code"));
                regionBean.setRegionName(object.getString("regionName"));
                list.add(regionBean);
            }
            regionResultBean.setRegionBeans(list);
        }
        return regionResultBean;
    }

    /**
     * 科室
     * @param string
     * @return
     * @throws JSONException
     */
    public static OfficeResultBean JsonOfficeResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        OfficeResultBean officeResultBean = new OfficeResultBean();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        officeResultBean.setQuery(query);

        if (jsonObject.has("relist")){
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<OfficeBean> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                OfficeBean officeBean = new OfficeBean();
                officeBean.setId(object.getString("id"));
                officeBean.setDepartmentName(object.getString("departmentName"));
                list.add(officeBean);
            }
            officeResultBean.setOfficeBeans(list);
        }
        return officeResultBean;
    }

    /**
     * 标签
     * @param string
     * @return
     * @throws JSONException
     */
    public static TagsResultBean JsonTagsResult(String string) throws JSONException{
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        TagsResultBean tagsResultBean = new TagsResultBean();
        Query query = new Query();
        JSONObject queryJs = jsonObject.getJSONObject("query");
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        tagsResultBean.setQuery(query);

        if (jsonObject.has("reList")){
            JSONArray jsonArray = jsonObject.getJSONArray("reList");
            List<TagsBean> list = new ArrayList<>();
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                TagsBean tagsBean = new TagsBean();
                tagsBean.setTagUuid(object.getString("tagUuid"));
                tagsBean.setTagName(object.getString("tagName"));
                list.add(tagsBean);
            }
            tagsResultBean.setTagsBeans(list);
        }
        return tagsResultBean;
    }
}
