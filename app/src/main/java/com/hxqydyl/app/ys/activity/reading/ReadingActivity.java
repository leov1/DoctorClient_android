package com.hxqydyl.app.ys.activity.reading;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

public class ReadingActivity extends BaseTitleActivity implements View.OnClickListener{

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initViews();
        initListeners();
    }

    private void initViews(){
        initViewOnBaseTitle("阅读");
        webView = (WebView) findViewById(R.id.webview);
    }

    private void initListeners() {
        setBackListener(this);

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
