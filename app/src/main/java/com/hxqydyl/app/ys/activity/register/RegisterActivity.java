package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.register.CaptchaNet;
import com.hxqydyl.app.ys.http.register.RegisterFirstNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.Validator;

import framework.listener.RegisterSucListener;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseTitleActivity implements View.OnClickListener, CaptchaNet.OnCaptchaNetListener, RegisterFirstNet.OnRegisterFirstListener, RegisterSucListener {

    private TextView registerOrderBtn;
    private TextView loginBtn;
    private Button nextBtn;//下一步
    private Button codeBtn;//获取验证码
    private EditText captchaEdit;//验证码
    private EditText mobileEdit;//手机号
    private EditText passwordEdit;//密码
    private CheckBox agreeCheckBox;
    private String captcha = "";//验证码
    private String mobile = "";//手机号
    private String password = "";//密码

    private CaptchaNet captchaNet;
    private RegisterFirstNet registerFirstNet;

    private Intent intent;

    private int timeCount = 60;
    public static final int GET_VERIFICATION = 0x23;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_VERIFICATION:
                    codeBtn.setText(timeCount + "秒");
                    if (timeCount > 0) {
                        timeCount--;
                        handler.sendEmptyMessageDelayed(GET_VERIFICATION, 1000);
                    } else {
                        codeBtn.setEnabled(true);
                        codeBtn.setText("获取验证码");
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initListeners();
    }

    private void initListeners() {
        setBackListener(this);
        registerOrderBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        codeBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    private void initViews() {
        initViewOnBaseTitle("注册");

        addRegisterListener(this);

        captchaNet = new CaptchaNet();
        captchaNet.setListener(this);
        registerFirstNet = new RegisterFirstNet();
        registerFirstNet.setListener(this);

        registerOrderBtn = (TextView) findViewById(R.id.textview_register_order);
        loginBtn = (TextView) findViewById(R.id.login_btn);
        nextBtn = (Button) findViewById(R.id.next_btn);
        codeBtn = (Button) findViewById(R.id.btn_code);

        mobileEdit = (EditText) findViewById(R.id.mobile_edit);
        captchaEdit = (EditText) findViewById(R.id.captcha_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);

        agreeCheckBox = (CheckBox) findViewById(R.id.checkbox_agree);

    }

    @Override
    public void onClick(View v) {
        String isCan = "";
        switch (v.getId()) {
            case R.id.textview_register_order:
                intent = new Intent(this, RegisterOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.next_btn://下一步
//              String isPass = validateInfo();
//              if (TextUtils.isEmpty(isPass)){
//                 intent = new Intent(this,EvpiUserActivity.class);
//                 startActivity(intent);
//              }else{
//                  UIHelper.ToastMessage(this,isPass);
//              }
                isCan = validateInfo();
                if (TextUtils.isEmpty(isCan)) {
                    registerOne();
                } else {
                    UIHelper.ToastMessage(this, isCan);
                }
                break;
            case R.id.btn_code://获取验证码
                isCan = validateMobile();
                if (TextUtils.isEmpty(isCan)) {
                    obtainCaptcha();
                } else {
                    UIHelper.ToastMessage(this, isCan);
                }
                break;
            case R.id.login_btn:
                UIHelper.showLogin(RegisterActivity.this);
                finish();
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }

    /**
     * 获取验证码网络请求
     */
    private void obtainCaptcha() {
        if (!TextUtils.isEmpty(validateMobile())) {
            UIHelper.ToastMessage(RegisterActivity.this, validateMobile());
            return;
        }
        codeBtn.setEnabled(false);
        timeCount = 60;
        handler.sendEmptyMessage(GET_VERIFICATION);
        captchaNet.obtainCaptcha(mobile);
    }

    /**
     * 注册请求
     */
    private void registerOne() {
        showDialog("请稍等...");
        registerFirstNet.registerFirst(mobile, password, captcha);
    }

    /**
     * 验证输入内容
     */
    private String validateInfo() {
        String isMobile = validateMobile();
        if (!TextUtils.isEmpty(isMobile)) return isMobile;

        captcha = captchaEdit.getText().toString();
//        if (TextUtils.isEmpty(captcha)) return "验证码不能为空";

        password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(password)) return "密码不能为空";
        if (!Validator.isPassword(password)) return "密码只能字母加数字";

        if (!agreeCheckBox.isChecked()) return "请同意医师协议";
        return "";
    }

    /**
     * 验证输入的手机号码
     *
     * @return
     */
    private String validateMobile() {
        mobile = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(mobile)) return "手机号码不能为空";
        if (isMobile(mobile)) return "手机号码格式不正确";
        return "";
    }

    private boolean isMobile(String str){
        return str.length() < 11;
    }

    @Override
    public void requestCaptchaNetSuc(CaptchaResult captchaResult) {
        dismissDialog();
        System.out.println("captcha--->" + captchaResult.toString());
    }

    @Override
    public void requestCaptchaNetFail() {

        UIHelper.ToastMessage(RegisterActivity.this, "请求出错");
    }

    @Override
    public void requestRegisterFirstNetSuccess(RegisterFirst registerFirst) {
        dismissDialog();
        UIHelper.ToastMessage(RegisterActivity.this, registerFirst.getQuery().getMessage());
        if (registerFirst.getQuery().getSuccess().equals("1")) {
            LoginManager.setRegisterUuid(registerFirst.getDoctorUuid());
            intent = new Intent(this, EvpiUserActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void requestRegisterFirstNetFail() {
        dismissDialog();
        UIHelper.ToastMessage(RegisterActivity.this, "请求出错");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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
}
