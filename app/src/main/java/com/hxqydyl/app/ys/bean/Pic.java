package com.hxqydyl.app.ys.bean;

import java.io.Serializable;

/**
 * Created by white_ash on 2016/3/24.
 */
public class Pic extends BaseBean implements Serializable {
    private static final String PRE_WEB = "http://"; // from Web
    private static final String PRE_SD = "file://"; // from SD card
    private static final String PRE_PROVIDER = "content://"; // from content provider
    private static final String PRE_ASSETS = "assets://"; // from assets
    private static final String PRE_DRAWABLE = "drawable://"; // from drawables (non-9patch images)

    private String url;
    private String thumbUrl;
    private int source = Source.WEB;

    /**
     * 无参构造方法，会默认指定图片来源为Pic.Source.WEB
     */
    public Pic(){}

    /**
     * 通过指定来源构造Pic对象
     * @param source 来源（SD卡，资源文件，网络等，对应取值见内部Source类）
     */
    public Pic(int source){
        this.source = source;
    }

    /**
     * 获取用于ImageLoader显示的uri，带有根据来源添加的前缀
     * @return String
     */
    public String getDisplayUri() {
        if(url == null){
            return null;
        }
        return getWholeUri(url);
    }

    /**
     * 获取用于ImageLoader显示的缩略图uri，带有根据来源添加的前缀
     * @return String
     */
    public String getDisplayThumbUri(){
        if(thumbUrl == null){
            return null;
        }
        return getWholeUri(thumbUrl);
    }


    /**
     * 获取uri
     * @param uri 未添加前缀的uri
     * @return
     */
    private String getWholeUri(String uri){
        String uri_pre = "";
        switch(source){
            case Source.ASSETS:
                uri_pre = uri.startsWith(PRE_ASSETS) ? "" : PRE_ASSETS;
                break;
            case Source.DRAWABLE:
                uri_pre = uri.startsWith(PRE_DRAWABLE) ? "" : PRE_DRAWABLE;
                break;
            case Source.PROVIDER:
                uri_pre = uri.startsWith(PRE_PROVIDER) ? "" : PRE_PROVIDER;
                break;
            case Source.SD:
                uri_pre = uri.startsWith(PRE_SD) ? "" : PRE_SD;
                break;
            case Source.WEB:
                uri_pre = uri.startsWith(PRE_WEB) ? "" : PRE_WEB;
                break;
        }
        return  uri_pre + uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    /**
     * 图片来源
     */
    public static class Source{
        public static final int WEB = 1; // from Web
        public static final int SD = 2; // from SD card
        public static final int PROVIDER = 3; // from content provider
        public static final int ASSETS = 4; // from assets
        public static final int DRAWABLE = 5; // from drawables (non-9patch images)
    }
}
