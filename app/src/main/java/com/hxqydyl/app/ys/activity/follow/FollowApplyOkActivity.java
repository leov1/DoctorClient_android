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
import com.hxqydyl.app.ys.bean.plan.Plan;

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

    private List<Plan> planList;
    private List<PatientGroup> patientGroupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply_ok);
        initViewOnBaseTitle("选择随访方案");
        setBackListener();
        initView();
        initEvent();
    }

    private void initView() {
        llAddPlan = (LinearLayout) findViewById(R.id.llAddPlan);
        llAddGroup = (LinearLayout) findViewById(R.id.llAddGroup);
        lvPlan = (ListView) findViewById(R.id.lvPlan);
        lvGroup = (ListView) findViewById(R.id.lvGroup);
        btnApply = (Button) findViewById(R.id.btnApply);

        planList = new ArrayList<>();
        planList.add(new Plan("方案1"));
        planList.add(new Plan("方案2"));
        planList.add(new Plan("方案3"));
        planList.add(new Plan("方案4"));
        patientGroupList = new ArrayList<>();
        patientGroupList.add(new PatientGroup("组1"));
        patientGroupList.add(new PatientGroup("组2"));
        patientGroupList.add(new PatientGroup("组3"));

        planSelectAdapter = new PlanSelectAdapter(this, planList);
        lvPlan.setAdapter(planSelectAdapter);
        patientGroupSelectAdapter = new PatientGroupSelectAdapter(this, patientGroupList);
        lvGroup.setAdapter(patientGroupSelectAdapter);
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
                Toast.makeText(this, "点击确定了", Toast.LENGTH_SHORT);
                break;
        }
    }

    private void addGroupDialog() {

    }
}
