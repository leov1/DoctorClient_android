package com.hxqydyl.app.ys.activity.patient;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * Created by shs.cn on 2016/3/19.
 */
public class PatientActivity extends BaseTitleActivity implements View.OnClickListener {
    private ImageView ivPatientPortrait;
    private TextView tvPatientName;
    private TextView tvPatientAge;
    private TextView tvPatientFollowTime;
    private TextView tvDescription;
    private TextView tvDeletePatient;
    private ListView lvPatientCaseReport;
    private Button bCommunicateWithPatient;
    private Button bAddCaseReport;
    private Button bSelectNewFollowUpForPatient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        String patientName = getIntent().getStringExtra("patientName");
        if(!TextUtils.isEmpty(patientName)){
            initViewOnBaseTitle(patientName);
        }
        setBackListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                this.finish();
                break;
            case R.id.bCommunicateWithPatient:
                break;
            case R.id.bAddCaseReport:
                break;
            case R.id.bSelectNewFollowUpForPatient:
                break;
        }
    }
}
