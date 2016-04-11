package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.follow.PlanEditActivity;
import com.hxqydyl.app.ys.activity.follow.PlanPatientListActivity;
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

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenuExpandableListView;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * 医嘱
 */
public class PatientAdviceInfoActivity extends BaseTitleActivity implements View.OnClickListener{

    private TextView tvTitle;
    private TextView tvDrugTherapy; // 不良反应
    private TextView tvSideEffects; //其他治疗

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> medicineList;        // 药品列表

    private TextView tvEdit;

    private String customerUuid = null;    //医嘱uuid
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
        setContentView(R.layout.activity_patient_advice_info);
        Intent intent = getIntent();
        customerUuid = intent.getStringExtra("customerUuid");

        initViewOnBaseTitle("重要医嘱");

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
                visitPreceptDetail(customerUuid);
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

        lvMedicine = (ListView) findViewById(R.id.lvMedicine);

        medicineList = new ArrayList<>();
        medicineList.add(new Medicine());
        medicineAdapter = new MedicineAdapter(this, medicineList, lvMedicine, false);
        lvMedicine.setAdapter(medicineAdapter);
    }

    private void bindEvent() {
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.right_txt_btn:
                intent.setClass(PatientAdviceInfoActivity.this, PatientAdviceEditActivity.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
                break;
        }
    }


    private void visitPreceptDetail(String visitUuid) {
        FollowPlanNet.visitPreceptDetail(visitUuid, new FollowCallback(){
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
                        "  }";
                plan = Plan.parseDetailJson(response);
                dismissDialog();
                handler.sendEmptyMessage(100);
            }
        });
    }

    private void updateUIData() {
        if (plan == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
        }
        tvTitle.setText(plan.getPreceptName());
        tvDrugTherapy.setText(plan.getDrugTherapy());
        tvSideEffects.setText(plan.getSideEffects());

        medicineList.clear();
        medicineList.addAll(plan.getMedicineList());

        medicineAdapter.notifyDataSetChanged();
    }
}
