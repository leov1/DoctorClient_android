package com.hxqydyl.app.ys.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hxq on 2016/3/26.
 */
public class BaseWebFragment extends BaseFragment{

    public View view;
    public WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_web, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        webView = (WebView) view.findViewById(R.id.webview);
        initWebSetting();
    }

    private void initWebSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = this.getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
//        webView.setWebChromeClient(mChromeClient);
//        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);
    }

    public WebViewClient webViewClient = new WebViewClient() {
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
            if (url.contains("goodm://")) {//自定义js协议
                Pattern pl = Pattern.compile("goodm://([a-zA-Z0-9]+)(/[\\w\\W]*)?");
                Matcher ml = pl.matcher(url);
                String functionname = "", paramater = "";
                if (ml.find()) {
                    functionname = ml.group(1);
                    paramater = ml.group(2);
                }
                if (!TextUtils.isEmpty(paramater)) {
                    paramater = paramater.substring(1);
                }
//                SetJsBridge(functionname,paramater);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
}
