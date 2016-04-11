package com.hxqydyl.app.ys.activity.patient;

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
import com.hxqydyl.app.ys.activity.follow.ChoiceScaleActivity;
import com.hxqydyl.app.ys.activity.follow.ChoiceSelfActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsAdapter;
import com.hxqydyl.app.ys.adapter.MedicineAdapter;
import com.hxqydyl.app.ys.adapter.MedicineDosageAdapter;
import com.hxqydyl.app.ys.adapter.PlanCheckSycleAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
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
 * 编辑医嘱
 */
public class PatientAdviceEditActivity extends BaseTitleActivity implements View.OnClickListener{

    private EditText etTitle;
    private EditText etDrugTherapy; // 不良反应
    private EditText etSideEffects; //其他治疗


    private LinearLayout llAddMedicine;  //   添加其他药品

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private ArrayList<Medicine> medicineList;        // 药品列表

    private Button btnSave;

    private String from = null; // my\suggest
    private Plan plan = null;

    private FollowCallback updateFollowCallback;

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
        setContentView(R.layout.activity_patient_advice_edit);
        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        from = intent.getStringExtra("from");
        if (plan != null) {
            // 编辑
            initViewOnBaseTitle("编辑方案");
        } else {
            initViewOnBaseTitle("新增方案");
        }

        initView();
        bindEvent();
        initUpdateFollowCallback();
        handler.sendEmptyMessage(100);
    }

    private void initView() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDrugTherapy = (EditText) findViewById(R.id.etDrugTherapy);
        etSideEffects = (EditText) findViewById(R.id.etSideEffects);

        llAddMedicine = (LinearLayout) findViewById(R.id.llAddMedicine);

        lvMedicine = (ListView) findViewById(R.id.lvMedicine);

        medicineList = new ArrayList<>();
        medicineList.add(new Medicine());
        medicineAdapter = new MedicineAdapter(this, medicineList, lvMedicine, true);
        lvMedicine.setAdapter(medicineAdapter);

        btnSave = (Button) findViewById(R.id.btnSave);
    }

    private void bindEvent() {
        setBackListener();
        llAddMedicine.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddMedicine:
                medicineList.add(new Medicine());
                medicineAdapter.notifyDataSetChanged(true);
                break;

            case R.id.btnSave:
                saveInfo();
                break;
        }
    }

    private void saveInfo() {
        String title = etTitle.getText().toString();
        if (StringUtils.isEmpty(title)) {
            UIHelper.ToastMessage(this, "方案名称不能为空");
            return;
        }

        String drugTherapy = etDrugTherapy.getText().toString();
        if (StringUtils.isEmpty(drugTherapy)) {
            UIHelper.ToastMessage(this, "药物不良反应处理不能为空");
            return;
        }
        String sideEffects = etSideEffects.getText().toString();
        if (StringUtils.isEmpty(sideEffects)) {
            UIHelper.ToastMessage(this, "其他治疗不能为空");
            return;
        }

        if (lvMedicine.getChildCount() == 0) {
            UIHelper.ToastMessage(this, "请添加药品信息");
            return;
        }

        ArrayList<Medicine> mList = new ArrayList<>();
        for (int i=0; i < lvMedicine.getChildCount(); i++) {
            MedicineAdapter.ViewHolder vh = (MedicineAdapter.ViewHolder) lvMedicine.getChildAt(i).getTag();
            Medicine m = new Medicine();
            m.setName(vh.etName.getText().toString());
            m.setFood(vh.tvFoodRelation.getText().toString());
            m.setTimeNoon(vh.boolTimeNoon);
            m.setTimeNight(vh.boolTimeNight);
            m.setTimeMorning(vh.boolTimeMorning);

            ListView lvDosage = vh.lvDosage;
            ArrayList<MedicineDosage> mdList = new ArrayList<>();
            for (int j=0; j < lvDosage.getChildCount(); j++) {
                MedicineDosageAdapter.ViewHolder mdVh = (MedicineDosageAdapter.ViewHolder) lvDosage.getChildAt(j).getTag();
                MedicineDosage md = new MedicineDosage();
                md.setDay(mdVh.etDay.getText().toString());
                md.setSize(mdVh.etSize.getText().toString());
                md.setUnit(mdVh.tvUnit.getText().toString());
                mdList.add(md);
            }
            m.setMdList(mdList);
            mList.add(m);
        }

        plan.setPreceptName(title);
        plan.setDrugTherapy(drugTherapy);
        plan.setSideEffects(sideEffects);

        plan.setMedicineList(mList);
        try {
            if (StringUtils.isEmpty(plan.getVisitUuid())) {
                //新建
                FollowPlanNet.addVisitPrecept(plan, updateFollowCallback);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.ToastMessage(this, "提交失败");
        }

    }

    private void initUpdateFollowCallback() {
        updateFollowCallback = new FollowCallback(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                UIHelper.ToastMessage(PatientAdviceEditActivity.this, "保存成功");
                finish();
            }

            @Override
            public void onFail(String status, String msg) {
                super.onFail(status, msg);
                UIHelper.ToastMessage(PatientAdviceEditActivity.this, "保存失败");
            }
        };
    }

    private void updateUIData() {
        if (plan == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
        }
        if ("my".equals(from)) {
            etTitle.setText(plan.getPreceptName());
        }
        etDrugTherapy.setText(plan.getDrugTherapy());
        etSideEffects.setText(plan.getSideEffects());

        medicineList.clear();
        medicineList.addAll(plan.getMedicineList());

        medicineAdapter.notifyDataSetChanged();
    }
}
