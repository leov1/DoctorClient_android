package com.hxq.hxq.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hxq.hxq.R;

/**
 * 完善单位信息页面
 */
public class EvpiAddressActivity extends Activity implements View.OnClickListener{

    private Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_address);

        initViews();
        initListeners();
    }

    private void initViews(){
        nextBtn = (Button) findViewById(R.id.next_btn);
    }

    private void initListeners() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_btn:
                Intent nextIntent = new Intent(this,GoodChoiceActivity.class);
                startActivity(nextIntent);
                break;
        }
    }
}
