package com.hxqydyl.app.ys.activity.follow;

import android.os.Bundle;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * 患者信息页面
 */
public class PatientInfoActivity extends BaseTitleActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        initViewOnBaseTitle("患者信息");
        setBackListener();

    }
}
