package com.hxqydyl.app.ys.activity.case_report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.adapter.IllnessChangeRecordAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/6.
 */
public class IllnessChangeRecordActivity extends BaseTitleActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView lvIllnessChangeRecord;
    private IllnessChangeRecordAdapter illnessChangeAdapter;
    private ArrayList<IllnessChangeRecord> changeRecords = new ArrayList<IllnessChangeRecord>();
    private CaseReportNet caseReportNet;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_illness_change_record);
        initView();

        caseReportNet = new CaseReportNet(this);
        caseReportNet.getIllnessChangeRecordHistory(LoginManager.getDoctorUuid(), patient.getCustomerUuid());
    }

    private void init() {
        patient = (Patient) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_PATIENT);
        if (patient == null) {
            finish();
            return;
        }
    }

    private void initView() {
        initViewOnBaseTitle(getString(R.string.illness_change));
        lvIllnessChangeRecord = (ListView) findViewById(R.id.lvIllnessChangeRecord);
        illnessChangeAdapter = new IllnessChangeRecordAdapter(this, changeRecords);
        lvIllnessChangeRecord.setAdapter(illnessChangeAdapter);
    }

    private void initListener() {
        setBackListener(this);
        lvIllnessChangeRecord.setOnItemClickListener(this);
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        changeRecords.clear();
        changeRecords.addAll((ArrayList<IllnessChangeRecord>) result);
        illnessChangeAdapter.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IllnessChangeRecord record = (IllnessChangeRecord) parent.getItemAtPosition(position);
        if (record != null) {
            Intent intent = new Intent(IllnessChangeRecordActivity.this, IllnessChangeDetalsActivity.class);
            intent.putExtra(IllnessChangeDetalsActivity.KEY_ILLNESS_CHANGE_RECORD, record);
            startActivity(intent);
        }
    }
}
