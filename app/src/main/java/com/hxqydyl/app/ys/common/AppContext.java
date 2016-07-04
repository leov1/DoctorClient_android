package com.hxqydyl.app.ys.common;

import android.content.Intent;

import com.hxqydyl.app.ys.servise.UnReadMsgService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;
import common.BaseApplication;

public class AppContext extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initShare();

    }

    private void initShare() {
        PlatformConfig.setWeixin("wx4ab9879d6d249090", "68241ec26a6b59dcdf3593db65a1bed2");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
    }

}