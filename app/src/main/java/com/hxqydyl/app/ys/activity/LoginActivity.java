package com.hxqydyl.app.ys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.register.RegisterActivity;
import com.hxqydyl.app.ys.bean.DoctorInfo;
import com.hxqydyl.app.ys.bean.DoctorResult;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.login.LoginNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxq on 2016/2/25.
 * 登陆页面
 */
public class LoginActivity extends BaseTitleActivity implements View.OnClickListener,LoginNet.OnLoginNetListener{

    private TextView forgetBtn;
    private TextView registerBtn;

    private EditText mobileEdit;
    private EditText passwordEdit;

    private Button loginBtn;

    private LoginNet loginNet = new LoginNet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListener();
    }

    private void initViews() {
        initViewOnBaseTitle(getResources().getString(R.string.login));
        forgetBtn = (TextView) findViewById(R.id.forget_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        mobileEdit = (EditText) findViewById(R.id.mobile_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);

        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    private void initListener() {
        loginNet.setmListener(this);
        setBackListener(this);
        mobileEdit.setText(Constants.phone);
        passwordEdit.setText(Constants.password);

        forgetBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.forget_btn:
                Intent forgetIntent = new Intent(this,ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_btn:
               UIHelper.showRegister(this);
                break;
            case R.id.login_btn:
                loginNet.loginData(mobileEdit.getText().toString(), passwordEdit.getText().toString());
                break;
        }
    }

    @Override
    public void requestLoginNetSuccess(DoctorInfo doctorInfo) {
        System.out.println("doctorinfo--->"+doctorInfo);
        SharedPreferences.getInstance().putString("doctorUuid", doctorInfo.getDoctorUuid());
        LoginManager.setLoginStatus(true);
        UIHelper.ToastMessage(LoginActivity.this, "登陆成功");
        finish();
    }

    @Override
    public void requestLoginNetFail(int statusCode) {
        UIHelper.ToastMessage(LoginActivity.this,"登陆失败");
    }

    @Override
    public void registerOneSuccess(DoctorInfo doctorInfo) {

    }

    @Override
    public void registerTwoSuccess(String uuid) {

    }

    @Override
    public void registerThreeSuccess(DoctorInfo doctorInfo) {

    }

    @Override
    public void requestFail(int statusCode) {

    }
}
