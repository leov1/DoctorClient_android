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
 * 编辑随访方案
 */
public class PlanEditActivity extends BaseTitleActivity implements View.OnClickListener{

    private EditText etTitle;
    private EditText etDrugTherapy; // 不良反应
    private EditText etSideEffects; //其他治疗

    private TextView tvFollowCycle; //随访
    private TextView tvWeightCycle;  //体重
    private TextView tvEcgCycle;  //心电图
    private TextView tvBloodCycle;  //血
    private TextView tvLiverCycle;  //肝

    private LinearLayout llAddMedicine;  //   添加其他药品
    private LinearLayout llAddOtherSycle;      // 添加其他检查周期
    private TextView tvSelfScale;  //   添加自评量表
    private TextView tvDoctorScale;  // 添加医评量表
    private View tvDoctorScaleLine;
    private View tvSelfScaleLine;

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private ArrayList<Medicine> medicineList;        // 药品列表

    private SwipeMenuListView lvOtherSycle;
    private PlanCheckSycleAdapter planCheckSycleAdapter;
    private ArrayList<CheckSycle> checkSycleList;

    private ListView lvSelfScale;
    private ListView lvDoctorScale;
    private PlanSelfScaleAdapter selfScaleAdapter;
    private PlanSelfScaleAdapter doctorScaleAdapter;

    private ArrayList<Scale> selfScaleList;
    private ArrayList<Scale> doctorScaleList;

    private SwipeMenuExpandableListView elvHealthTips;
    private HealthTipsAdapter healthTipsAdapter;
    private ArrayList<HealthTips> healthTipsList;
    private LinearLayout llAddTips;

    private Button btnSave;

    private String from = null; // my\suggest
    private Plan plan = null;

    private FollowCallback updateFollowCallback;
    private StringBuffer ortherMapDelete = new StringBuffer();
    private StringBuffer healthGuideDelete = new StringBuffer();

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
        setContentView(R.layout.activity_plan_edit);
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

        tvFollowCycle = (TextView) findViewById(R.id.tvFollowCycle);
        tvWeightCycle = (TextView) findViewById(R.id.tvWeightCycle);
        tvEcgCycle = (TextView) findViewById(R.id.tvEcgCycle);
        tvBloodCycle = (TextView) findViewById(R.id.tvBloodCycle);
        tvLiverCycle = (TextView) findViewById(R.id.tvLiverCycle);

