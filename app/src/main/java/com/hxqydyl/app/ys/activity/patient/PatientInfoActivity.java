package com.hxqydyl.app.ys.activity.patient;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.http.PatientNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientInfoActivity extends BaseRequstActivity implements View.OnClickListener {
    @InjectId(id = R.id.tvPhoneNumber)
    private TextView tvPhoneNumber;
    @InjectId(id = R.id.tvNick)
    private TextView tvNick;
    @InjectId(id = R.id.tvName)
    private TextView tvName;
    @InjectId(id = R.id.tvSex)
    private TextView tvSex;
    @InjectId(id = R.id.tvBirthday)
    private TextView tvBirthday;
    @InjectId(id = R.id.tvCard)
    private TextView tvCard;
    @InjectId(id = R.id.tvEmail)
    private TextView tvEmail;
    @InjectId(id = R.id.tvMarriage)
    private TextView tvMarriage;
    @InjectId(id = R.id.tvVocation)
    private TextView tvVocation;
    @InjectId(id = R.id.tvAddress)
    private TextView tvAddress;
    @InjectId(id = R.id.tvDiseaseProcess)
    private TextView tvDiseaseProcess;
    @InjectId(id = R.id.tvFirstSeeDoctorTime)
    private TextView tvFirstSeeDoctorTime;
    @InjectId(id = R.id.tvRelapse)
    private TextView tvRelapse;
    @InjectId(id = R.id.tvRelapseTimes)
    private TextView tvRelapseTimes;
    @InjectId(id = R.id.tvHeight)
    private TextView tvHeight;
    @InjectId(id = R.id.tvWeight)
    private TextView tvWeight;
    @InjectId(id = R.id.tvUseCondition)
    private TextView tvUseCondition;
    @InjectId(id = R.id.tvDescription)
    private TextView tvDescription;

    private Patient patient;
    private PatientNet patientNet;
    private String patientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientId = getIntent().getStringExtra("patientId");
        if(patientId==null){
            finish();
            return;
        }
        setContentView(R.layout.activity_patient_info);
        InjectUtils.injectView(this);
        initViewOnBaseTitle(getString(R.string.patient_info));
        setBackListener(this);

        refreshPatient();
    }

    private void refreshPatient() {
        if (patientNet == null) {
            patientNet = new PatientNet(this);
        }
        toNomalNetStringBack(toGetParams(toParamsBaen("uuid",patientId)),1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PATIENT_PERSIONAL_INFO, "2.0"),"获取患者详情中....");
        patientNet.getPatientPersionalInfo(patientId);
    }


    private void initViewData() {
        tvPhoneNumber.setText(patient.getMobile());
        tvNick.setText(patient.getNickname());
        tvName.setText(patient.getRealName());
        tvSex.setText(patient.getSex());
        tvBirthday.setText(patient.getBirthday());
        tvCard.setText(patient.getCertCode());
        tvEmail.setText(patient.getEmail());
        tvMarriage.setText(patient.getMarryState());
        tvVocation.setText(patient.getIndustry());
        tvAddress.setText(patient.getAddress());
        tvDiseaseProcess.setText(patient.getDiseaseProcess());
        tvFirstSeeDoctorTime.setText(patient.getFirstSeeDoctorTime());
        tvRelapse.setText(patient.getRelapse());
        tvRelapseTimes.setText(patient.getRelapseTimes());
        tvHeight.setText(patient.getHeight());
        tvWeight.setText(patient.getWeight());
        tvUseCondition.setText(patient.getUseCondition());
        tvDescription.setText(patient.getUseCondition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        patient = (Patient) result;
        initViewData();
    }
}
