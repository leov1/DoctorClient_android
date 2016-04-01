package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

/**
 * 注册协议
 */
public class RegisterOrderActivity extends BaseTitleActivity implements View.OnClickListener{

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order);

        initViews();
        initListeners();
    }

    private void initListeners() {
        setBackListener(this);
    }

    private void initViews() {
        initViewOnBaseTitle("注册协议");
        webView = (WebView) findViewById(R.id.webview_order);
//        webView.loadUrl("file:///android_asset/register/register_order.html");
        webView.loadUrl("http://172.168.1.41/html/reg/addreginfo_amet.shtml");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }
}
