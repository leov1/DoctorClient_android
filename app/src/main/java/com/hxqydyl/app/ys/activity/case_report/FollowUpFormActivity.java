package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * Created by white_ash on 2016/3/23.
 */
public class FollowUpFormActivity extends BaseTitleActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_form);

        initViewOnBaseTitle(getString(R.string.patient_details));
        setBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }
}
