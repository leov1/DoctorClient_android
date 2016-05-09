package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.article.ArticleResult;
import com.hxqydyl.app.ys.bean.article.Child;
import com.hxqydyl.app.ys.bean.article.Group;
import com.hxqydyl.app.ys.bean.homepage.PageIconBean;
import com.hxqydyl.app.ys.bean.homepage.PageIconResult;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.CityBean;
import com.hxqydyl.app.ys.bean.register.CityResultBean;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.register.IconBean;
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
import java.util.List;

/**
 * Created by hxq on 2016/3/3.
 * 解析网络请求返回的数据
 */
public class JsonUtils {

    /**
     * 主页获取医生信息
     *
     * @param string
     * @return
     */
    public static DoctorResultNew JsonDoctorInfoNew(String string) {
        if (TextUtils.isEmpty(string)) return null;
        DoctorResultNew doctorResultNew = new Gson().fromJson(string, DoctorResultNew.class);
        return doctorResultNew;
    }

    /**
     * 修改密码返回
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static Query JsonUpdatePw(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        return JsonQuery(string);
    }

    /**
     * 注册第一步
     *
     * @param string
     * @return
     */
    public static RegisterFirst JsonRegisterFirst(String string) {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = null;
        RegisterFirst registerFirst = new RegisterFirst();
        try {
            jsonObject = new JSONObject(string);
            if (jsonObject.has("doctorUuid"))
                registerFirst.setDoctorUuid(jsonObject.getString("doctorUuid"));
            if (jsonObject.has("mobile")) registerFirst.setMobile(jsonObject.getString("mobile"));
            registerFirst.setQuery(JsonQuery(string));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return registerFirst;
    }

    /**
     * 上传头像返回
     *
     * @param string
     * @return
     */
    public static HeadIconResult JsonHeadIconResult(String string) {
        if (TextUtils.isEmpty(string)) return null;
        HeadIconResult headIconResult = new Gson().fromJson(string, HeadIconResult.class);
        return headIconResult;
    }

    /**
     * 注册第二步
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static RegisterFirst JsonRegisterSec(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject js = new JSONObject(string);
        RegisterFirst registerFirst = new RegisterFirst();
        registerFirst.setQuery(JsonQuery(string));
        if (js.has("doctorUuid")) {
            registerFirst.setDoctorUuid(js.getString("doctorUuid"));
        }
        return registerFirst;
    }

    /**
     * 省
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static ProvinceInfoResult JsonProvinceInfoResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        ProvinceInfoResult provinceInfoResult = new ProvinceInfoResult();
        provinceInfoResult.setQuery(JsonQuery(string));

        if (jsonObject.has("relist")) {
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<ProvinceInfo> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ProvinceInfo provinceInfo = new ProvinceInfo();
                provinceInfo.setCode(object.getString("code"));
                provinceInfo.setProvinceName(object.getString("provinceName"));
                list.add(provinceInfo);
            }
            provinceInfoResult.setProvinceInfos(list);
        }
        return provinceInfoResult;
    }

    /**
     * 市
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static CityResultBean JsonCityResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        CityResultBean cityResultBean = new CityResultBean();
        cityResultBean.setQuery(JsonQuery(string));

        if (jsonObject.has("relist")) {
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<CityBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                CityBean cityBean = new CityBean();
                cityBean.setCode(object.getString("code"));
                cityBean.setCityName(object.getString("cityName"));
                list.add(cityBean);
            }
            cityResultBean.setCityBeans(list);
        }
        return cityResultBean;
    }

    /**
     * 医院列表
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static HospitalResultBean JsonHospitalResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        HospitalResultBean hospitalResultBean = new HospitalResultBean();
        hospitalResultBean.setQuery(JsonQuery(string));

        if (jsonObject.has("relist")) {
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<HospitalsBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
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
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static RegionResultBean JsonRegionResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        RegionResultBean regionResultBean = new RegionResultBean();
        regionResultBean.setQuery(JsonQuery(string));

        if (jsonObject.has("relist")) {
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<RegionBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
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
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static OfficeResultBean JsonOfficeResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(StringUtils.cutoutBracketToString(string));
        OfficeResultBean officeResultBean = new OfficeResultBean();
        officeResultBean.setQuery(JsonQuery(StringUtils.cutoutBracketToString(string)));

        if (jsonObject.has("relist")) {
            JSONArray jsonArray = jsonObject.getJSONArray("relist");
            List<OfficeBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
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
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static TagsResultBean JsonTagsResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject jsonObject = new JSONObject(string);
        TagsResultBean tagsResultBean = new TagsResultBean();
        tagsResultBean.setQuery(JsonQuery(string));

        if (jsonObject.has("reList")) {
            JSONArray jsonArray = jsonObject.getJSONArray("reList");
            List<TagsBean> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
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

    /**
     * 上传多张图片返回
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static ArrayList<IconBean> JsonIcons(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONArray jsonArray = new JSONArray(string);
        ArrayList<IconBean> iconBeans = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject js = jsonArray.getJSONObject(i);
            IconBean iconBean = new IconBean();
            iconBean.setId(js.getString("id"));
            iconBean.setName(js.getString("name"));
            iconBean.setSize(js.getInt("size"));
            iconBean.setUrl(js.getString("url"));
            iconBean.setThumbnail(js.getString("thumbnail"));
            iconBean.setError(js.getString("error"));
            iconBeans.add(iconBean);
        }
        return iconBeans;
    }

    /**
     * 注册第三步
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static RegisterFirst JsonRegisterThree(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject js = new JSONObject(string);
        RegisterFirst registerFirst = new RegisterFirst();
        registerFirst.setQuery(JsonQuery(string));
        if (js.has("mobile")) {
            registerFirst.setMobile(js.getString("mobile"));
        }
        if (js.has("uuid")) {
            registerFirst.setDoctorUuid(js.getString("uuid"));
        }
        return registerFirst;
    }

    /**
     * 解析验证码
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static CaptchaResult JsonCaptchResult(String string) {
        if (TextUtils.isEmpty(string)) return null;
        CaptchaResult captchaResult = new CaptchaResult();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
            captchaResult.setQuery(JsonQuery(string));
            if (jsonObject.has("captcha")) {
                captchaResult.setCaptcha(jsonObject.getString("captcha"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return captchaResult;
    }

    /**
     * 解析query
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static Query JsonQuery(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject js = new JSONObject(string);
        JSONObject queryJs = js.getJSONObject("query");
        Query query = new Query();
        query.setSuccess(queryJs.getString("success"));
        query.setMessage(queryJs.getString("message"));
        return query;
    }

    /**
     * 群发分组
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static ArticleResult JsonArticleResult(String string) throws JSONException {
        if (TextUtils.isEmpty(string)) return null;
        JSONObject js = new JSONObject(string);
        ArticleResult articleResult = new ArticleResult();
        articleResult.setQuery(JsonQuery(string));
        if (js.has("relist")) {
            JSONArray relist = js.getJSONArray("relist");
            ArrayList<Group> groups = new ArrayList<>();
            for (int i = 0; i < relist.length(); i++) {
                JSONObject groupJs = relist.getJSONObject(i);
                Group group = new Group();
                group.setChecked(false);
                group.setGroupId(groupJs.getString("groupId"));
                group.setGroupName(groupJs.getString("groupName"));
                JSONArray customers = groupJs.getJSONArray("customers");
                ArrayList<Child> children = new ArrayList<>();
                for (int j = 0; j < customers.length(); j++) {
                    JSONObject customJs = customers.getJSONObject(j);
                    Child child = new Child();
                    child.setAge(customJs.getString("age"));
                    child.setCustomerImg(customJs.getString("customerImg"));
                    child.setCustomerUuid(customJs.getString("customerUuid"));
                    child.setCustomerMessage(customJs.getString("customerMessage"));
                    child.setSex(customJs.getString("sex"));
                    child.setCustomerName(customJs.getString("customerName"));
                    child.setChecked(false);
                    children.add(child);
                }
                group.setChildren(children);
                groups.add(group);
            }
            articleResult.setGroups(groups);
        }
        return articleResult;
    }

    /**
     * 获取导航图
     *
     * @param string
     * @return
     * @throws JSONException
     */
    public static PageIconResult JsonPageIconResult(String string) {
        if (TextUtils.isEmpty(string)) return null;
        PageIconResult pageIconResult = new PageIconResult();

        try {
            pageIconResult.setQuery(JsonQuery(string));
            JSONObject js = new JSONObject(string);
            if (js.has("relist")) {
                JSONArray relist = js.getJSONArray("relist");
                ArrayList<PageIconBean> pageIconBeans = new ArrayList<>();
                for (int i = 0; i < relist.length(); i++) {
                    JSONObject jsonObject = relist.getJSONObject(i);
                    PageIconBean pageIconBean = new PageIconBean();
                    pageIconBean.setNote(jsonObject.getString("note"));
                    pageIconBean.setImageUrl(jsonObject.getString("imageUrl"));
                    pageIconBean.setImageNote(jsonObject.getString("imageNote"));
                    pageIconBean.setPosition(jsonObject.getInt("position"));
                    pageIconBean.setImageUuid(jsonObject.getString("imageUuid"));
                    pageIconBean.setUrl(jsonObject.getString("url"));
                    pageIconBeans.add(pageIconBean);
                }
                pageIconResult.setPageIconBeans(pageIconBeans);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pageIconResult;
    }
}
