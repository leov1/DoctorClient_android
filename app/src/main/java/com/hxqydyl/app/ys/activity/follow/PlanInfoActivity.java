package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsAdapter;
import com.hxqydyl.app.ys.adapter.MedicineAdapter;
import com.hxqydyl.app.ys.adapter.PlanCheckSycleAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuExpandableListView;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * Created by wangchao36 on 16/3/22.
 * 编辑随访方案
 */
public class PlanInfoActivity extends BaseTitleActivity implements View.OnClickListener{

    private TextView tvTitle;
    private TextView tvDrugTherapy; // 不良反应
    private TextView tvSideEffects; //其他治疗

    private TextView tvFollowCycle; //随访
    private TextView tvWeightCycle;  //体重
    private TextView tvEcgCycle;  //心电图
    private TextView tvBloodCycle;  //血
    private TextView tvLiverCycle;  //肝

    private View tvDoctorScaleLine;
    private View tvSelfScaleLine;

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> medicineList;        // 药品列表

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                updateUIData();
            }
        }
    };

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                showDialog("加载中");
                visitPreceptDetail(visitUuid);
            }
        }).run();
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
        medicineList.add(new Medicine());
        medicineAdapter = new MedicineAdapter(this, medicineList, lvMedicine, false);
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
        for(int i = 0; i < healthTipsAdapter.getGroupCount(); i++){
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
        FollowPlanNet.visitPreceptDetail(visitUuid, new FollowCallback(this){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (FollowApplyNet.myDev)
                    response = "{" +
                        "  \"hepatic\" : \"1\"," +
                        "  \"selfTest\" : [" +
                        "    {" +
                        "      \"description\" : \"\"," +
                        "      \"id\" : \"0001\"," +
                        "      \"thumb\" : \"\"," +
                        "      \"digest\" : \"\"," +
                        "      \"createDate\" : \"2015-11-24 20:09:56\"," +
                        "      \"total\" : 0," +
                        "      \"url\" : \"\"," +
                        "      \"title\" : \"综合测试2\"," +
                        "      \"self\" : \"0\"," +
                        "      \"cover\" : \"\"," +
                        "      \"integral\" : 0," +
                        "      \"media\" : \"\"," +
                        "      \"sort\" : 1," +
                        "      \"parentId\" : \"00\"" +
                        "    }" +
                        "  ]," +
                        "  \"period\" : \"6\"," +
                        "  \"preceptName\" : \"方案名称\"," +
                        "  \"query\" : {" +
                        "    \"success\" : \"1\"," +
                        "    \"message\" : \"操作成功\"" +
                        "  }," +
                        "  \"doctorAdvice\" : [" +
                        "    {" +
                        "      \"doctorName\" : \"李狗蛋\"," +
                        "      \"productName\" : \"\"," +
                        "      \"medicalRecordUuid\" : \"\"," +
                        "      \"dosage\" : \"1|mg\"," +
                        "      \"opeTime\" : \"2016-04-08 11:46:19\"," +
                        "      \"serviceStaffUuid\" : \"ae0a8f2d76e3442981625ee96648b6eb\"," +
                        "      \"visitRecordUuid\" : \"sitPrecept0000000343\"," +
                        "      \"medicalDateBegin\" : null," +
                        "      \"sortType\" : \"desc\"," +
                        "      \"medicineUuid\" : \"也是名称\"," +
                        "      \"uuid\" : \"ctorAdvice0000000368\"," +
                        "      \"cureNote\" : \"\"," +
                        "      \"customerUuid\" : \"\"," +
                        "      \"delFlag\" : \"1\"," +
                        "      \"type\" : \"0\"," +
                        "      \"food\" : \"饭前服用\"," +
                        "      \"state\" : \"\"," +
                        "      \"sortName\" : \"uuid\"," +
                        "      \"oper\" : \"\"," +
                        "      \"unit\" : \"\"," +
                        "      \"createTime\" : \"\"," +
                        "      \"directions\" : \"早,中,晚\"," +
                        "      \"frequency\" : \"1\"," +
                        "      \"mapCondition\" : {" +
                        "" +
                        "      }," +
                        "      \"customerName\" : \"\"," +
                        "      \"medicalDateEnd\" : null" +
                        "    }" +
                        "  ]," +
                        "  \"electrocardiogram\" : \"5\"," +
                        "  \"weight\" : \"4\"," +
                        "  \"drugTherapy\" : \"药物不良咋处理\"," +
                        "  \"sideEffects\" : \"其他治疗也行\"," +
                        "  \"otherMap\" : [" +
                        "" +
                        "  ]," +
                        "  \"visitUuid\" : \"sitPrecept0000000343\"," +
                        "  \"doctorTest\" : [" +
                        "    {" +
                        "      \"description\" : \"\"," +
                        "      \"id\" : \"0005\"," +
                        "      \"thumb\" : \"\"," +
                        "      \"digest\" : \"\"," +
                        "      \"createDate\" : \"2015-11-24 20:10:36\"," +
                        "      \"total\" : 0," +
                        "      \"url\" : \"\"," +
                        "      \"title\" : \"综合测试6\"," +
                        "      \"self\" : \"1\"," +
                        "      \"cover\" : \"\"," +
                        "      \"integral\" : 0," +
                        "      \"media\" : \"\"," +
                        "      \"sort\" : 5," +
                        "      \"parentId\" : \"00\"" +
                        "    }," +
                        "    {" +
                        "      \"description\" : \"\"," +
                        "      \"id\" : \"0006\"," +
                        "      \"thumb\" : \"\"," +
                        "      \"digest\" : \"\"," +
                        "      \"createDate\" : \"2015-11-24 20:10:44\"," +
                        "      \"total\" : 0," +
                        "      \"url\" : \"\"," +
                        "      \"title\" : \"综合测试7\"," +
                        "      \"self\" : \"1\"," +
                        "      \"cover\" : \"\"," +
                        "      \"integral\" : 0," +
                        "      \"media\" : \"\"," +
                        "      \"sort\" : 6," +
                        "      \"parentId\" : \"00\"" +
                        "    }" +
                        "  ]," +
                        "  \"bloodRoutine\" : \"2\"," +
                        "  \"healthGuide\" : [" +
                        "  ] }";
                plan = Plan.parseDetailJson(response);
                dismissDialog();
                handler.sendEmptyMessage(100);
            }
        });
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

        medicineList.clear();
        medicineList.addAll(plan.getMedicineList());
        healthTipsList.clear();
        healthTipsList.addAll(plan.getHealthTipsList());
        checkSycleList.clear();
        checkSycleList.addAll(plan.getOtherCheckSycle());
        selfScaleList.clear();
        selfScaleList.addAll(plan.getSelfTestList());
        doctorScaleList.clear();
        doctorScaleList.addAll(plan.getDoctorTestList());

        medicineAdapter.notifyDataSetChanged();
        planCheckSycleAdapter.notifyDataSetChanged();
        selfScaleAdapter.notifyDataSetChanged();
        doctorScaleAdapter.notifyDataSetChanged();
        BaseExpandableListAdapter adapter =
                (BaseExpandableListAdapter) elvHealthTips.getExpandableListAdapter();
        adapter.notifyDataSetChanged();
    }
}
