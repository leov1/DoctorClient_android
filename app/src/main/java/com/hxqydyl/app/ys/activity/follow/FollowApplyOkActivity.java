package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupSelectAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelectAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.CustomerNet;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by wangchao36 on 16/3/23.
 * 随访申请接受，并关联随访方案
 */
public class FollowApplyOkActivity extends BaseTitleActivity implements View.OnClickListener {

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
//        applyUuid = getIntent().getStringExtra("applyUuid");
        customerUuid = getIntent().getStringExtra("customerUuid");
        type = getIntent().getStringExtra("type");

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

    private void getMyVisitPreceptList() {
        FollowPlanNet.getMyVisitPreceptList(new FollowCallback(this) {
            @Override
            public void onFail(String status, String msg) {
                super.onFail(status, msg);
                UIHelper.ToastMessage(FollowApplyOkActivity.this, msg);
            }

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
                try {
                    List<PlanBaseInfo> tmp = PlanBaseInfo.parseList(result);
                    if (tmp.size() > 0) {
                        planList.clear();
                        planList.addAll(tmp);
                        planSelectAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    onFail("","解析出错啦，刷新下就好啦");
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

    private void applyUpdate() {
        if (planSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择方案");
            return;
        }
        showDialog("正在提交");
        PlanBaseInfo plan = planList.get(planSelectAdapter.getSelect());
        FollowApplyNet.updateVisitRecord(customerUuid, plan.getVisitUuid(), new FollowCallback(this){
            @Override
            public void onResponse(String response) {
                if (StringUtils.isEmpty(response)) {
                    UIHelper.ToastMessage(FollowApplyOkActivity.this, "没有数据");
                    return;
                }
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    String message = object.getString("message");
                    String value = object.getString("value");
                    UIHelper.ToastMessage(FollowApplyOkActivity.this, message);
                    if ("true".equals(value)) {
                        setResult(RESULT_OK);
                        finish();
                    }
                } catch (Exception e) {
                    onFail("999999", "解析出错啦，重新刷新下吧");
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                dismissDialog();
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "提交失败");
            }
        });
    }
    private void apply() {
        if (planSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择方案");
            return;
        }
        if (patientGroupSelectAdapter.getSelect() < 0) {
            UIHelper.ToastMessage(this, "请选择分组");
            return;
        }
        showDialog("正在提交");
        PlanBaseInfo plan = planList.get(planSelectAdapter.getSelect());
        FollowApplyNet.addVisitRecord(customerUuid, plan.getVisitUuid(), new FollowCallback(this){
//            @Override
//            public void onResult(String result) {
//                super.onResult(result);
//                updateCustomerGroup();
//            }

            @Override
            public void onResponse(String response) {
                JSONObject object = JSONObject.parseObject(response);
                int code = object.getInteger("code");
                if (code==200){
                    updateCustomerGroup();
                }else{
                    dismissDialog();
                    UIHelper.ToastMessage(FollowApplyOkActivity.this, "提交失败");
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                dismissDialog();
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "提交失败");
            }
        });
    }

    private void updateCustomerGroup() {
        final PatientGroup pg = patientGroupList.get(patientGroupSelectAdapter.getSelect());
        CustomerNet.updateCustomerGroup(pg.getId(), customerUuid, new FollowCallback(this){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                dismissDialog();
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "保存成功");
                finish();
            }
            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                dismissDialog();
                UIHelper.ToastMessage(FollowApplyOkActivity.this, "提交失败");
            }
        });
    }


}
