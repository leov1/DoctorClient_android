package com.hxqydyl.app.ys.activity;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.request.BaseRequest;
import com.hxqydyl.app.ys.bean.request.ParamsBean;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.xus.http.httplib.https.HttpUtil;
import com.xus.http.httplib.interfaces.HttpUtilBack;
import com.xus.http.httplib.model.BaseParams;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxu on 2016/4/20.
 */
public class BaseRequstActivity<T> extends BaseTitleActivity implements HttpUtilBack {
    public HttpUtil httpUtil = new HttpUtil(this);
    public Gson gson = new Gson();

    @Override
    public <T> void onSuccess(int i, String s, Class<T> aClass, Map<String, String> map) {
        Log.e("wangxu", "json=" + s);
        try {
            dismissDialog();
            if (map.get("IsString").equals("false")) {
                BaseResponse t = (BaseResponse) gson.fromJson(s, aClass);
                if (t.code==200 || (t.query != null && !TextUtils.isEmpty(t.query.success) && t.query.success.equals("1"))||(t.value!=null&&t.value.equals("true"))) {
                    onSuccessToBean(t, i);
                } else if (t.code!=200 && !TextUtils.isEmpty(t.message)) {
                    UIHelper.ToastMessage(this, t.message);
                } else if (t.query != null && !TextUtils.isEmpty(t.query.message)) {
                    UIHelper.ToastMessage(this, t.query.message);
                } else {
                    UIHelper.ToastMessage(this, "请求异常！请稍后再试");
                }
            } else {
                onSuccessToString(s, i);
            }
        } catch (Exception e) {
            Log.e("wangxu" ,e.toString());
            UIHelper.ToastMessage(this, "加载失败，请稍后再试"+e.toString());

            onfail(i, 9999, map);
        }

    }

    @Override
    public void onfail(int i, int i1, Map<String, String> map) {
        dismissDialog();
        UIHelper.ToastMessage(this, "加载失败，请稍后再试");
    }

    public <T> void onSuccessToBean(T bean, int flag) {

    }

    public void onSuccessToString(String json, int flag) {

    }

    /**
     * @param params 使用toPostParams或者toGetParams方法
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p>
     *               onSuccessString中回调
     */
    public void toNomalNetStringBack(BaseParams params, int flag, String url,String showdialog) {
        if (!TextUtils.isEmpty(showdialog)){
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "true");
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, String.class, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, String.class, map);
        }
    }

    /**
     * 普通请求，默认转换为bean类
     *
     * @param params 使用toPostParams或者toGetParams方法
     * @param aClass 转换成bean类的class
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p>
     *               在onSuccessToBean中回调
     */
    public void toNomalNet(BaseParams params, Class<T> aClass, int flag, String url,String showdialog) {
        if (!TextUtils.isEmpty(showdialog)){
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, aClass, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, aClass, map);
        }
    }
    public void toNomalNet(BaseParams params, int flag, String url,String showdialog) {
        if (!TextUtils.isEmpty(showdialog)){
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, BaseResponse.class, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post,  BaseResponse.class, map);
        }
    }
    public GetParams toGetParams( ParamsBean... keys) {
        return toGetParams(null, keys);
    }
    public PostPrams toPostParams( ParamsBean... keys) {
        return toPostParams(null,keys);
    }
    /**
     * @param keys 通过toParamsBaen获取键值对
     * @return
     */
    public PostPrams toPostParams(Map<String,String> header, ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header!=null){
            params.setHeader(header);
        }
        return params;
    }

    public GetParams toGetParams(Map<String,String> header,ParamsBean... keys) {
        GetParams params = new GetParams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header!=null){
            params.setHeader(header);
        }
        return params;
    }

    /**
     * 填入表单值
     *
     * @param key
     * @param value
     * @return
     */
    public ParamsBean toParamsBaen(String key, String value) {
        return new ParamsBean(key, value);
    }
}
