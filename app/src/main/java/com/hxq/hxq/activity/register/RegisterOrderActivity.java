package com.hxq.hxq.activity.register;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.hxq.hxq.R;
import com.hxq.hxq.ui.swipebacklayout.SwipeBackActivity;

/**
 * 注册协议
 */
public class RegisterOrderActivity extends SwipeBackActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order);

        initViews();
    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webview_order);
        webView.loadUrl("file:///android_asset/register/register_order.html");
    }
}
