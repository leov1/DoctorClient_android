package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.activity.patient.PatientAddActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient_group.PatientGroupManageActivity;
import com.hxqydyl.app.ys.activity.patient_group.PatientGroupSelectActivity;
import com.hxqydyl.app.ys.adapter.PatientListAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.fullshowview.FullShowExpandableListView;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.xus.http.httplib.model.PostPrams;

import java.util.ArrayList;
import java.util.Map;

/**
 * 随访主页
 */
public class FollowMainActivity extends BaseRequstActivity implements View.OnClickListener, DialogUtils.DeleteListener, PatientListAdapter.OnChildClickListener,ExpandableListView.OnGroupExpandListener {

    private static final int REQ_MANAGE_PATIENT_GROUP = 1;
    private static final int REQ_MOVE_TO_OTHER_GROUP = 2;
    private ImageView addBtn;//添加患者
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

    private Intent intent;
    private Patient curOperatePatient;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        initViews();
        patientListAdapter = new PatientListAdapter(this, patientGroups);
        elvPatientList.setGroupIndicator(null);
        elvPatientList.setAdapter(patientListAdapter);
        initListeners();
        getPatientGroup();

    }
//    18543228901
//    hao123
    //获取患者列表
    public void getPatientGroup() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_AND_GROUP_INFO, "1.0"), "正在获取患者列表");
    }

    //移动患者到其他组
    public void movePatientToOtherGroup(String groupId, String customerUuid, String doctorUuid) {
        PostPrams postPrams = toPostParams(toParamsBaen("groupId", groupId), toParamsBaen("customerUuid", customerUuid), toParamsBaen("doctorUuid", doctorUuid));
        toNomalNet(postPrams, BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.MOVE_PATIENT_TO_OTHER_GROUP, "1.0"), "正在移动患者");
    }

    //删除患者
    public void deletePatient(String groupId, String customerUuid) {
        PostPrams postPrams = toPostParams(toParamsBaen("groupId", groupId), toParamsBaen("customerUuid", customerUuid),toParamsBaen("doctorUuid",LoginManager.getDoctorUuid()));
        toNomalNet(postPrams, BaseResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.DELETE_PATIENT, "1.0"), "正在删除患者");
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
        elvPatientList.setOnGroupExpandListener(this);
        patientListAdapter.setOnClildClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.right_img:    //添加患者
                intent = new Intent(FollowMainActivity.this, PatientAddActivity.class);
                startActivityForResult(intent, REQ_MANAGE_PATIENT_GROUP);
                break;
            case R.id.rl_apply://随访申请
                intent = new Intent(FollowMainActivity.this, FollowApplyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mgr://随访方案管理
                intent = new Intent(FollowMainActivity.this, PlanMgrActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_notice://群发通知
                intent = new Intent(FollowMainActivity.this, MassActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_articl://患教库
                CommentWebActivity.toCommentWeb(UrlConstants.getWholeApiUrl(UrlConstants.PATIENT_EDUCATION), null, FollowMainActivity.this, false);
                break;
            case R.id.ivManagePatientGroup://分组管理
                intent = new Intent(FollowMainActivity.this, PatientGroupManageActivity.class);
                intent.putExtra(PatientGroupManageActivity.GROUPS_INFO_KEY, patientGroups);
                startActivityForResult(intent, REQ_MANAGE_PATIENT_GROUP);
                break;
            case R.id.rl_follow_task:
                intent = new Intent(this, FollowTaskActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQ_MANAGE_PATIENT_GROUP:
                getPatientGroup();
                break;
            case REQ_MOVE_TO_OTHER_GROUP:
                if (data != null && data.hasExtra(PatientGroupManageActivity.GROUPS_INFO_KEY)){
                    PatientGroup group = (PatientGroup) data.getSerializableExtra(PatientGroupManageActivity.GROUPS_INFO_KEY);
                    if (group != null && curOperatePatient != null) {
                        movePatientToOtherGroup(group.getGroupId(), curOperatePatient.getCustomerUuid(), LoginManager.getDoctorUuid());
                    }
                }else {
                    getPatientGroup();
                }
                break;
        }
    }

    @Override
    public void onConfirmDelete() {
        deletePatient(groupId,curOperatePatient.getCustomerUuid() );
    }

    @Override
    public void onCancelDelete() {

    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1://获取患者列表返回
                PatientGroupResponse p = (PatientGroupResponse) bean;
                if (p.getRelist() != null && p.getRelist().size() > 0) {
                    patientGroups.clear();
                    patientGroups.addAll(p.getRelist());
                    patientListAdapter.notifyDataSetChanged();
                }
                break;
            case 2://移动患者分组返回
            case 3://删除患者
                getPatientGroup();
                break;
        }
    }

//点击患者
    @Override
    public void onChildClick(int groupPosition, int childPosition) {
        Intent intent = new Intent(FollowMainActivity.this, PatientDetailsActivity.class);
        Patient patient = (Patient) patientListAdapter.getChild(groupPosition, childPosition);
        intent.putExtra(PatientDetailsActivity.KEY_PATIENT, patient);
        startActivity(intent);
    }

    //患者列表菜单
    @Override
    public void onMenuClick(int groupPosition, int childPosition, int menu) {
        curOperatePatient = (Patient) patientListAdapter.getChild(groupPosition, childPosition);
        switch (menu) {
            case PatientListAdapter.MOVE:
                Intent intent = new Intent(FollowMainActivity.this, PatientGroupSelectActivity.class);
                intent.putExtra(PatientGroupManageActivity.GROUPS_INFO_KEY, patientGroups);
                startActivityForResult(intent, REQ_MOVE_TO_OTHER_GROUP);
                break;
            case PatientListAdapter.DELETE:
                PatientGroup patientGroup = (PatientGroup) patientListAdapter.getGroup(groupPosition);
                groupId = patientGroup.getGroupId();
                DialogUtils.showDeleteDialog(FollowMainActivity.this, FollowMainActivity.this, String.format(getString(R.string.confirm_delete_this_patient), curOperatePatient.getCustomerName()));
                break;
        }
    }

//分组展开
    @Override
    public void onGroupExpand(int groupPosition) {
        if (lastExpandGroupPosition >= 0 && lastExpandGroupPosition != groupPosition) {
            elvPatientList.collapseGroup(lastExpandGroupPosition);
        }
        lastExpandGroupPosition = groupPosition;
    }
}
