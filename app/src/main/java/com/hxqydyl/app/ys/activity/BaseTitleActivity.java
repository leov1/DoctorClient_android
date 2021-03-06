package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.http.NetRequestListener;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.LoginManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseTitleActivity extends SwipeBackActivity implements NetRequestListener {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    public LoginManager.OnLoginSuccess onLoginSuccess;
    private ImageView backImg;
    public TextView topTv;
    public TextView leftTv;
    public ImageView rightImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initViewOnBaseTitle(String title) {
        try {
            backImg = (ImageView) findViewById(R.id.back_img);
            topTv = (TextView) findViewById(R.id.title_name);
            rightImg = (ImageView) findViewById(R.id.right_img);

            if (!TextUtils.isEmpty(title)) {
                topTv.setText(title);
            } else {
                topTv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initViewOnBaseTitle(String title, String left) {
        try {
            backImg = (ImageView) findViewById(R.id.back_img);
            topTv = (TextView) findViewById(R.id.title_name);
            leftTv = (TextView) findViewById(R.id.left_txt_btn);
            rightImg = (ImageView) findViewById(R.id.right_img);

            if (!TextUtils.isEmpty(title)) {
                topTv.setText(title);
            } else {
                topTv.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(left)) {
                leftTv.setText(left);
            } else {
                leftTv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLeftListener(View.OnClickListener l) {
        leftTv.setOnClickListener(l);
    }
    public void setLeftListener() {
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        rightImg.setVisibility(View.GONE);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    if (webView.canGoBack()){
                        leftTv.setVisibility(View.VISIBLE);
                    }else{
                        leftTv.setVisibility(View.GONE);
                    }
                } else {

                    finish();
                }
            }
        });

    }

    public void hideInput() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            // 隐藏软键盘
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        }
    }

    public void showInput(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
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
        this.onLoginSuccess = onLoginSuccess;
        if (TextUtils.isEmpty(LoginManager.getDoctorUuid())) {
            UIHelper.showLoginForResult(this,false);
        } else {
            onLoginSuccess.onLoginSuccess(LoginManager.getDoctorUuid());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UIHelper.LOGIN_REQUEST_CODE) {
                setResult(Activity.RESULT_OK);
                if (onLoginSuccess==null)
                    return;
                if (data.getBooleanExtra("isLogin", false)) {
                    onLoginSuccess.onLoginSuccess(LoginManager.getDoctorUuid());
                } else {
                    onLoginSuccess.onLoginfail();
                }
            }
        }
    }

}
