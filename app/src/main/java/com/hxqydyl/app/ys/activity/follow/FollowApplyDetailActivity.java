package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientInfoActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;

/**
 * 随访申请
 */
public class FollowApplyDetailActivity extends BaseTitleActivity
        implements View.OnClickListener {

    private RelativeLayout rlPatient;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply_detail);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("随访申请详情");
        rlPatient = (RelativeLayout) findViewById(R.id.rl_patient);
        btnApply = (Button) findViewById(R.id.btnApply);
    }

    private void initListeners() {
        setBackListener(this);
        rlPatient.setOnClickListener(this);
        btnApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.rl_patient:
                Intent intent = new Intent(this, PatientInfoActivity.class);
                intent.putExtra("patientId", 1);
                startActivity(intent);
                break;
            case R.id.btnApply:
                Intent okIntent = new Intent(this, FollowApplyOkActivity.class);
                startActivity(okIntent);
                break;


        }
    }

}
