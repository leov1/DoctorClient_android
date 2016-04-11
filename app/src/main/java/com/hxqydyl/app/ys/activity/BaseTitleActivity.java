package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.register.listener.RegisterSucListener;
import com.hxqydyl.app.ys.activity.register.listener.RegisterSucMag;
import com.hxqydyl.app.ys.http.NetRequestListener;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseTitleActivity extends SwipeBackActivity implements NetRequestListener {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    public LoginManager.OnLoginSuccess onLoginSuccess;
    private ImageView backImg;
    public TextView topTv;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initViewOnBaseTitle(String title) {
        try {
            backImg = (ImageView) findViewById(R.id.back_img);
            topTv = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(title)) {
                topTv.setText(title);
            } else {
                topTv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackListener(View.OnClickListener l) {
        backImg.setOnClickListener(l);
    }

    public void setBackListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setWebBackListener(final WebView webView) {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });

    }

    public void addRegisterListener(RegisterSucListener listener) {
        if (listener != null)
            RegisterSucMag.getInstance().addRegisterSucListeners(listener);
    }

    public void removeRegisterListener(RegisterSucListener listener) {
        if (listener != null)
            RegisterSucMag.getInstance().removeRegisterSucListeners(listener);
    }

    public void showDialog(String text) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(text);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void dismissDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismissWithAnimation();
        }
    }

    @Override
    public void onSend(String url) {
        showDialog("加载中");
    }

    @Override
    public void onResponse(String url, Object result) {
        dismissDialog();
    }

    @Override
    public void onError(String url, Exception exception) {
        dismissDialog();
        Toast.makeText(this, "服务器异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress(String url, Float progress) {
    }

    public void getDoctorUuid(LoginManager.OnLoginSuccess onLoginSuccess) {
        this.onLoginSuccess=onLoginSuccess;
        if (TextUtils.isEmpty(LoginManager.getDoctorUuid())) {
            UIHelper.showLoginForResult(this);
        }else{
            onLoginSuccess.onLoginSuccess(LoginManager.getDoctorUuid());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UIHelper.LOGIN_REQUEST_CODE) {
                if (data.getBooleanExtra("isLogin",false)){
                    onLoginSuccess.onLoginSuccess(LoginManager.getDoctorUuid());
                }else{
                    onLoginSuccess.onLoginfail();
                }
            }
        }
    }
}
