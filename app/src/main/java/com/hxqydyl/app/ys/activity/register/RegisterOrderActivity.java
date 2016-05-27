package com.hxqydyl.app.ys.activity.register;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.hxqydyl.app.ys.http.UrlConstants;

/**
 * 注册协议
 */
public class RegisterOrderActivity extends BaseTitleActivity implements View.OnClickListener{

    @Bind(R.id.webview_order) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_order);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    private void initListeners() {
        setBackListener(this);
    }

    private void initViews() {
        initViewOnBaseTitle("注册协议");
//        webView.loadUrl("file:///android_asset/register/register_order.html");
        webView.loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.ADD_REGINFO));
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
