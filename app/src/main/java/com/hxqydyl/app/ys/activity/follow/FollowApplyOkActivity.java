package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupSelectAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelectAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/23.
 * 随访申请接受，并关联随访方案
 */
public class FollowApplyOkActivity extends BaseTitleActivity implements View.OnClickListener {

    private LinearLayout llAddPlan;
    private LinearLayout llAddGroup;
    private Button btnApply;
    private ListView lvPlan;
    private ListView lvGroup;

    private PlanSelectAdapter planSelectAdapter;
    private PatientGroupSelectAdapter patientGroupSelectAdapter;
    private PatientGroupNet patientGroupNet;

    private List<PlanBaseInfo> planList;
    private List<PatientGroup> patientGroupList;

    private String applyUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply_ok);
        initViewOnBaseTitle("选择随访方案");
        applyUuid = getIntent().getStringExtra("applyUuid");

        setBackListener();
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyVisitPreceptList();
        patientGroupNet.getPatientGroups(LoginManager.getDoctorUuid());
    }

    private void initView() {
        llAddPlan = (LinearLayout) findViewById(R.id.llAddPlan);
        llAddGroup = (LinearLayout) findViewById(R.id.llAddGroup);
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
    }

    private void initEvent() {
        llAddPlan.setOnClickListener(this);
        llAddGroup.setOnClickListener(this);
        btnApply.setOnClickListener(this);
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
                apply();
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

    private void getMyVisitPreceptList() {
        FollowPlanNet.getMyVisitPreceptList(new FollowCallback(){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                if (FollowApplyNet.myDev)
                    result = "[" +
                        "{" +
                        "\"visitUuid\": \"0000\"," +
                        "\"preceptName\": \"推荐你就选我呗\"," +
                        "\"doctorUuid\": \"4c61df50ebb34b7bac8339f605f2c218\"," +
                        "\"num\": \"1\"" +
                        "}" +
                        "]";
                List<PlanBaseInfo> tmp = PlanBaseInfo.parseList(result);
                if (tmp.size() > 0) {
                    planList.clear();
                    planList.addAll(tmp);
                    planSelectAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.GET_ALL_PATIENT_GROUP)) {
            List<PatientGroup> pgList = (ArrayList<PatientGroup>) result;
            patientGroupList.clear();
            patientGroupList.addAll(pgList);
            patientGroupSelectAdapter.notifyDataSetChanged();
        }
    }

    private void apply() {
        showDialog("正在提交");
        FollowApplyNet.addVisitRecord(applyUuid, applyUuid, new FollowCallback(){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                dismissDialog();
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "保存成功");
                finish();
            }
        });
    }
}
