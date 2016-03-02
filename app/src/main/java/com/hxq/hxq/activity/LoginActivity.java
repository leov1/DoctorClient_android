package com.hxq.hxq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxq.hxq.R;
import com.hxq.hxq.activity.register.RegisterActivity;
import com.hxq.hxq.bean.DoctorInfo;
import com.hxq.hxq.bean.DoctorResult;
import com.hxq.hxq.http.OkHttpClientManager;
import com.hxq.hxq.ui.swipebacklayout.SwipeBackActivity;
import com.hxq.hxq.utils.Constants;
import com.hxq.hxq.utils.SharedPreferences;
import com.hxq.hxq.utils.StringUtils;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxq on 2016/2/25.
 * 登陆页面
 */
public class LoginActivity extends SwipeBackActivity implements View.OnClickListener{

    private TextView forgetBtn;
    private TextView registerBtn;

    private EditText mobileEdit;
    private EditText passwordEdit;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListener();
    }

    private void initViews() {
        forgetBtn = (TextView) findViewById(R.id.forget_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        mobileEdit = (EditText) findViewById(R.id.mobile_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);

        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    private void initListener() {
        mobileEdit.setText(Constants.phone);
        passwordEdit.setText(Constants.password);

        forgetBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_btn:
               Intent forgetIntent = new Intent(this,ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_btn:
                Intent registerIntent = new Intent(this,RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.login_btn:
                Map<String,String> map = new HashMap<>();
                map.put("mobile", mobileEdit.getText().toString());
                map.put("password",passwordEdit.getText().toString());
                map.put("callback", "xch");
                OkHttpClientManager.postAsyn(Constants.LOGIN_URL, map, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        System.out.println("request--->" + request.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response1--->" + response.toString());
                        DoctorResult doctorResult = new Gson().fromJson(StringUtils.cutoutBracketToString(response), DoctorResult.class);
                        System.out.println("response2--->" + doctorResult.getServiceStaff().getDoctorUuid());
                        SharedPreferences.getInstance().putString("doctorUuid",doctorResult.getServiceStaff().getDoctorUuid());
                        SharedPreferences.getInstance().putBoolean(SharedPreferences.KEY_LOGIN_TYPE, true);
                        finish();
                    }
                });
                break;
        }
    }
}
