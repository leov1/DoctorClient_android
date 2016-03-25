package com.hxqydyl.app.ys.activity.video;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseWebActivity;
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
public class VideoActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDataAndURL(Constants.GET_VIDEOS);
    }

    private void loadDataAndURL(String url) {
        webView.loadUrl(url);
    }
}
