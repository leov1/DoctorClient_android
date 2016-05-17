package com.hxqydyl.app.ys.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import common.AppManager;
import framework.listener.RegisterSucListener;
import framework.listener.RegisterSucMag;


public class BaseFragmentActivity extends FragmentActivity implements RegisterSucListener {

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
            text = "您还未完善个人信息，您可以点击头像或个人中心进行认证";
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
            text = "您还未完善个人信息，您可以点击头像或个人中心进行认证";
        }
        return text;
    }

    @Override
    public void onRegisterSuc() {
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
}
