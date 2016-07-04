package com.hxqydyl.app.ys.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.request.ParamsBean;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.common.AppContext;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.CommonUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.Utils;
import com.umeng.analytics.MobclickAgent;
import com.xus.http.httplib.https.HttpUtil;
import com.xus.http.httplib.interfaces.HttpUtilBack;
import com.xus.http.httplib.model.BaseParams;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.AppManager;
import framework.listener.RegisterSucListener;
import framework.listener.RegisterSucMag;


public class BaseFragmentActivity<T> extends FragmentActivity implements RegisterSucListener,HttpUtilBack {

    private boolean isTest = CommonUtils.isTest(AppContext.getInstance());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

//        // 修改状态栏颜色，4.4+生效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus();
//        }
        // SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // tintManager.setStatusBarTintEnabled(true);
        // tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void addRegisterListener(RegisterSucListener listener) {
        if (listener != null)
            RegisterSucMag.getInstance().addRegisterSucListeners(listener);
    }

    public void removeRegisterListener(RegisterSucListener listener) {
        if (listener != null)
            RegisterSucMag.getInstance().removeRegisterSucListeners(listener);
    }

    //判断是否认证
    public String isIdenyInfo() {
        String code = SharedPreferences.getInstance().getString(SharedPreferences.USER_INFO_COMPLETE, "0");
        String text = "";
        if (code.equals("0")) {
            text = "您还未完善个人信息，您可以进入个人中心进行认证";
        } else if (code.equals("2")) {
            text = "未通过审核/未通过认证";
        } else if (code.equals("3")) {
            text = "认证中";
        }
        return text;
    }

    //医生是否完善个人资料
    public String isCompleteInfo() {
        String code = SharedPreferences.getInstance().getString(SharedPreferences.USER_INFO_COMPLETE, "0");
        String text = "";
        if (code.equals("0")) {
            text = "您还未完善个人信息，您可以进入个人中心进行认证";
        }
        return text;
    }

    @Override
    public void onRegisterSuc(boolean isRegister) {
        if (isRegister)
        DialogUtils.showNormalDialog(this);
    }
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }
    private SweetAlertDialog pDialog;
    public void showDialog(String text) {
        if (!(pDialog != null && pDialog.isShowing())) {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setCancelable(true);
            pDialog.show();
        }
        pDialog.setTitleText(text);

    }

    public void dismissDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismissWithAnimation();
        }
    }

    public HttpUtil httpUtil = new HttpUtil(this);
    public Gson gson = new Gson();

    @Override
    public <T> void onSuccess(int i, String s, Class<T> aClass, Map<String, String> map) {
        Log.e("wangxu", "url=" + map.get("url"));
        Log.e("wangxu", "cookie" + map.get("Cookie"));
        Log.e("wangxu", "params" + map.get("params"));
        Log.e("wangxu", "json=" + s);
        try {
            String cookie = map.get("Cookie");
            if (!TextUtils.isEmpty(cookie)){
                SharedPreferences.getInstance().putString("Http_Cookie",cookie);
            }
            dismissDialog();
            if (map.get("IsString").equals("false")) {
                BaseResponse t = (BaseResponse) gson.fromJson(s, aClass);
                if (t.code == 200 || (t.query != null && !TextUtils.isEmpty(t.query.success) && t.query.success.equals("1")) || (t.value != null && t.value.equals("true"))) {
                    onSuccessToBean(t, i);
                    onSuccessToBeanWithMap(t, i, map);
                } else if (t.code != 200 && !TextUtils.isEmpty(t.message)) {
                    if (t.code == 500) {
                        UIHelper.ToastMessage(this, "服务器正在升级，请稍后再试");
                    } else {
                        UIHelper.ToastMessage(this, t.message);
                        if (isTest && t.code != 406) {
                            DialogUtils.showNormalDialog(this, "此弹框仅在测试弹出", "服务器错误:请测试人员区分是否为bug后记录-\nurl:" + map.get("url") + "\n请求数据:" + map.get("params") + "\n请求方式:" + map.get("httpType") + "\n" + "返回数据:" + s);
                        }
                    }

                } else if (t.query != null && !TextUtils.isEmpty(t.query.message)) {
                    UIHelper.ToastMessage(this, t.query.message);
                } else {
                    UIHelper.ToastMessage(this, "请求异常！请稍后再试");
                    if (isTest) {
                        DialogUtils.showNormalDialog(this, "此弹框仅在测试弹出", "服务端请求头有误 \nurl:" + map.get("url") + "\n请求数据:" + map.get("params") + "\n请求方式:" + map.get("httpType") + "\n" + "返回数据:" + s);
                    }
                }
            } else {
                onSuccessToString(s, i);
                onSuccessToStringWithMap(s, i, map);
            }
        } catch (Exception e) {
            Log.e("wangxu", e.toString());
            UIHelper.ToastMessage(this, "加载失败，请稍后再试");
            if (isTest) {
                DialogUtils.showNormalDialog(this, "此弹框仅在测试弹出", "android程序内部错误" + e.toString());
            }
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

    public <T> void onSuccessToBeanWithMap(T bean, int flag, Map<String, String> map) {

    }

    public void onSuccessToString(String json, int flag) {

    }

    public void onSuccessToStringWithMap(String json, int flag, Map<String, String> map) {

    }

    /**
     * @param params 使用toPostParams或者toGetParams方法
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p/>
     *               onSuccessString中回调
     */
    public void toNomalNetStringBack(BaseParams params, int flag, String url, String showdialog) {
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "true");
        map.put("url", url);
        map.put("params", params.toString());
        doNet(params,flag,url,map,null,showdialog);
    }

    /**
     * 普通请求，默认转换为bean类
     *
     * @param params 使用toPostParams或者toGetParams方法
     * @param aClass 转换成bean类的class
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p/>
     *               在onSuccessToBean中回调
     */
    public void toNomalNet(BaseParams params, Class<T> aClass, int flag, String url, String showdialog) {
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        map.put("url", url);
        map.put("params", params.toString());
        doNet(params,flag,url,map,aClass,showdialog);
    }

    public void toNomalNet(BaseParams params, int flag, String url, String showdialog) {
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        doNet(params,flag,url,map,null,showdialog);
    }

    private void doNet(BaseParams params, int flag, String url, Map<String, String> map,Class<T> tClass, String showdialog){
        if (!Utils.isNetWorkConnected(this)){
            UIHelper.ToastMessage(this,"当前网络不可用，请检查网络");
        }

        if (!TextUtils.isEmpty(showdialog)) {
            showDialog(showdialog);
        }
        if (!TextUtils.isEmpty(SharedPreferences.getInstance().getString("Http_Cookie", ""))) {
            params.addHeader("cookie", SharedPreferences.getInstance().getString("Http_Cookie", ""));
        }
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get,tClass!=null?tClass:BaseResponse.class, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, tClass!=null?tClass:BaseResponse.class, map);
        }
    }
    public GetParams toGetParams(ParamsBean... keys) {
        return toGetParams(null, keys);
    }

    public PostPrams toPostParams(ParamsBean... keys) {
        return toPostParams(null, keys);
    }

    /**
     * @param keys 通过toParamsBaen获取键值对
     * @return
     */
    public PostPrams toPostParams(Map<String, String> header, ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header != null) {
            params.setHeader(header);
        }
        return params;
    }

    public PostPrams toPostFileParams(Map<String, String> header, ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.addFilePrams(s.getKey(), s.getValue());
        }
        if (header != null) {
            params.setHeader(header);
        }
        return params;
    }

    public PostPrams toPostFileParams(ParamsBean... keys) {
        PostPrams params = new PostPrams();
        for (ParamsBean s : keys) {
            params.addFilePrams(s.getKey(), s.getValue());
        }
        return params;
    }

    public GetParams toGetParams(Map<String, String> header, ParamsBean... keys) {
        GetParams params = new GetParams();
        for (ParamsBean s : keys) {
            params.put(s.getKey(), s.getValue());
        }
        if (header != null) {
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
