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
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by shs.cn on 2016/3/19.
 */
public class PatientDetailsActivity extends BaseTitleActivity implements View.OnClickListener {
    public static final String KEY_PATIENT = "patient";
    public static final String KEY_TREAT_INFO = "treat_info";
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

    private CaseReportNet caseReportNet;
    private Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        patient = (Patient) getIntent().getSerializableExtra(KEY_PATIENT);
        if (patient!=null&&!TextUtils.isEmpty(patient.getName())) {
            initViewOnBaseTitle(patient.getName());
        }
        setBackListener(this);

        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        simpleInfoViewHolder.setPatient(patient);
        simpleInfoViewHolder.wholeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetailsActivity.this, PatientInfoActivity.class);
                startActivity(intent);
            }
        });
        InjectUtils.injectView(this);

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
                        intent.putExtra(KEY_TREAT_INFO, treatInfo);
                        intent.putExtra(KEY_PATIENT,patient);
                        startActivity(intent);
                    }
                }
            }
        });

        bAddCaseReport.setOnClickListener(this);
        caseReportNet = new CaseReportNet(this);
        caseReportNet.getAllTreatInfoRecordOfPatient("efec4e3969234184840e37033fc1d3fd","88888888");
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
            intent.putExtra(KEY_PATIENT,patient);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.GET_PATIENT_TREAT_RECORD)) {
            patientTreatInfoArrayList.clear();
            patientTreatInfoArrayList.addAll((ArrayList<PatientTreatInfo>) result);
            patientTreatInfoAdapter.notifyDataSetChanged();
        }
    }
}
