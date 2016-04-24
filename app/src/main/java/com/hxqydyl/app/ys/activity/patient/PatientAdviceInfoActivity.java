package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.follow.PlanEditActivity;
import com.hxqydyl.app.ys.activity.follow.PlanPatientListActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsAdapter;
import com.hxqydyl.app.ys.adapter.MedicineAdapter;
import com.hxqydyl.app.ys.adapter.PlanCheckSycleAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.Advice;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.http.PatientAdviceNet;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
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

    private String customerUuid = null;    //患者uuid
    private Advice advice = null;

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
        tvTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitle:
                foodRelationDialog();
                break;
        }
    }

    private void foodRelationDialog() {
        String[] items = {"维持原治疗", "调整治疗方案"};
        final NormalListDialog dialog = new NormalListDialog(this, items);
        dialog.title("用药指导")
                .titleBgColor(getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (position == 1) {
                    Intent intent = new Intent();
                    intent.setClass(PatientAdviceInfoActivity.this, PatientAdviceEditActivity.class);
                    intent.putExtra("advice", advice);
                    intent.putExtra("customerUuid", customerUuid);
                    startActivity(intent);
                }
            }
        });
    }


    private void visitPreceptDetail(String visitUuid) {
        PatientAdviceNet.adviceSearch(visitUuid, new FollowCallback(this){
            @Override
            public void onResponse(String response) {
                if (FollowApplyNet.myDev)
                    response = "{" +
                            "  \"drugReaction\" : \"你\"," +
                            "  \"cureNote\" : \"你\"," +
                            "  \"child\" : [" +
                            "    {" +
                            "      \"medicineUuid\" : \"\"," +
                            "      \"directions\" : \"早\"," +
                            "      \"dosage\" : \"5|mg\"," +
                            "      \"frequency\" : \"1\"," +
                            "      \"unit\" : \"\"," +
                            "      \"food\" : \"饭前服用\"" +
                            "    }" +
                            "  ]," +
                            "  \"uuid\" : \"1ff22cc9fe064bc9a1a7afb7b1e24619\"" +
                            "}";
                if (StringUtils.isEmpty(response)) {
                    UIHelper.ToastMessage(PatientAdviceInfoActivity.this, "没有数据");
                    return;
                }
                advice = Advice.parseDetailJson(response);
                dismissDialog();
                handler.sendEmptyMessage(100);
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                UIHelper.ToastMessage(PatientAdviceInfoActivity.this, "数据加载失败");
                dismissDialog();
                onResponse("t");
            }
        });
    }

    private void updateUIData() {
        if (advice == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
            return;
        }
        tvDrugTherapy.setText(advice.getDrugReaction());
        tvSideEffects.setText(advice.getCureNote());

        medicineList.clear();
        medicineList.addAll(advice.getMedicineList());

        medicineAdapter.notifyDataSetChanged();
    }
}
