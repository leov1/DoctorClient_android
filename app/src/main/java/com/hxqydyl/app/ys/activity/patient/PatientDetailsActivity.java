package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.activity.case_report.AddCaseReportActivity;
import com.hxqydyl.app.ys.activity.case_report.FollowUpFormActivity;
import com.hxqydyl.app.ys.activity.case_report.InPatientCaseReportActivity;
import com.hxqydyl.app.ys.activity.case_report.OutPatientCaseReportActivity;
import com.hxqydyl.app.ys.activity.follow.FollowApplyOkActivity;
import com.hxqydyl.app.ys.adapter.PatientTreatInfoAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;

/**
 * Created by shs.cn on 2016/3/19.
 */
public class PatientDetailsActivity extends BaseTitleActivity implements View.OnClickListener {
    public static final String KEY_PATIENT = "patient";
    public static final String KEY_TREAT_INFO = "treat_info";
    private static final int REQ_ADD_CASE_REPORT = 1;
    private static final int REQ_ADD_FOLLOW_PLAN = 2;       //随访方案
    //    患者基本信息
    private PatientSimpleInfoViewHolder simpleInfoViewHolder;
    //    给患者添加治疗信息
    private Button bCommunicateWithPatient;
    private Button bAddCaseReport;
    private Button bSelectNewFollowUpForPatient;
    private TextView right_txt_btn;

    //    患者治疗信息
    private ListView lvPatientTreatInfo;
    private PatientTreatInfoAdapter patientTreatInfoAdapter;
    private ArrayList<PatientTreatInfo> patientTreatInfoArrayList = new ArrayList<PatientTreatInfo>();
    private CaseReportNet caseReportNet;
    private Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        patient = (Patient) getIntent().getSerializableExtra(KEY_PATIENT);
        if(patient ==null){
            finish();
            return;
        }
        if (!TextUtils.isEmpty(patient.getRealName())) {
            initViewTitle(patient.getRealName());
        }else{
            initViewTitle("患者详情");
        }
        setBackListener(this);

        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        simpleInfoViewHolder.setPatient(patient);
        simpleInfoViewHolder.wholeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetailsActivity.this, PatientInfoActivity.class);
                intent.putExtra("patientId",patient.getCustomerUuid());
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
        bSelectNewFollowUpForPatient.setOnClickListener(this);
        bCommunicateWithPatient.setOnClickListener(this);

        refreshTreatInfoList();
    }
public void initView(){
    lvPatientTreatInfo=(ListView)findViewById(R.id.lvPatientTreatInfo);
    bCommunicateWithPatient=(Button)findViewById(R.id.bCommunicateWithPatient);
    bAddCaseReport=(Button)findViewById(R.id.bAddCaseReport);
    bSelectNewFollowUpForPatient=(Button)findViewById(R.id.bSelectNewFollowUpForPatient);
    right_txt_btn=(TextView)findViewById(R.id.right_txt_btn);
}





    private void refreshTreatInfoList(){
        if(caseReportNet == null){
            caseReportNet = new CaseReportNet(this);
        }
        caseReportNet.getAllTreatInfoRecordOfPatient(patient.getCustomerUuid(), LoginManager.getDoctorUuid());
    }

    private void initViewTitle(String title) {
        initViewOnBaseTitle(title);
//        right_txt_btn.setBackgroundResource(R.mipmap.dot_dot_dot);
//        right_txt_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                PopupWindow pop = new PopupWindow();
//                View view = View.inflate(PatientDetailsActivity.this,R.layout.delete_patient_button,null);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                pop.setContentView(view);
//                pop.showAsDropDown();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.bCommunicateWithPatient:
                CommentWebActivity.toCommentWeb(UrlConstants.getWholeApiUrl(UrlConstants.COMMUNICATE_WITH_PATIENT_H5,patient.getCustomerUuid()),"",this,true);
                break;
            case R.id.bAddCaseReport:
                intent = new Intent(this, AddCaseReportActivity.class);
                intent.putExtra(KEY_PATIENT,patient);
                startActivityForResult(intent,REQ_ADD_CASE_REPORT);
                break;
            case R.id.bSelectNewFollowUpForPatient:
                intent = new Intent(this, FollowApplyOkActivity.class);
                intent.putExtra("customerUuid", patient.getCustomerUuid());
                startActivity(intent);
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_ADD_CASE_REPORT && resultCode == RESULT_OK){
            refreshTreatInfoList();

        }
    }
}
