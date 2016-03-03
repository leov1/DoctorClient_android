package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * 完善姓名邮箱信息页面
 */
public class EvpiUserActivity extends BaseTitleActivity implements View.OnClickListener{

    private Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_user);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");
        nextBtn = (Button) findViewById(R.id.next_btn);
    }

    private void initListeners() {
        setBackListener(this);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_btn:
                Intent nextIntent = new Intent(this,EvpiAddressActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }
}
