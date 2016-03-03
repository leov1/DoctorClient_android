package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    }

    private void initViews() {
        initViewOnBaseTitle("注册");
        registerOrderBtn = (TextView) findViewById(R.id.textview_register_order);
        nextBtn = (Button) findViewById(R.id.next_btn);
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
          case R.id.back_img:
              finish();
              break;
      }
    }
}
