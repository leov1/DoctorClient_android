package com.hxqydyl.app.ys.utils.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;

/**
 * 开源框架 Android-Universal-Image-Loader 的封装实现
 * Created by alice_company on 2016/5/31.
 */
public class UniversalAndroidImageLoader implements ImageLoaderWrapper{
    private final static String HTTP = "http";
    private final static String HTTPS = "https";

    UniversalAndroidImageLoader() {

    }

    @Override
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option) {
        int imageLoadingResId = R.drawable.default_image;
        int imageErrorResId = R.drawable.default_image;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true) //加载本地图片不需要再做SD卡缓存，只做内存缓存即可
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        String uri;
        if (imageFile == null) {
            uri = "";
        } else {
            uri = ImageDownloader.Scheme.FILE.wrap(imageFile.getAbsolutePath());
        }
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    @Override
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option) {
        int imageLoadingResId = R.drawable.default_image;
        int imageErrorResId = R.drawable.default_image;
        if (option != null) {
            imageLoadingResId = option.loadingResId;
            imageErrorResId = option.loadErrorResId;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageLoadingResId)
                .showImageForEmptyUri(imageErrorResId)
                .showImageOnFail(imageErrorResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }
}
