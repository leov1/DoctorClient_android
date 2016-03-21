package com.hxqydyl.app.ys.activity.patient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientInfoActivity extends BaseTitleActivity implements View.OnClickListener {
    private TextView tvPhoneNumber;
    private TextView tvNick;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvBirthday;
    private TextView tvCard;
    private TextView tvEmail;
    private TextView tvMarriage;
    private TextView tvVocation;
    private TextView tvAddress;
    private TextView tvDiseaseProcess;
    private TextView tvFirstSeeDoctorTime;
    private TextView tvRelapse;
    private TextView tvRelapseTimes;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvUseCondition;
    private TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        initViewOnBaseTitle(getString(R.string.patient_info));
        setBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
