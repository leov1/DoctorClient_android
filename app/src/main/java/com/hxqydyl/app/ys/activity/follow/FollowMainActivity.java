package com.hxqydyl.app.ys.activity.follow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientAddActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient_group.PatientGroupManageActivity;
import com.hxqydyl.app.ys.adapter.PatientListAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.ui.fullshowexpandablelistview.FullShowExpandableListView;

import java.util.ArrayList;

/**
 * 随访主页
 */
public class FollowMainActivity extends BaseTitleActivity implements View.OnClickListener {

    private ImageView addBtn;
    private RelativeLayout applyBtn;
    private RelativeLayout mgrBtn;
    private RelativeLayout patientBtn;
    private RelativeLayout noticeBtn;
    private RelativeLayout articeBtn;
    private FullShowExpandableListView elvPatientList;
    private int lastExpandGroupPosition = -1;
    private ArrayList<PatientGroup> patientGroups = new ArrayList<PatientGroup>();
    private PatientListAdapter patientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow);
        initListData();
        initViews();
        patientListAdapter = new PatientListAdapter(this, patientGroups);
        elvPatientList.setGroupIndicator(null);
        elvPatientList.setAdapter(patientListAdapter);
        initListeners();
    }

    private PatientListAdapter.OnChildClickListener onChildClickListener = new PatientListAdapter.OnChildClickListener() {
        @Override
        public void onChildClick(int groupPosition, int childPosition) {
            Intent intent = new Intent(FollowMainActivity.this, PatientDetailsActivity.class);
            Patient patient = (Patient) patientListAdapter.getChild(groupPosition, childPosition);
            intent.putExtra("patient", patient);
            startActivity(intent);
        }
    };

    private void initListData() {
        for (int i = 0; i < 5; i++) {
            PatientGroup patientGroup = new PatientGroup();
            patientGroup.setId(i + "");
            patientGroup.setGroupName("group " + i);
            for (int j = 0; j < i; j++) {
                Patient patient = new Patient();
                patient.setId("" + i + j);
                patient.setName("组" + i + "病人" + j);
                patient.setAge("年龄：" + j + "岁");
                patient.setSex("女");
                patient.setFollowTime("2016-03-21");
                patient.setDescription("问题：天天吃不下饭，没有精神，头晕目眩。当看到美女时，两眼会放光，其他时间都无精打采");
                patientGroup.addPatient(patient);
            }
            patientGroups.add(patientGroup);
        }
    }

    private void initViews() {
        initViewOnBaseTitle("随访");
        addBtn = (ImageView) findViewById(R.id.right_img);
        addBtn.setVisibility(View.VISIBLE);
        addBtn.setImageDrawable(getResources().getDrawable(R.mipmap.btn_add_white));

        applyBtn = (RelativeLayout) findViewById(R.id.rl_apply);
        mgrBtn = (RelativeLayout) findViewById(R.id.rl_mgr);
        patientBtn = (RelativeLayout) findViewById(R.id.rl_patient);
        noticeBtn = (RelativeLayout) findViewById(R.id.rl_notice);
        articeBtn = (RelativeLayout) findViewById(R.id.rl_articl);
        elvPatientList = (FullShowExpandableListView) findViewById(R.id.elvPatientList);

    }

    private void initListeners() {
        setBackListener(this);
        addBtn.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
        mgrBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        articeBtn.setOnClickListener(this);
        patientBtn.setOnClickListener(this);
        elvPatientList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandGroupPosition >= 0 && lastExpandGroupPosition != groupPosition) {
                    elvPatientList.collapseGroup(lastExpandGroupPosition);
                }
                lastExpandGroupPosition = groupPosition;
            }
        });
        patientListAdapter.setOnClildClickListener(onChildClickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.right_img:    //添加患者
                Intent patientAddIntent = new Intent(FollowMainActivity.this, PatientAddActivity.class);
                startActivity(patientAddIntent);
                break;
            case R.id.rl_apply://随访申请
                Intent applyIntent = new Intent(FollowMainActivity.this, FollowApplyActivity.class);
                startActivity(applyIntent);
                break;
            case R.id.rl_mgr://随访方案管理
                Intent mgrIntent = new Intent(FollowMainActivity.this, PlanMgrActivity.class);
                startActivity(mgrIntent);
                break;
            case R.id.rl_notice://群发通知
                Intent noticeIntent = new Intent(FollowMainActivity.this, MassActivity.class);
                startActivity(noticeIntent);
                break;
            case R.id.rl_articl://患教库
                Intent articleIntent = new Intent(FollowMainActivity.this, ArticleActivity.class);
                startActivity(articleIntent);
                break;
            case R.id.rl_patient:
                Intent groupManageIntent = new Intent(FollowMainActivity.this,PatientGroupManageActivity.class);
                startActivity(groupManageIntent);
                break;
        }
    }
}
