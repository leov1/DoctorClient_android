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
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.Utils;
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
public class BaseRequstActivity extends BaseTitleActivity {
    public HttpUtil httpUtil = new HttpUtil(this);
    public Gson gson = new Gson();
    private int pickPic = 1;//选择图片模式
    private int pickNum = 1;//允许选择张数
    private boolean isTest = CommonUtils.isTest(AppContext.getInstance());

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
                .isneedcamera(false)
                .isSqureCrop(false)
                .setUropOptions(options)
                .maxPickSize(pickNum)
                .spanCount(Integer.parseInt("3"))
                .pickMode(pickPic).build();
    }



    public BroadcastReceiver pushListener;

    public <L> void setPushListener(final OnMessageGet<L> listeners, final PushType type) {
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("com.push.sendMessage");
        pushListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                BasePushBean basePushBean = gson.fromJson(intent.getExtras().getString("json"), type.getBean());
                if (basePushBean.id.equals(type.getId())) {
                    L t = (L) basePushBean;
                    listeners.onMessageGet(t);
                }
            }
        };
        registerReceiver(pushListener, dynamic_filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pushListener != null) {
            unregisterReceiver(pushListener);
        }
    }
}
