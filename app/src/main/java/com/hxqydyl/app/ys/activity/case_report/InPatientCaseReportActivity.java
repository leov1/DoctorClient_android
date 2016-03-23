package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientSimpleInfoViewHolder;
import com.hxqydyl.app.ys.adapter.CaseHistoryAdapter;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/23.
 */
public class InPatientCaseReportActivity extends BaseTitleActivity implements View.OnClickListener{
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_patient_case_report);

        initViewOnBaseTitle(getString(R.string.patient_details));
        setBackListener(this);
        patientSimpleInfo = new PatientSimpleInfoViewHolder(this);
        InjectUtils.injectView(this);

        picList.add(new Pic());
        picList.add(new Pic());
        picList.add(new Pic());
        picList.add(new Pic());
        picList.add(new Pic());
        caseHistoryAdapter = new CaseHistoryAdapter(this,picList);
        gvPatientCaseHistory.setAdapter(caseHistoryAdapter);
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
