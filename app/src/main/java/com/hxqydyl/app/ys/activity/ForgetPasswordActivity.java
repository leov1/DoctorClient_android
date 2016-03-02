package com.hxqydyl.app.ys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends SwipeBackActivity implements View.OnClickListener{

    private TextView loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();
        initListeners();
    }

    private void initListeners() {
        loginBtn.setOnClickListener(this);
    }

    private void initViews() {
        loginBtn = (TextView) findViewById(R.id.login_btn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                Intent loginIntent = new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}
