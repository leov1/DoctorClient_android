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
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.RegiserResult;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.Validator;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;

import java.util.ArrayList;

import framework.listener.RegisterSucListener;
import framework.listener.RegisterSucMag;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseRequstActivity implements View.OnClickListener{

    @InjectId(id = R.id.textview_register_order)
    private TextView registerOrderBtn;
    @InjectId(id = R.id.login_btn)
    private TextView loginBtn;
    @InjectId(id = R.id.next_btn)
    private Button nextBtn;//下一步
    @InjectId(id = R.id.btn_code)
    private Button codeBtn;//获取验证码
    @InjectId(id = R.id.captcha_edit)
    private EditText captchaEdit;//验证码
    @InjectId(id = R.id.mobile_edit)
    private EditText mobileEdit;//手机号
    @InjectId(id = R.id.password_edit)
    private EditText passwordEdit;//密码
    @InjectId(id = R.id.visit_edit)
    private EditText visitEdit;//邀请码
    @InjectId(id = R.id.checkbox_agree)
    private CheckBox agreeCheckBox;

    private String captcha = "";//验证码
    private String mobile = "";//手机号
    private String password = "";//密码

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
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InjectUtils.injectView(this);
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
        GetParams params = toGetParams(toParamsBaen("mobile", mobile));
        toNomalNetStringBack(params,2,UrlConstants.getWholeApiUrl(UrlConstants.GET_VERIFICATION_CODE),"");
    }

    /**
     * 注册请求
     */
    private void registerOne() {
        PostPrams params = toPostParams(toParamsBaen("mobile",mobile),toParamsBaen("password", password),toParamsBaen("captcha", captcha));
        toNomalNet(params, RegiserResult.class,1,UrlConstants.getWholeApiUrl(UrlConstants.REGISTER_ONE,"2.0"),"请稍等...");
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
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag){
            case 1: //注册成功
                UIHelper.ToastMessage(RegisterActivity.this, "注册成功");
                    LoginManager.setDoctorUuid(((RegiserResult)bean).value.getUuid());
                    SharedPreferences.getInstance().putString(SharedPreferences.USER_INFO_COMPLETE,((RegiserResult)bean).value.getSate());
                    removeBeforViews();
                    finish();
                break;

        }
    }

    @Override
    public void onSuccessToString(String json, int flag) {
        switch (flag){
            case 2://验证码
                CaptchaResult captchaResult = JsonUtils.JsonCaptchResult(json);
                UIHelper.ToastMessage(RegisterActivity.this, captchaResult.getQuery().getMessage());
                break;
        }
    }

    /**
     * 观察者移除之前页面
     */
    private void removeBeforViews() {
        ArrayList<RegisterSucListener> registerSucListeners = RegisterSucMag.getInstance().downloadListeners;
        if (registerSucListeners == null || registerSucListeners.size() == 0) return;
        for (int i = 0; i < registerSucListeners.size(); i++) {
            registerSucListeners.get(i).onRegisterSuc();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
