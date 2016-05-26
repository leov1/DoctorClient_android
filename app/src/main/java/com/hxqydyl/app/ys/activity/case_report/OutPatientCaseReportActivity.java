package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient.PatientSimpleInfoViewHolder;
import com.hxqydyl.app.ys.adapter.CaseHistoryAdapter;
import com.hxqydyl.app.ys.bean.CaseReport;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/23.
 */
public class OutPatientCaseReportActivity extends BaseTitleActivity implements View.OnClickListener{
    private PatientSimpleInfoViewHolder patientSimpleInfo;
    @InjectId(id = R.id.tvTreatMentType)
    private TextView tvTreatMentType;
    @InjectId(id = R.id.tvTreatmentTime)
    private TextView tvTreatmentTime;
    @InjectId(id = R.id.tvHospital)
    private TextView tvHospital;
    @InjectId(id = R.id.tvDoctor)
    private TextView tvDoctor;
    @InjectId(id = R.id.gvPatientCaseHistory)
    private GridView gvPatientCaseHistory;

    private CaseHistoryAdapter caseHistoryAdapter;
    private ArrayList<Pic> picList = new ArrayList<Pic>();

    private Patient patient;
    private PatientTreatInfo treatInfo;

    private CaseReportNet caseReportNet;
    private CaseReport caseReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patient = (Patient) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_PATIENT);
        treatInfo = (PatientTreatInfo) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_TREAT_INFO);
        if(patient == null || treatInfo == null){
            finish();
            return;
        }
        setContentView(R.layout.activity_out_patient_case_report);

        initViewOnBaseTitle(getString(R.string.patient_details));
        setBackListener(this);
        patientSimpleInfo = new PatientSimpleInfoViewHolder(this);
        InjectUtils.injectView(this);
        patientSimpleInfo.setPatient(patient);

        caseHistoryAdapter = new CaseHistoryAdapter(this,picList,null);
        gvPatientCaseHistory.setAdapter(caseHistoryAdapter);

        caseReportNet = new CaseReportNet(this);
        caseReportNet.getCaseRecordDetails(treatInfo.getId());
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if(url.endsWith(UrlConstants.GET_PATIENT_CASE_REPORT_DETAILS)){
            caseReport = (CaseReport) result;
            initViewData();
        }
    }

    private void initViewData() {
        if(caseReport!=null){
            tvTreatMentType.setText("门诊");
            tvTreatmentTime.setText(caseReport.getSeeDoctorTime());
            tvHospital.setText(caseReport.getHospitalName());
            tvDoctor.setText(caseReport.getDoctorName());
            if(caseReport.getPics()!=null&&caseReport.getPics().size()>0){
                picList.clear();
                picList.addAll(caseReport.getPics());
                caseHistoryAdapter.notifyDataSetChanged();
            }
        }
    }
}
