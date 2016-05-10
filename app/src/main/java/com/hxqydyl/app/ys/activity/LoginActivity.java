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
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;

import framework.listener.RegisterSucListener;

/**
 * Created by hxq on 2016/2/25.
 * 登陆页面
 */
public class LoginActivity extends BaseRequstActivity implements View.OnClickListener, RegisterSucListener {

    private TextView forgetBtn;
    private TextView registerBtn;

    private EditText mobileEdit;
    private EditText passwordEdit;

    private Button loginBtn;
    private boolean isNeedCallback = false;

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
        setBackListener(this);

        forgetBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                setLoginResult();
                break;
            case R.id.forget_btn:
                Intent forgetIntent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_btn:
                UIHelper.showRegister(this);
                break;
            case R.id.login_btn:
                toNomalNet(toPostParams(toParamsBaen("mobile", mobileEdit.getText().toString()),toParamsBaen("password", passwordEdit.getText().toString())), DoctorResult.class,1, UrlConstants.getWholeApiUrl(UrlConstants.LOGIN_URL,"2.0"),"登陆中...");
                break;
        }
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        super.onSuccessToBean(bean, flag);
        LoginManager.setDoctorUuid(((DoctorResult)bean).value.getDoctorUuid());
        SharedPreferences.getInstance().putString(SharedPreferences.USER_INFO_COMPLETE,((DoctorResult)bean).value.getSate());
        UIHelper.ToastMessage(LoginActivity.this, "登陆成功");
        setLoginResult();
    }

    public void setLoginResult() {
        if (isNeedCallback) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onRegisterSuc() {
        setLoginResult();
    }

    @Override
    protected void onDestroy() {
        removeRegisterListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setLoginResult();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
