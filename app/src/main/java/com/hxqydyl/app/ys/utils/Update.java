package com.hxqydyl.app.ys.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hxqydyl.app.ys.bean.AppVersion;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import common.AppManager;
import okhttp3.Call;

/**
 * Created by wangxu on 2016/4/12.
 */
public class Update {
    public final static int GET_UNDATAINFO_ERROR = 1002;
    public final static int DOWN_ERROR = 1003;
    public Context context;
    private static Update update;

    public synchronized static Update getIncetence(Context context) {
        if (update == null) {
            update = new Update(context);
        }
        return update;
    }

    public Update(Context context) {
        this.context = context;
    }

    //获取服务器请求 是否更新
    public void cheackIsUp() {
        String versionStr = "2.4.4";
        try {

            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionStr = packInfo.versionName;
        }catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpUtils.get().addHeader("Accept", "application/json")
                .addParams("type","0")//0医生端Android软件，1患者端Android软件，4医生端IOS软件，5患者端IOS
                .addParams("version",versionStr)
                .url(UrlConstants.getWholeApiUrl(UrlConstants.UPDATE)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                AppVersion version = gson.fromJson(response, AppVersion.class);
                try {
                    if (version == null || TextUtils.isEmpty(version.getNewest())) {

                        return;
                    }

                    if (version.getNewest().trim().equals("false") && !TextUtils.isEmpty(version.getUrl())) {
                        //强制更新
                        if (!TextUtils.isEmpty(version.getForceUpdate()) && version.getForceUpdate().trim().equals("true")) {
                            showUpdataDialog(context, version.getUrl(), "检测到有新的版本，是否更新",true);

                        }
                        //不强制更新
                        else if (!TextUtils.isEmpty(version.getForceUpdate()) && version.getForceUpdate().trim().equals("false")){
                            showUpdataDialog(context, version.getUrl(), "检测到有新的版本，是否更新",false);

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_UNDATAINFO_ERROR:
                    //服务器超时
                    Toast.makeText(context, "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(context, "下载新版本失败", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    protected void showUpdataDialog(final Context context, final String url, String message) {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle("版本升级");
        builer.setMessage(message);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dwonloadApk(context, url);
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    protected void showUpdataDialog(final Context context, final String url, String message,final boolean isForceOrder) {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle("版本升级");
        builer.setMessage(message);
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dwonloadApk(context, url,isForceOrder);
            }
        });

            builer.setNegativeButton(isForceOrder?"退出":"取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (isForceOrder) {
                        System.exit(0);
                        AppManager.getAppManager().AppExit(context);
                    }
                }
            });
        builer.setCancelable(false);
        AlertDialog dialog = builer.create();
        dialog.show();
    }


    //下载apk
    public void dwonloadApk(final Context context, final String url) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(url, pd);
                    sleep(3000);
                    openApk(file, context);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //下载apk
    public void dwonloadApk(final Context context, final String url,final boolean isForceOrder) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setButton(DialogInterface.BUTTON_NEGATIVE,"取消下载",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (isForceOrder) {
                    System.exit(0);
                    AppManager.getAppManager().AppExit(context);
                }else {
                    pd.dismiss();
                }
            }
        });
        pd.show();
        pd.setCancelable(false);
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(url, pd);
                    sleep(3000);
                    openApk(file, context);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //打开apk
    public void openApk(File file, Context context) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //获取versionname
    private float getVersionName(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        float v = packInfo.versionCode;
        return v;
    }

    public static void clearUpdate() {
        update = null;
    }
}
