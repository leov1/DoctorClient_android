package com.hxqydyl.app.ys.activity.video;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.squareup.okhttp.internal.Util;

import org.apache.http.util.EncodingUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频列表
 */
public class VideoActivity extends Activity {
    private static final String CLIENT_INTERFACE_NAME = "local_obj";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
 //     webView.setWebChromeClient(mChromeClient);
        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);

        loadDataAndURL(Constants.GET_VIDEOS);
    }

    private void loadDataAndURL(String url) {
        webView.loadUrl(url);
    }

    WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("goodm://")){//自定义js协议
                Pattern pl = Pattern.compile("goodm://([a-zA-Z0-9]+)(/[\\w\\W]*)?");
                Matcher ml = pl.matcher(url);
                String functionname = "",paramater = "";
                if (ml.find()){
                    functionname = ml.group(1);
                    paramater = ml.group(2);
                }
                if (!TextUtils.isEmpty(paramater)){
                    paramater = paramater.substring(1);
                }
//                SetJsBridge(functionname,paramater);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
}
