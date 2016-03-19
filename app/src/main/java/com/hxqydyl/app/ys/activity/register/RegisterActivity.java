package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.CaptchaResult;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.register.CaptchaNet;
import com.hxqydyl.app.ys.http.register.RegisterFirstNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.hxqydyl.app.ys.utils.Utils;
import com.hxqydyl.app.ys.utils.Validator;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseTitleActivity implements View.OnClickListener,CaptchaNet.OnCaptchaNetListener,RegisterFirstNet.OnRegisterFirstListener{

    private TextView registerOrderBtn;
    private Button nextBtn;//下一步
    private Button codeBtn;//获取验证码
    private EditText captchaEdit;//验证码
    private EditText mobileEdit;//手机号
    private EditText passwordEdit;//密码
    private CheckBox agreeCheckBox;

    private String captcha = "";//验证码
    private String captchaRight = "";//系统返回的验证码
    private String mobile = "";//手机号
    private String password = "";//密码

    private CaptchaNet captchaNet;
    private RegisterFirstNet registerFirstNet;

    private Intent intent;
    private SweetAlertDialog pDialog;

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
        };
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
    }

    private void initViews() {
        initViewOnBaseTitle("注册");

        captchaNet = new CaptchaNet();
        captchaNet.setListener(this);
        registerFirstNet = new RegisterFirstNet();
        registerFirstNet.setListener(this);

        registerOrderBtn = (TextView) findViewById(R.id.textview_register_order);
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
      switch (v.getId()){
          case R.id.textview_register_order:
              intent = new Intent(this,RegisterOrderActivity.class);
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
              if (TextUtils.isEmpty(isCan)){
                  registerOne();
              }else {
                  UIHelper.ToastMessage(this,isCan);
              }
              break;
          case R.id.btn_code://获取验证码
              isCan = validateMobile();
              if (TextUtils.isEmpty(isCan)) {
                  obtainCaptcha();
              }else {
                  UIHelper.ToastMessage(this,isCan);
              }
              break;
          case R.id.back_img:
              finish();
              break;
      }
    }

    /**
     * 获取验证码网络请求
     */
    private void obtainCaptcha(){
        codeBtn.setEnabled(false);
        timeCount = 60;
        handler.sendEmptyMessage(GET_VERIFICATION);

        captchaNet.obtainCaptcha(mobile);
    }

    /**
     * 注册请求
     */
    private void registerOne(){
       registerFirstNet.registerFirst(mobile, password,"123456");
    }

    /**
     * 验证输入内容
     */
    private String validateInfo(){
        String isMobile = validateMobile();
        if(!TextUtils.isEmpty(isMobile)) return isMobile;

//        captcha = captchaEdit.getText().toString();
//        if (TextUtils.isEmpty(captcha)) return "验证码不能为空";
//        if (!captcha.equals(captchaRight)) return "验证码不正确";

        password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(password)) return "密码不能为空";
        if (!Validator.isPassword(password)) return "密码只能字母加数字";

        if (!agreeCheckBox.isChecked()) return "请同意医师协议";
        return "";
    }

    /**
     * 验证输入的手机号码
     * @return
     */
    private String validateMobile(){
        mobile = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(mobile)) return "手机号码不能为空";
        if (!Validator.isMobile(mobile)) return "手机号码格式不正确";
        return "";
    }

    private void showDialog(String text){
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(text);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    private void dismissDialog(){
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismissWithAnimation();
        }
    }

    @Override
    public void requestCaptchaNetSuc(CaptchaResult captchaResult) {
        dismissDialog();
        System.out.println("captcha--->"+captchaResult.toString());
        captchaRight = captchaResult.getCaptcha();
    }

    @Override
    public void requestCaptchaNetFail() {

    }

    @Override
    public void requestRegisterFirstNetSuccess(RegisterFirst registerFirst) {
        UIHelper.ToastMessage(RegisterActivity.this, registerFirst.getQuery().getMessage());
        if (registerFirst.getQuery().getMessage().equals("操作成功")){
            intent = new Intent(this,EvpiUserActivity.class);
            intent.putExtra("doctorUuid",registerFirst.getDoctorUuid());
            startActivity(intent);
        }
    }

    @Override
    public void requestRegisterFirstNetFail() {

    }
}
