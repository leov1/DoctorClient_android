package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient.PatientSimpleInfoViewHolder;
import com.hxqydyl.app.ys.adapter.CaseHistoryAdapter;
import com.hxqydyl.app.ys.bean.CaseReport;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.bean.response.CaseReportResponse;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by white_ash on 2016/3/23.
 */
public class InPatientCaseReportActivity extends BaseRequstActivity implements View.OnClickListener {
    private PatientSimpleInfoViewHolder patientSimpleInfo;
    @InjectId(id = R.id.tvTreatMentType)
    private TextView tvTreatMentType;
    @InjectId(id = R.id.tvBeInHospitalTime)
    private TextView tvBeInHospitalTime;
    @InjectId(id = R.id.tvOutHospitalTime)
    private TextView tvOutHospitalTime;
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

    private CaseReport caseReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patient = (Patient) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_PATIENT);
        treatInfo = (PatientTreatInfo) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_TREAT_INFO);
        if (patient == null || treatInfo == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_in_patient_case_report);

        initViewOnBaseTitle(getString(R.string.patient_details));
        setBackListener(this);
        patientSimpleInfo = new PatientSimpleInfoViewHolder(this);
        InjectUtils.injectView(this);
        patientSimpleInfo.setPatient(patient);

        caseHistoryAdapter = new CaseHistoryAdapter(this, picList, null);
        gvPatientCaseHistory.setAdapter(caseHistoryAdapter);

        initData();
    }

    private void initData() {
        toNomalNet(toGetParams(toParamsBaen("medicalRecordUuid", treatInfo.getId())), CaseReportResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PATIENT_CASE_REPORT_DETAILS, "2.0"), "");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        caseReport = ((CaseReportResponse) bean).value;
        initViewData();
    }

    private void initViewData() {
        if (caseReport != null) {
            tvTreatMentType.setText("住院");
            tvBeInHospitalTime.setText(caseReport.getInHospitalTime());
            tvOutHospitalTime.setText(caseReport.getOutHospitalTime());
            tvHospital.setText(caseReport.getHospitalName());
            tvDoctor.setText(caseReport.getDoctorName());
            if (caseReport.getPics() != null && caseReport.getPics().size() > 0) {
                picList.clear();
                picList.addAll(caseReport.getPics());
                caseHistoryAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                this.finish();
                break;
        }

    }
}
