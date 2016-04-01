package com.hxqydyl.app.ys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.http.login.UpdatePasswordNet;
import com.hxqydyl.app.ys.http.register.CaptchaNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.Validator;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseTitleActivity implements View.OnClickListener, UpdatePasswordNet.OnUpdatePasswordListener, CaptchaNet.OnCaptchaNetListener {

    private TextView loginBtn;
    private Button next_btn;
    private Button btn_code;
    private EditText mobile_edit;
    private EditText captcha_edit;
    private EditText password_edit;

    private String captcha = "";//验证码
    private String mobile = "";//手机号
    private String password = "";//密码

    private Intent intent;
    private UpdatePasswordNet updatePasswordNet;
    private CaptchaNet captchaNet;

    private int timeCount = 60;
    public static final int GET_VERIFICATION = 0x23;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_VERIFICATION:
                    btn_code.setText(timeCount + "秒");
                    if (timeCount > 0) {
                        timeCount--;
                        handler.sendEmptyMessageDelayed(GET_VERIFICATION, 1000);
                    } else {
                        btn_code.setEnabled(true);
                        btn_code.setText("获取验证码");
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
        setContentView(R.layout.activity_forget_password);

        initViews();
        initListeners();
    }

    private void initListeners() {
        loginBtn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        setBackListener(this);
    }

    private void initViews() {
        initViewOnBaseTitle(getResources().getString(R.string.forget_pw_text));
        loginBtn = (TextView) findViewById(R.id.login_btn);
        next_btn = (Button) findViewById(R.id.next_btn);
        btn_code = (Button) findViewById(R.id.btn_code);
        mobile_edit = (EditText) findViewById(R.id.mobile_edit);
        captcha_edit = (EditText) findViewById(R.id.captcha_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);

        updatePasswordNet = new UpdatePasswordNet();
        captchaNet = new CaptchaNet();
        updatePasswordNet.setListener(this);
        captchaNet.setListener(this);

    }

    @Override
    public void onClick(View v) {
        String isCan = "";
        switch (v.getId()) {
            case R.id.login_btn:
                UIHelper.showLogin(ForgetPasswordActivity.this);
                break;
            case R.id.btn_code:
                isCan = validateMobile();
                if (TextUtils.isEmpty(isCan)) {
                    obtainCaptcha();
                } else {
                    UIHelper.ToastMessage(this, isCan);
                }
                break;
            case R.id.back_img:
                finish();
                break;
            case R.id.next_btn:
                isCan = validateInfo();
                if (TextUtils.isEmpty(isCan)) {
                    updatePassword();
                } else {
                    UIHelper.ToastMessage(this, isCan);
                }
                break;
        }
    }

    /**
     * 修改网络请求
     */
    private void updatePassword() {
        updatePasswordNet.updatePassword(mobile, password, captcha);
    }

    /**
     * 获取验证码网络请求
     */
    private void obtainCaptcha() {
        if (!TextUtils.isEmpty(validateMobile())){
            UIHelper.ToastMessage(ForgetPasswordActivity.this,validateMobile());
            return;
        }

        btn_code.setEnabled(false);
        timeCount = 60;
        handler.sendEmptyMessage(GET_VERIFICATION);

        captchaNet.obtainCaptcha(mobile);
    }

    /**
     * 验证输入内容
     */
    private String validateInfo() {
        String isMobile = validateMobile();
        if (!TextUtils.isEmpty(isMobile)) return isMobile;

        captcha = captcha_edit.getText().toString();
        if (TextUtils.isEmpty(captcha)) return "验证码不能为空";

        password = password_edit.getText().toString();
        if (TextUtils.isEmpty(password)) return "密码不能为空";
        if (!Validator.isPassword(password)) return "密码只能字母加数字";

        return "";
    }

    /**
     * 验证输入的手机号码
     *
     * @return
     */
    private String validateMobile() {
        mobile = mobile_edit.getText().toString();
        if (TextUtils.isEmpty(mobile)) return "手机号码不能为空";
        if (!Validator.isMobile(mobile)) return "手机号码格式不正确";
        return "";
    }

    @Override
    public void requestUpdatePwSuc(Query query) {
        System.out.println("query--->" + query.toString());
        if (query == null) {
            UIHelper.ToastMessage(ForgetPasswordActivity.this, "请求出错");
            return;
        }
        if (query.getSuccess().equals("1")) {
            UIHelper.ToastMessage(ForgetPasswordActivity.this, "修改成功");
            finish();
        } else {
            UIHelper.ToastMessage(ForgetPasswordActivity.this, query.getMessage());
        }
    }

    @Override
    public void requestUpdatePwFail() {

    }

    @Override
    public void requestCaptchaNetSuc(CaptchaResult captchaResult) {
        System.out.println("capche--->"+captchaResult.getCaptcha());
        if (captchaResult == null)return;
        UIHelper.ToastMessage(ForgetPasswordActivity.this,captchaResult.getQuery().getMessage());
    }

    @Override
    public void requestCaptchaNetFail() {

    }
}
