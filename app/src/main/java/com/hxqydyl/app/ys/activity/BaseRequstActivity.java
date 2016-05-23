package com.hxqydyl.app.ys.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.request.BaseRequest;
import com.hxqydyl.app.ys.bean.request.ParamsBean;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.common.AppContext;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.push.OnMessageGet;
import com.hxqydyl.app.ys.push.bean.BasePushBean;
import com.hxqydyl.app.ys.push.listener.BasePushListener;
import com.hxqydyl.app.ys.push.toactivity.PushType;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.CommonUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.xus.http.httplib.https.HttpUtil;
import com.xus.http.httplib.interfaces.HttpUtilBack;
import com.xus.http.httplib.model.BaseParams;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;


import java.util.HashMap;
import java.util.Map;

import galleryfinal.wq.photo.widget.PickConfig;
import galleryfinal.yalantis.ucrop.UCrop;

/**
 * Created by wangxu on 2016/4/20.
 */
public class BaseRequstActivity<T> extends BaseTitleActivity implements HttpUtilBack {
    public HttpUtil httpUtil = new HttpUtil(this);
    public Gson gson = new Gson();
    private int pickPic = 1;//选择图片模式
    private int pickNum = 1;//允许选择张数
    private boolean isTest= CommonUtils.isTest(AppContext.getInstance());

    @Override
    public <T> void onSuccess(int i, String s, Class<T> aClass, Map<String, String> map) {
        Log.e("wangxu","params"+map.get("params"));
        Log.e("wangxu", "json=" + s);
        try {
            dismissDialog();
            if (map.get("IsString").equals("false")) {
                BaseResponse t = (BaseResponse) gson.fromJson(s, aClass);
                if (t.code == 200 || (t.query != null && !TextUtils.isEmpty(t.query.success) && t.query.success.equals("1")) || (t.value != null && t.value.equals("true"))) {
                    onSuccessToBean(t, i);
                } else if (t.code != 200 && !TextUtils.isEmpty(t.message)) {
                    UIHelper.ToastMessage(this, t.message);
                    if (isTest&&t.code!=406){
                        DialogUtils.showNormalDialog(this,"此弹框仅在测试弹出","服务器错误:请测试人员区分是否为bug后记录-\nurl:"+map.get("url")+"\n请求数据:"+map.get("params")+"\n请求方式:"+map.get("httpType")+"\n"+"返回数据:"+s);
                    }
                } else if (t.query != null && !TextUtils.isEmpty(t.query.message)) {
                    UIHelper.ToastMessage(this, t.query.message);
                } else {
                    UIHelper.ToastMessage(this, "请求异常！请稍后再试");
                    if (isTest){
                        DialogUtils.showNormalDialog(this,"此弹框仅在测试弹出","服务端请求头有误 \nurl:"+map.get("url")+"\n请求数据:"+map.get("params")+"\n请求方式:"+map.get("httpType")+"\n"+"返回数据:"+s);
                    }
                }
            } else {
                onSuccessToString(s, i);
            }
        } catch (Exception e) {
            Log.e("wangxu", e.toString());
            UIHelper.ToastMessage(this, "加载失败，请稍后再试");
            if (isTest){
                DialogUtils.showNormalDialog(this,"此弹框仅在测试弹出","android程序内部错误"+e.toString());
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

    public void onSuccessToString(String json, int flag) {

    }

    /**
     * @param params 使用toPostParams或者toGetParams方法
     * @param flag   表示该次请求的flag
     * @param url    请求地址
     *               <p>
     *               onSuccessString中回调
     */
    public void toNomalNetStringBack(BaseParams params, int flag, String url, String showdialog) {
        if (!TextUtils.isEmpty(showdialog)) {
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "true");
        map.put("url",url);
        map.put("params",params.toString());
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, String.class, map);
            map.put("httpType","get");

        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, String.class, map);
            map.put("httpType","post");

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
    public void toNomalNet(BaseParams params, Class<T> aClass, int flag, String url, String showdialog) {
        if (!TextUtils.isEmpty(showdialog)) {
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        map.put("url",url);
        map.put("params",params.toString());
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, aClass, map);
            map.put("httpType","get");
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, aClass, map);
            map.put("httpType","post");
        }
    }

    public void toNomalNet(BaseParams params, int flag, String url, String showdialog) {
        if (!TextUtils.isEmpty(showdialog)) {
            showDialog(showdialog);
        }
        Map<String, String> map = new HashMap<>();
        map.put("IsString", "false");
        if (params instanceof GetParams) {
            GetParams get = (GetParams) params;
            httpUtil.doGet(flag, url, get, BaseResponse.class, map);
        } else if (params instanceof PostPrams) {
            PostPrams post = (PostPrams) params;
            httpUtil.doPost(flag, url, post, BaseResponse.class, map);
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

    private static final int CODE_FOR_WRITE_PERMISSION = 1119;

    public void access(int pickPic, int picknum) {
        this.pickNum = picknum;
        this.pickPic = pickPic;
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(BaseRequstActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CODE_FOR_WRITE_PERMISSION);

                return;
            }
        }
        showEditPhotoLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                showEditPhotoLayout();
            } else {
                //用户不同意，自行处理即可
                // finish();
            }
        }
    }

    public void showEditPhotoLayout() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        new PickConfig.Builder(BaseRequstActivity.this)
                .isneedcrop(false)
                .actionBarcolor(Color.parseColor("#1F80B8"))
                .statusBarcolor(Color.parseColor("#FFFFFF"))
                .isneedcamera(true)
                .isSqureCrop(false)
                .setUropOptions(options)
                .maxPickSize(pickNum )
                .spanCount(Integer.parseInt("3"))
                .pickMode(pickPic).build();
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

    public <L>void setPushListener(final OnMessageGet<L> listeners, final PushType type){
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("com.push.sendMessage");
            registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              BasePushBean basePushBean=  gson.fromJson(intent.getExtras().getString("json"),type.getBean());
                if (basePushBean.id.equals(type.getId())){
                    L t=(L)basePushBean;
                    listeners.onMessageGet(t);
                }
            }
        }, dynamic_filter);
    }

}
