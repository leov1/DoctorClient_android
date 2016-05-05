package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupSelectAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelectAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.BaseStringResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.bean.response.PlanListResponse;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.xus.http.httplib.model.PostPrams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 * 随访申请接受，并关联随访方案
 */
public class FollowApplyOkActivity extends BaseRequstActivity implements View.OnClickListener {

    private LinearLayout llAddPlan;
    private LinearLayout llAddGroup;
    private LinearLayout llGroupArea;
    private Button btnApply;
    private ListView lvPlan;
    private ListView lvGroup;

    private PlanSelectAdapter planSelectAdapter;
    private PatientGroupSelectAdapter patientGroupSelectAdapter;
    private PatientGroupNet patientGroupNet;

    private List<PlanBaseInfo> planList;
    private List<PatientGroup> patientGroupList;

    private String applyUuid;
    private String customerUuid;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply_ok);
        initViewOnBaseTitle("选择随访方案");
        applyUuid = getIntent().getStringExtra("applyUuid");
        customerUuid = getIntent().getStringExtra("customerUuid");
        type = getIntent().getStringExtra("type");
        setBackListener();
        initView();
        initEvent();
        getGroupList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyVisitPreceptList();
    }

    private void initView() {
        llAddPlan = (LinearLayout) findViewById(R.id.llAddPlan);
        llAddGroup = (LinearLayout) findViewById(R.id.llAddGroup);
        llGroupArea = (LinearLayout) findViewById(R.id.llGroupArea);
        lvPlan = (ListView) findViewById(R.id.lvPlan);
        lvGroup = (ListView) findViewById(R.id.lvGroup);
        btnApply = (Button) findViewById(R.id.btnApply);
        planList = new ArrayList<>();
        patientGroupList = new ArrayList<>();
        planSelectAdapter = new PlanSelectAdapter(this, planList);
        lvPlan.setAdapter(planSelectAdapter);
        patientGroupSelectAdapter = new PatientGroupSelectAdapter(this, patientGroupList);
        lvGroup.setAdapter(patientGroupSelectAdapter);
        patientGroupNet = new PatientGroupNet(this);
        if ("updateVisitRecord".equals(type)) {
            llGroupArea.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        llAddPlan.setOnClickListener(this);
        llAddGroup.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        lvPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                planSelectAdapter.setSelect(position);
            }
        });
        lvGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientGroupSelectAdapter.setSelect(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddGroup:
                addGroupDialog();
                break;
            case R.id.llAddPlan:
                Intent planIntent = new Intent(this, PlanEditActivity.class);
                startActivityForResult(planIntent, 0);
                break;
            case R.id.btnApply:
                if ("updateVisitRecord".equals(type)) {
                    applyUpdate();
                } else {
                    apply();
                }
                break;
        }
    }

    private void addGroupDialog() {
        DialogUtils.showAddPatientGroupDialog(this, new DialogUtils.SavePatientGroupListener() {
            @Override
            public void onSaveGroup(String groupName) {
                PatientGroup pg = new PatientGroup();
                pg.setGroupName(groupName);
                patientGroupList.add(pg);
                patientGroupSelectAdapter.notifyDataSetChanged();
            }
        });
    }
    //更新关联
    private void applyUpdate() {
        if (planSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择方案");
            return;
        }
        PlanBaseInfo plan = planList.get(planSelectAdapter.getSelect());

        String url="http://172.168.1.53/app/pub/doctor/1.0/updateVisitRecord";
        toNomalNet(toPostParams(toParamsBaen("customerUuid", customerUuid), toParamsBaen("visitPreceptUuid", plan.getVisitUuid()), toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())),
                BaseResponse.class, 5,url, "正更新关联信息");
//        toNomalNet(toPostParams(toParamsBaen("customerUuid", customerUuid), toParamsBaen("visitPreceptUuid", plan.getVisitUuid()), toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())),
//                BaseResponse.class, 5, UrlConstants.getWholeApiUrl(UrlConstants.UPDATE_VISIT_RECORD, "1.0"), "正更新关联信息");
    }

    //获取群组
    public void getGroupList() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 4, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_GROUP, "1.0"), "正在获取群组列表");

    }

    //获取方案列表
    private void getMyVisitPreceptList() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PlanListResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_MYVISIT_PRECEPTLIST, "1.0"), "正在获取方案列表");
    }

    //第一次接受并关联
    private void apply() {
        if (planSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择方案");
            return;
        }
        if (patientGroupSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择分组");
            return;
        }
        PlanBaseInfo plan = planList.get(planSelectAdapter.getSelect());
        String url="http://172.168.1.53/app/pub/doctor/2.0/addVisitRecord";
        toNomalNet(toGetParams(toParamsBaen("visitUuid", applyUuid), toParamsBaen("visitPreceptUuid", plan.getVisitUuid())), BaseStringResponse.class, 1, url, "正在接受 ..");

//        toNomalNet(toGetParams(toParamsBaen("visitUuid", applyUuid), toParamsBaen("visitPreceptUuid", plan.getVisitUuid())), BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.ADD_VISIT_RECORD, "1.0"), "正在接受 ..");

    }

    //将患者更新至分组
    private void updateCustomerGroup() {
        final PatientGroup pg = patientGroupList.get(patientGroupSelectAdapter.getSelect());
        PostPrams postPrams = toPostParams(toParamsBaen("groupId",  pg.getGroupId()), toParamsBaen("customerUuid", customerUuid), toParamsBaen("doctorUuid",  LoginManager.getDoctorUuid()));
        toNomalNet(postPrams, BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.MOVE_PATIENT_TO_OTHER_GROUP, "1.0"), "正在移动患者");
//        Map<String,String> head=new HashMap<>();
//        head.put("accept", "application/json");
//        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("groupId", pg.getGroupId()), toParamsBaen("customerUuid", customerUuid)), BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.UPDATE_CUSTOMER_GROUP, "1.0"), "正在关联 ..");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                updateCustomerGroup();
                break;
            case 2:
            case 5:
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "保存成功");
                finish();
                break;
            case 3:
                PlanListResponse plr = (PlanListResponse) bean;
                List<PlanBaseInfo> tmp = plr.getRelist();
                if (tmp != null && tmp.size() > 0) {
                    planList.clear();
                    planList.addAll(tmp);
                    planSelectAdapter.notifyDataSetChanged();
                }
                break;
            case 4:
                PatientGroupResponse pgr = (PatientGroupResponse) bean;
                if (pgr.getRelist() != null && pgr.getRelist().size() > 0) {
                    patientGroupList.clear();
                    patientGroupList.addAll(pgr.getRelist());
                    patientGroupSelectAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


}
