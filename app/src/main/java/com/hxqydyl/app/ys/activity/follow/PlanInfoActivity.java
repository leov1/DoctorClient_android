package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsAdapter;
import com.hxqydyl.app.ys.adapter.MedicineInfoAdapter;
import com.hxqydyl.app.ys.adapter.PlanCheckSycleAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.bean.response.PlanResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenuExpandableListView;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * Created by wangchao36 on 16/3/22.
 * 编辑随访方案
 */
public class PlanInfoActivity extends BaseRequstActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvDrugTherapy; // 不良反应
    private TextView tvSideEffects; //其他治疗

    private TextView tvFollowCycle; //随访
    private TextView tvWeightCycle;  //体重
    private TextView tvEcgCycle;  //心电图
    private TextView tvBloodCycle;  //血
    private TextView tvLiverCycle;  //肝
    private TextView tvCustomerTest;//自评周期
    private TextView tvDoctorTest;//医评周期

    private View tvDoctorScaleLine;
    private View tvSelfScaleLine;
    private ListView lvMedicine;
    private MedicineInfoAdapter medicineAdapter;
    private List<ImportantAdviceChild> medicineList;        // 药品列表
    private SwipeMenuListView lvOtherSycle;
    private PlanCheckSycleAdapter planCheckSycleAdapter;
    private List<CheckSycle> checkSycleList;
    private ListView lvSelfScale;
    private ListView lvDoctorScale;
    private PlanSelfScaleAdapter selfScaleAdapter;
    private PlanSelfScaleAdapter doctorScaleAdapter;
    private List<Scale> selfScaleList;
    private List<Scale> doctorScaleList;
    private SwipeMenuExpandableListView elvHealthTips;
    private HealthTipsAdapter healthTipsAdapter;
    private List<HealthTips> healthTipsList;
    private TextView tvPatientList;
    private TextView tvEdit;
    private String visitUuid = null;    //随访方案uuid
    private String preceptName = null;
    private String from = null; // my\suggest
    private Plan plan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_info);
        Intent intent = getIntent();
        visitUuid = intent.getStringExtra("visitUuid");
        from = intent.getStringExtra("from");
        preceptName = intent.getStringExtra("preceptName");
        initViewOnBaseTitle(preceptName + "随访方案");
        setBackListener();
        initView();
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        visitPreceptDetail(visitUuid);
    }

    private void initView() {
        tvEdit = (TextView) findViewById(R.id.right_txt_btn);
        tvEdit.setText("编辑");
        tvEdit.setVisibility(View.VISIBLE);
        tvEdit.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDrugTherapy = (TextView) findViewById(R.id.tvDrugTherapy);
        tvSideEffects = (TextView) findViewById(R.id.tvSideEffects);
        tvPatientList = (TextView) findViewById(R.id.tvPatientList);
        tvFollowCycle = (TextView) findViewById(R.id.tvFollowCycle);
        tvWeightCycle = (TextView) findViewById(R.id.tvWeightCycle);
        tvEcgCycle = (TextView) findViewById(R.id.tvEcgCycle);
        tvBloodCycle = (TextView) findViewById(R.id.tvBloodCycle);
        tvLiverCycle = (TextView) findViewById(R.id.tvLiverCycle);
        tvCustomerTest = (TextView) findViewById(R.id.tvCustomerTest);
        tvDoctorTest = (TextView) findViewById(R.id.tvDoctorTest);
        tvDoctorScaleLine = findViewById(R.id.tvDoctorScaleLine);
        tvSelfScaleLine = findViewById(R.id.tvSelfScaleLine);

        lvMedicine = (ListView) findViewById(R.id.lvMedicine);
        lvSelfScale = (ListView) findViewById(R.id.lvSelfScale);
        lvDoctorScale = (ListView) findViewById(R.id.lvDoctorScale);

        lvOtherSycle = (SwipeMenuListView) findViewById(R.id.lvOtherSycle);
        checkSycleList = new ArrayList<>();
        planCheckSycleAdapter = new PlanCheckSycleAdapter(this, checkSycleList);
        lvOtherSycle.setAdapter(planCheckSycleAdapter);

        medicineList = new ArrayList<>();
        medicineList.add(new ImportantAdviceChild());
        medicineAdapter = new MedicineInfoAdapter(this, medicineList);
        lvMedicine.setAdapter(medicineAdapter);

        selfScaleList = new ArrayList<>();
        selfScaleAdapter = new PlanSelfScaleAdapter(this, selfScaleList, tvSelfScaleLine);
        lvSelfScale.setAdapter(selfScaleAdapter);
        doctorScaleList = new ArrayList<>();
        doctorScaleAdapter = new PlanSelfScaleAdapter(this, doctorScaleList, tvDoctorScaleLine);
        lvDoctorScale.setAdapter(doctorScaleAdapter);

        elvHealthTips = (SwipeMenuExpandableListView) findViewById(R.id.elvHealthTips);
        elvHealthTips.setGroupIndicator(null);
        healthTipsList = new ArrayList<>();
        healthTipsList.add(new HealthTips());
        healthTipsAdapter = new HealthTipsAdapter(this, healthTipsList, elvHealthTips);
        elvHealthTips.setAdapter(healthTipsAdapter);
        for (int i = 0; i < healthTipsAdapter.getGroupCount(); i++) {
            elvHealthTips.expandGroup(i);
        }
        if ("my".equals(from)) {
            tvPatientList.setVisibility(View.VISIBLE);
        } else {
            tvPatientList.setVisibility(View.GONE);
        }
    }

    private void bindEvent() {
        tvPatientList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.right_txt_btn:
                intent.setClass(PlanInfoActivity.this, PlanEditActivity.class);
                intent.putExtra("from", from);
                intent.putExtra("plan", plan);
                startActivity(intent);
                break;
            case R.id.tvPatientList:
                intent.setClass(PlanInfoActivity.this, PlanPatientListActivity.class);
                intent.putExtra("preceptUuid", plan.getVisitUuid());
                startActivity(intent);
                break;
        }
    }


    private void visitPreceptDetail(String visitUuid) {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("visitUuid", visitUuid)), PlanResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.VISIT_PRECEPT_DETAIL, "2.0"), "正在获取方案详情");
    }


    @Override
    public void onSuccessToBean(Object bean, int flag) {
        PlanResponse plans = (PlanResponse) bean;
        plan = plans.value;
        updateUIData();
    }

    private void updateUIData() {
        if (plan == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
            return;
        }
        tvTitle.setText(plan.getPreceptName());
        tvDrugTherapy.setText(plan.getDrugTherapy());
        tvSideEffects.setText(plan.getSideEffects());
        tvFollowCycle.setText(plan.getPeriod() + "周");
        tvWeightCycle.setText(plan.getWeight() + "周");
        tvEcgCycle.setText(plan.getElectrocardiogram() + "周");
        tvBloodCycle.setText(plan.getBloodRoutine() + "周");
        tvLiverCycle.setText(plan.getHepatic() + "周");
        tvCustomerTest.setText(plan.getSelfPeriod() + "周");
        tvDoctorTest.setText(plan.getDoctorPeriod() + "周");
        medicineList.clear();
        if (plan.getDoctorAdvice() != null)
            medicineList.addAll(plan.getDoctorAdvice());
        healthTipsList.clear();
        if (plan.getHealthGuide() != null)
            healthTipsList.addAll(plan.getHealthGuide());
        checkSycleList.clear();
        if (plan.getOtherMap() != null)
            checkSycleList.addAll(plan.getOtherMap());
        selfScaleList.clear();
        if (plan.getSelfTest() != null)
            selfScaleList.addAll(plan.getSelfTest());
        doctorScaleList.clear();
        if (plan.getDoctorTest() != null)
            doctorScaleList.addAll(plan.getDoctorTest());

        medicineAdapter.notifyDataSetChanged();
        planCheckSycleAdapter.notifyDataSetChanged();
        selfScaleAdapter.notifyDataSetChanged();
        doctorScaleAdapter.notifyDataSetChanged();
        BaseExpandableListAdapter adapter =
                (BaseExpandableListAdapter) elvHealthTips.getExpandableListAdapter();
        adapter.notifyDataSetChanged();
    }
}
