package com.hxqydyl.library.common;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


public class BaseApplication extends Application {

    private static BaseApplication app;

    public BaseApplication() {
        app = this;
    }

    public static synchronized BaseApplication getInstance() {
        if (app == null) {
            app = new BaseApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

}