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
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseTitleActivity implements View.OnClickListener{

    private TextView registerOrderBtn;
    private Button nextBtn;//下一步
    private Button codeBtn;//获取验证码
    private EditText codeEdit;//验证码
    private EditText mobileEdit;//手机号

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

        mobileEdit = (EditText) findViewById(R.id.mobile_edit);

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

    /**
     * 获取验证码
     */
    private void  requestForVerification(){
        codeBtn.setEnabled(false);
        timeCount = 60;
        handler.sendEmptyMessage(GET_VERIFICATION);

        Map<String,String> params = new HashMap<>();
        OkHttpClientManager.getAsyn(Constants.GET_VERIFICATION_CODE+"?mobile="+mobileEdit.getText().toString()+"&callback=hxq", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) throws JSONException {
               System.out.println("response----->"+response);
            }
        });
    }
}
