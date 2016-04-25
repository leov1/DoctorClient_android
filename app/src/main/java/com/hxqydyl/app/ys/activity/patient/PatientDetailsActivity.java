package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
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
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shs.cn on 2016/3/19.
 */
public class PatientDetailsActivity extends BaseRequstActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
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
    private Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        init();
        initView();
        initListener();
        refreshTreatInfoList();
    }

    //初始化数据源
    public void init() {
        if (getIntent().hasExtra(KEY_PATIENT))
            patient = (Patient) getIntent().getSerializableExtra(KEY_PATIENT);
        if (patient == null) {
            finish();
            return;
        }
    }

    //初始化监听
    public void initListener() {
        lvPatientTreatInfo.setOnItemClickListener(this);
        simpleInfoViewHolder.wholeView.setOnClickListener(this);
        bAddCaseReport.setOnClickListener(this);
        bSelectNewFollowUpForPatient.setOnClickListener(this);
        bCommunicateWithPatient.setOnClickListener(this);
        setBackListener(this);
    }

    //初始化控件
    public void initView() {
        lvPatientTreatInfo = (ListView) findViewById(R.id.lvPatientTreatInfo);
        bCommunicateWithPatient = (Button) findViewById(R.id.bCommunicateWithPatient);
        bAddCaseReport = (Button) findViewById(R.id.bAddCaseReport);
        bSelectNewFollowUpForPatient = (Button) findViewById(R.id.bSelectNewFollowUpForPatient);
        right_txt_btn = (TextView) findViewById(R.id.right_txt_btn);
        patientTreatInfoAdapter = new PatientTreatInfoAdapter(this, patientTreatInfoArrayList);
        lvPatientTreatInfo.setAdapter(patientTreatInfoAdapter);
        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        simpleInfoViewHolder.setPatient(patient);
        if (!TextUtils.isEmpty(patient.getCustomerName())) {
            initViewTitle(patient.getCustomerName());
        } else {
            initViewTitle("患者详情");
        }
    }


    private void refreshTreatInfoList() {
        toNomalNetStringBack(toGetParams(toParamsBaen("customerUuid", patient.getCustomerUuid()), toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PATIENT_TREAT_RECORD, "1.0"), "正在获取患者病例");
    }

    @Override
    public void onSuccessToString(String json, int flag) {
        Type listType = new TypeToken<ArrayList<PatientTreatInfo>>() {
        }.getType();
        ArrayList<PatientTreatInfo> list = gson.fromJson(json, listType);
        if (list != null && list.size() > 0) {
            patientTreatInfoArrayList.clear();
            patientTreatInfoArrayList.addAll(list);
            patientTreatInfoAdapter.notifyDataSetChanged();
        }
    }

    private void initViewTitle(String title) {
        initViewOnBaseTitle(title);
        //TODO 此处弹出删除操作
//        right_txt_btn.setBackgroundResource(R.mipmap.dot_dot_dot);
//        right_txt_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.bCommunicateWithPatient:
                CommentWebActivity.toCommentWeb(UrlConstants.getWholeApiUrl(UrlConstants.COMMUNICATE_WITH_PATIENT_H5, patient.getCustomerUuid()), "", this, true);
                break;
            case R.id.bAddCaseReport:
                intent = new Intent(this, AddCaseReportActivity.class);
                intent.putExtra(KEY_PATIENT, patient);
                startActivityForResult(intent, REQ_ADD_CASE_REPORT);
                break;
            case R.id.bSelectNewFollowUpForPatient:
                intent = new Intent(this, FollowApplyOkActivity.class);
                intent.putExtra("customerUuid", patient.getCustomerUuid());
                startActivity(intent);
                break;
            case R.id.llPatientSimpleInfo://患者详情
                intent = new Intent(PatientDetailsActivity.this, PatientInfoActivity.class);
                intent.putExtra("patientId", patient.getCustomerUuid());
                intent.putExtra("type", "updateVisitRecord");
                startActivityForResult(intent, REQ_ADD_FOLLOW_PLAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ADD_CASE_REPORT && resultCode == RESULT_OK) {
            refreshTreatInfoList();

        }else if(requestCode == REQ_ADD_CASE_REPORT && resultCode == RESULT_OK){
            refreshTreatInfoList();
        }
    }

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
                intent.putExtra(KEY_PATIENT, patient);
                startActivity(intent);
            }
        }
    }
}
