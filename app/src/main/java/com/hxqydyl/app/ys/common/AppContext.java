package com.hxqydyl.app.ys.common;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    }


}