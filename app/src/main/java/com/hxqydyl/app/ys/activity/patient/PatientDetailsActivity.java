package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.case_report.AddCaseReportActivity;
import com.hxqydyl.app.ys.activity.case_report.FollowUpFormActivity;
import com.hxqydyl.app.ys.activity.case_report.InPatientCaseReportActivity;
import com.hxqydyl.app.ys.activity.case_report.OutPatientCaseReportActivity;
import com.hxqydyl.app.ys.adapter.PatientTreatInfoAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by shs.cn on 2016/3/19.
 */
public class PatientDetailsActivity extends BaseTitleActivity implements View.OnClickListener {
    //    患者基本信息
    private PatientSimpleInfoViewHolder simpleInfoViewHolder;

    //    患者治疗信息
    @InjectId(id = R.id.lvPatientTreatInfo)
    private ListView lvPatientTreatInfo;
    private PatientTreatInfoAdapter patientTreatInfoAdapter;
    private ArrayList<PatientTreatInfo> patientTreatInfoArrayList = new ArrayList<PatientTreatInfo>();

    //    给患者添加治疗信息
    @InjectId(id = R.id.lvPatientTreatInfo)
    private Button bCommunicateWithPatient;
    @InjectId(id = R.id.bAddCaseReport)
    private Button bAddCaseReport;
    @InjectId(id = R.id.bSelectNewFollowUpForPatient)
    private Button bSelectNewFollowUpForPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Patient patient = (Patient) getIntent().getSerializableExtra("patient");
        if (!TextUtils.isEmpty(patient.getName())) {
            initViewOnBaseTitle(patient.getName());
        }
        setBackListener(this);

        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        simpleInfoViewHolder.wholeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetailsActivity.this, PatientInfoActivity.class);
                startActivity(intent);
            }
        });
        InjectUtils.injectView(this);

        patientTreatInfoArrayList.add(new PatientTreatInfo("2015/03/20", 1, false));
        patientTreatInfoArrayList.add(new PatientTreatInfo("2015/03/21", 1, false));
        patientTreatInfoArrayList.add(new PatientTreatInfo("2015/03/19", 3, true));
        patientTreatInfoArrayList.add(new PatientTreatInfo("2015/03/11", 2, false));
        patientTreatInfoAdapter = new PatientTreatInfoAdapter(this, patientTreatInfoArrayList);
        lvPatientTreatInfo.setAdapter(patientTreatInfoAdapter);
        lvPatientTreatInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientTreatInfo treatInfo = (PatientTreatInfo) parent.getItemAtPosition(position);
                if (treatInfo != null) {
                    Intent intent = null;
                    switch (treatInfo.getTreatType()) {
                        case PatientTreatInfo.TREAT_TYPE_MEN_ZHEN:
                            intent = new Intent(PatientDetailsActivity.this, OutPatientCaseReportActivity.class);
                            break;
                        case PatientTreatInfo.TREAT_TYPE_BIAO_DAN:
                            intent = new Intent(PatientDetailsActivity.this, FollowUpFormActivity.class);
                            break;
                        case PatientTreatInfo.TREAT_TYPE_ZHU_YUAN:
                            intent = new Intent(PatientDetailsActivity.this, InPatientCaseReportActivity.class);
                            break;
                    }
                    if (intent != null) {
                        intent.putExtra("treat_info", treatInfo);
                        startActivity(intent);
                    }
                }
            }
        });


        bAddCaseReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.bCommunicateWithPatient:
                break;
            case R.id.bAddCaseReport:
                intent = new Intent(this, AddCaseReportActivity.class);
                break;
            case R.id.bSelectNewFollowUpForPatient:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
