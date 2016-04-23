package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.register.DoctorResult;
import com.hxqydyl.app.ys.http.login.LoginNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;

import framework.listener.RegisterSucListener;

/**
 * Created by hxq on 2016/2/25.
 * 登陆页面
 */
public class LoginActivity extends BaseTitleActivity implements View.OnClickListener, LoginNet.OnLoginNetListener, RegisterSucListener {

    private TextView forgetBtn;
    private TextView registerBtn;

    private EditText mobileEdit;
    private EditText passwordEdit;

    private Button loginBtn;
    private boolean isNeedCallback = false;
    private LoginNet loginNet = new LoginNet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListener();
    }

    private void initViews() {
        isNeedCallback = getIntent().getBooleanExtra("isNeedCallBack", false);
        initViewOnBaseTitle(getResources().getString(R.string.login));

        addRegisterListener(this);

        forgetBtn = (TextView) findViewById(R.id.forget_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        mobileEdit = (EditText) findViewById(R.id.mobile_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);

        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    private void initListener() {
        loginNet.setmListener(this);
        setBackListener(this);

        forgetBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                setLoginResult(LoginManager.isHasLogin());
                finish();
                break;
            case R.id.forget_btn:
                Intent forgetIntent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_btn:
                UIHelper.showRegister(this);
                break;
            case R.id.login_btn:
                showDialog("登陆中...");
                loginNet.loginData(mobileEdit.getText().toString(), passwordEdit.getText().toString());
                break;
        }
    }

    @Override
    public void requestLoginNetSuccess(DoctorResult doctorResult) {
        try {
            System.out.println("doctorinfo--->" + doctorResult);
            dismissDialog();
            if (doctorResult == null) return;
            if (doctorResult.getQuery().getSuccess().equals("1")) {
                SharedPreferences.getInstance().putString("doctorUuid", doctorResult.getServiceStaff().getDoctorUuid());
                UIHelper.ToastMessage(LoginActivity.this, "登陆成功");
                setLoginResult(LoginManager.isHasLogin());

                finish();
            } else {
                UIHelper.ToastMessage(LoginActivity.this, doctorResult.getQuery().getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            UIHelper.ToastMessage(LoginActivity.this, "登陆失败");
        }


    }

    public void setLoginResult(boolean islogin) {
        if (isNeedCallback) {
            Intent intent = new Intent();
            intent.putExtra("isLogin", islogin);
            setResult(Activity.RESULT_OK, intent);
        }
    }

    @Override
    public void requestLoginNetFail() {
        dismissDialog();
        UIHelper.ToastMessage(LoginActivity.this, "登陆失败");
    }

    @Override
    public void onRegisterSuc() {
        finish();
    }

    @Override
    protected void onDestroy() {
        removeRegisterListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setLoginResult(LoginManager.isHasLogin());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
