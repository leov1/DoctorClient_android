package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.activity.patient.PatientAddActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient_group.PatientGroupManageActivity;
import com.hxqydyl.app.ys.activity.patient_group.PatientGroupSelectActivity;
import com.hxqydyl.app.ys.adapter.PatientListAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.PatientNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.fullshowview.FullShowExpandableListView;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;

/**
 * 随访主页
 */
public class FollowMainActivity extends BaseTitleActivity implements View.OnClickListener ,DialogUtils.DeleteListener{

    private static final int REQ_MANAGE_PATIENT_GROUP = 1 ;
    private static final int REQ_MOVE_TO_OTHER_GROUP = 2;
    private ImageView addBtn;
    private RelativeLayout applyBtn;
    private RelativeLayout mgrBtn;
    private ImageView ivManagePatientGroup;
    private RelativeLayout noticeBtn;
    private RelativeLayout articeBtn;
    private RelativeLayout followTaskBtn;
    private FullShowExpandableListView elvPatientList;
    private int lastExpandGroupPosition = -1;
    private ArrayList<PatientGroup> patientGroups = new ArrayList<PatientGroup>();
    private PatientListAdapter patientListAdapter;

    private PatientGroupNet patientGroupNet;
    private PatientNet patientNet;

    private Patient curOperatePatient;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow);
//        initListData();
        initViews();
        patientListAdapter = new PatientListAdapter(this, patientGroups);
        elvPatientList.setGroupIndicator(null);
        elvPatientList.setAdapter(patientListAdapter);
        initListeners();

        patientNet = new PatientNet(this);
        patientGroupNet = new PatientGroupNet(this);
        patientGroupNet.getAllPatientGroupAndPatient(LoginManager.getDoctorUuid());
    }

    private PatientListAdapter.OnChildClickListener onChildClickListener = new PatientListAdapter.OnChildClickListener() {
        @Override
        public void onChildClick(int groupPosition, int childPosition) {
            Intent intent = new Intent(FollowMainActivity.this, PatientDetailsActivity.class);
            Patient patient = (Patient) patientListAdapter.getChild(groupPosition, childPosition);
            intent.putExtra(PatientDetailsActivity.KEY_PATIENT, patient);
            startActivity(intent);
        }

        @Override
        public void onMenuClick(int groupPosition, int childPosition, int menu) {
            curOperatePatient = (Patient) patientListAdapter.getChild(groupPosition, childPosition);
            switch(menu){
                case PatientListAdapter.MOVE:
                    Intent intent = new Intent(FollowMainActivity.this, PatientGroupSelectActivity.class);
                    intent.putExtra(PatientGroupManageActivity.GROUPS_INFO_KEY,patientGroups);
                    startActivityForResult(intent,REQ_MOVE_TO_OTHER_GROUP);
                    break;
                case PatientListAdapter.DELETE:
                    PatientGroup patientGroup = (PatientGroup) patientListAdapter.getGroup(groupPosition);
                    groupId = patientGroup.getId();
                    DialogUtils.showDeleteDialog(FollowMainActivity.this,FollowMainActivity.this,String.format(getString(R.string.confirm_delete_this_patient),curOperatePatient.getName()));
                    break;
            }


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
        ivManagePatientGroup = (ImageView) findViewById(R.id.ivManagePatientGroup);
        noticeBtn = (RelativeLayout) findViewById(R.id.rl_notice);
        articeBtn = (RelativeLayout) findViewById(R.id.rl_articl);
        followTaskBtn = (RelativeLayout) findViewById(R.id.rl_follow_task);
        elvPatientList = (FullShowExpandableListView) findViewById(R.id.elvPatientList);
    }

    private void initListeners() {
        setBackListener(this);
        addBtn.setOnClickListener(this);
        applyBtn.setOnClickListener(this);
        mgrBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        articeBtn.setOnClickListener(this);
        ivManagePatientGroup.setOnClickListener(this);
        followTaskBtn.setOnClickListener(this);
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
                startActivityForResult(patientAddIntent, REQ_MANAGE_PATIENT_GROUP);
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
                CommentWebActivity.toCommentWeb(UrlConstants.getWholeApiUrl(UrlConstants.PATIENT_EDUCATION), null, FollowMainActivity.this, false);
                break;
            case R.id.ivManagePatientGroup:
                Intent groupManageIntent = new Intent(FollowMainActivity.this,PatientGroupManageActivity.class);
                startActivityForResult(groupManageIntent, REQ_MANAGE_PATIENT_GROUP);
                break;
            case R.id.rl_follow_task:
                Intent taskIntent = new Intent(this, FollowTaskActivity.class);
                startActivity(taskIntent);
                break;
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if(url.endsWith(UrlConstants.GET_ALL_PATIENT_AND_GROUP_INFO)) {
            patientGroups.clear();
            patientGroups.addAll((ArrayList<PatientGroup>) result);
            patientListAdapter.notifyDataSetChanged();
        }else if(url.endsWith(UrlConstants.DELETE_PATIENT)){
            patientGroupNet.getAllPatientGroupAndPatient(LoginManager.getDoctorUuid());
        }else if(url.endsWith(UrlConstants.MOVE_PATIENT_TO_OTHER_GROUP)){
            patientGroupNet.getAllPatientGroupAndPatient(LoginManager.getDoctorUuid());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_MANAGE_PATIENT_GROUP && resultCode == RESULT_OK){
            patientGroupNet.getAllPatientGroupAndPatient(LoginManager.getDoctorUuid());
        }else if(requestCode == REQ_MOVE_TO_OTHER_GROUP && resultCode == RESULT_OK){
            PatientGroup group = (PatientGroup) data.getSerializableExtra(PatientGroupManageActivity.GROUPS_INFO_KEY);
            if(group != null && curOperatePatient!=null){
                patientGroupNet.movePatientToOtherGroup(group.getId(),curOperatePatient.getId(),LoginManager.getDoctorUuid());
            }
        }
    }

    @Override
    public void onConfirmDelete() {
        patientNet.deletePatient(curOperatePatient.getId(),groupId);
    }

    @Override
    public void onCancelDelete() {

    }
}