        llAddMedicine = (LinearLayout) findViewById(R.id.llAddMedicine);
        llAddOtherSycle = (LinearLayout) findViewById(R.id.llAddOtherSycle);
        tvSelfScale = (TextView) findViewById(R.id.tvSelfScale);
        tvDoctorScale = (TextView) findViewById(R.id.tvDoctorScale);
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
        medicineAdapter = new MedicineAdapter(this, medicineList, lvMedicine, true);
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
        llAddTips = (LinearLayout) findViewById(R.id.llAddTips);
        for(int i = 0; i < healthTipsAdapter.getGroupCount(); i++){
            elvHealthTips.expandGroup(i);
        }
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    private void bindEvent() {
        setBackListener();
        tvFollowCycle.setOnClickListener(this);
        tvWeightCycle.setOnClickListener(this);
        tvEcgCycle.setOnClickListener(this);
        tvBloodCycle.setOnClickListener(this);
        tvLiverCycle.setOnClickListener(this);
        llAddMedicine.setOnClickListener(this);
        llAddOtherSycle.setOnClickListener(this);
        tvSelfScale.setOnClickListener(this);
        tvDoctorScale.setOnClickListener(this);
        llAddTips.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        lvOtherSycle.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(31, 128, 183)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        });

        lvOtherSycle.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        CheckSycle tmp = checkSycleList.get(position);
                        if (StringUtils.isNotEmpty(tmp.getUuid())) {
                            if (ortherMapDelete.length() > 0) ortherMapDelete.append(",");
                            ortherMapDelete.append(tmp.getUuid());
                        }
                        checkSycleList.remove(position);
                        planCheckSycleAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        elvHealthTips.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(31, 128, 183)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        });

        elvHealthTips.setOnMenuItemClickListener(new SwipeMenuExpandableListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        HealthTips tmp = healthTipsList.get(position);
                        if (StringUtils.isNotEmpty(tmp.getUuid())) {
                            if (healthGuideDelete.length() > 0) healthGuideDelete.append(",");
                            healthGuideDelete.append(tmp.getUuid());
                        }
                        healthTipsList.remove(position);
                        healthTipsAdapter.notifyDataSetChanged(true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFollowCycle:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem1);
                break;
            case R.id.tvWeightCycle:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem1);
                break;
            case R.id.tvEcgCycle:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem2);
                break;
            case R.id.tvBloodCycle:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem2);
                break;
            case R.id.tvLiverCycle:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem2);
                break;
            case R.id.tvSelfScale:
                Intent choiceSelfIntent = new Intent(this, ChoiceSelfActivity.class);
                startActivityForResult(choiceSelfIntent, 10);
                break;
            case R.id.tvDoctorScale:
                Intent choiceScaleIntent = new Intent(this, ChoiceScaleActivity.class);
                startActivityForResult(choiceScaleIntent, 10);
                break;
            case R.id.llAddTips:
                healthTipsList.add(new HealthTips());
                healthTipsAdapter.notifyDataSetChanged(true);
                for(int i = 0; i < healthTipsAdapter.getGroupCount(); i++){
                    elvHealthTips.expandGroup(i);
                }
                break;
            case R.id.llAddMedicine:
                medicineList.add(new Medicine());
                medicineAdapter.notifyDataSetChanged(true);
                break;
            case R.id.llAddOtherSycle:
                DialogUtils.showAddCheckSycleDialog(this, new DialogUtils.SaveCheckSycleListener() {
                    @Override
                    public boolean save(String name, String sycle) {
                        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(sycle)) {
                            return false;
                        }
                        checkSycleList.add(new CheckSycle(name, sycle));
                        planCheckSycleAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
                break;
            case R.id.btnSave:
                saveInfo();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if (requestCode == 10) {
                if (resultCode == 1) {
                    List<Scale> list = (List<Scale>)data.getSerializableExtra("list");
                    selfScaleList.clear();
                    selfScaleList.addAll(list);
                    selfScaleAdapter.notifyDataSetChanged();
                } else if(resultCode == 2) {
                    List<Scale> list = (List<Scale>)data.getSerializableExtra("list");
                    doctorScaleList.clear();
                    doctorScaleList.addAll(list);
                    doctorScaleAdapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            UIHelper.ToastMessage(PlanEditActivity.this,"解析出错啦，刷新下会有意外惊喜");
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

        ArrayList<CheckSycle> csList = new ArrayList<>();
        for (int i=0; i < lvOtherSycle.getChildCount(); i++) {
            PlanCheckSycleAdapter.ViewHolder vh = (PlanCheckSycleAdapter.ViewHolder) lvOtherSycle.getChildAt(i).getTag();
            CheckSycle cs = new CheckSycle();
            cs.setName(vh.tvName.getText().toString());
            cs.setPeriod(vh.tvLiverCycle.getText().toString());
            csList.add(cs);
        }

        ArrayList<HealthTips> htList = new ArrayList<>();
        for (int i=1; i < elvHealthTips.getChildCount(); i+=2) {
            HealthTipsAdapter.ChildViewHolder vh = (HealthTipsAdapter.ChildViewHolder) elvHealthTips.getChildAt(i).getTag();
            HealthTips ht = new HealthTips();
            ht.setDay(vh.etDay.getText().toString());
            ht.setFood(vh.etFood.getText().toString());
            ht.setSport(vh.etSport.getText().toString());
            ht.setSleep(vh.etSleep.getText().toString());
            ht.setOther(vh.etOther.getText().toString());
            htList.add(ht);
        }
        if (plan == null) {
            plan = new Plan();
        }
        plan.setPreceptName(title);
        plan.setDrugTherapy(drugTherapy);
        plan.setSideEffects(sideEffects);
        plan.setDoctorTestList(doctorScaleList);
        plan.setSelfTestList(selfScaleList);
        plan.setPeriod(cycleNum(tvFollowCycle));
        plan.setWeight(cycleNum(tvWeightCycle));
        plan.setHepatic(cycleNum(tvLiverCycle));
        plan.setBloodRoutine(cycleNum(tvBloodCycle));
        plan.setElectrocardiogram(cycleNum(tvEcgCycle));

        plan.setMedicineList(mList);
        plan.setOtherCheckSycle(csList);
        plan.setHealthTipsList(htList);
        try {
            if (StringUtils.isEmpty(plan.getVisitUuid())) {
                //新建
                FollowPlanNet.addVisitPrecept(plan, updateFollowCallback);
            } else {
                FollowPlanNet.editVisitPrecept(plan, medicineAdapter.getUuidDeleteSb().toString(),
                        ortherMapDelete.toString(), healthGuideDelete.toString(), updateFollowCallback);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.ToastMessage(this, "提交失败");
        }

    }

    private void initUpdateFollowCallback() {
        updateFollowCallback = new FollowCallback(this){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                UIHelper.ToastMessage(PlanEditActivity.this, "保存成功");
                finish();
            }

            @Override
            public void onFail(String status, String msg) {
                super.onFail(status, msg);
                UIHelper.ToastMessage(PlanEditActivity.this, "保存失败");
            }
        };
    }



    private String cycleNum(TextView tv) {
        return tv.getText().toString().replace("周", "");
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void updateUIData() {
        if (plan == null) {
            return;
        }
        if ("my".equals(from)) {
            etTitle.setText(plan.getPreceptName());
        }
        etDrugTherapy.setText(plan.getDrugTherapy());
        etSideEffects.setText(plan.getSideEffects());

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
