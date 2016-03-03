package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseTitleActivity implements View.OnClickListener{

    private TextView registerOrderBtn;
    private Button nextBtn;//下一步
    private Button codeBtn;//获取验证码
    private EditText codeEdit;//验证码

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
        registerOrderBtn = (TextView) findViewById(R.id.textview_register_order);
        nextBtn = (Button) findViewById(R.id.next_btn);
        codeBtn = (Button) findViewById(R.id.btn_code);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.textview_register_order:
              Intent orderIntent = new Intent(this,RegisterOrderActivity.class);
              startActivity(orderIntent);
              break;
          case R.id.next_btn://下一步
              Intent nextIntent = new Intent(this,EvpiUserActivity.class);
              startActivity(nextIntent);
              break;
          case R.id.btn_code:
              requestForVerification();
              break;
          case R.id.back_img:
              finish();
              break;
      }
    }

    private void  requestForVerification(){
        codeBtn.setEnabled(false);
        timeCount = 60;
        handler.sendEmptyMessage(GET_VERIFICATION);
    }
}
