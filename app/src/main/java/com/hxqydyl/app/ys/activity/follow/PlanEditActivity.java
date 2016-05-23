package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsEditAdapter;
import com.hxqydyl.app.ys.adapter.MedicineDosageEditAdapter;
import com.hxqydyl.app.ys.adapter.MedicineEditAdapter;
import com.hxqydyl.app.ys.adapter.PlanCheckSycleAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.CheckSycle;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.xus.http.httplib.model.PostPrams;

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
public class PlanEditActivity extends BaseRequstActivity implements View.OnClickListener {

    private EditText etTitle;
    private LinearLayout llAddMedicine;  //   添加其他药品
    private EditText etDrugTherapy; // 不良反应
    private EditText etSideEffects; //其他治疗
    private TextView tvFollowCycle; //随访
    private TextView tvWeightCycle;  //体重
    private TextView tvEcgCycle;  //心电图
    private TextView tvBloodCycle;  //血
    private TextView tvLiverCycle;  //肝
    private TextView tvSelfScale;  //   添加自评量表
    private TextView tvDoctorScale;  // 添加医评量表
    private LinearLayout llAddOtherSycle;      // 添加其他检查周期


    private View tvDoctorScaleLine;
    private View tvSelfScaleLine;

    private ListView lvMedicine;
    private MedicineEditAdapter medicineAdapter;
    private ArrayList<ImportantAdviceChild> medicineList;        // 药品列表

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
    private HealthTipsEditAdapter HealthTipsEditAdapter;
    private ArrayList<HealthTips> healthTipsList;
    private LinearLayout llAddTips;
    private TextView tvCustomerTest;//自评周期
    private TextView tvDoctorTest;//医评周期
    private Button btnSave;
    private String from = null; // my\suggest
    private Plan plan = null;

    private StringBuffer ortherMapDelete = new StringBuffer();
    private StringBuffer healthGuideDelete = new StringBuffer();

    private boolean isdraft = true;//是否可以保存为草稿
    private boolean isAdd = true;//是否为新增方案

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);
        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        from = intent.getStringExtra("from");
        isAdd = (plan == null);
        if (!isAdd) {
            isdraft = plan.getDelFlag().equals("0");

        } else {
            isdraft = true;

        }
        initViewOnBaseTitle(isAdd ? "新增方案" : "编辑方案");
        initView();
        bindEvent();
        updateUIData();
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
        medicineAdapter = new MedicineEditAdapter(this, medicineList, lvMedicine);
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
        HealthTipsEditAdapter = new HealthTipsEditAdapter(this, healthTipsList, elvHealthTips);
        elvHealthTips.setAdapter(HealthTipsEditAdapter);
        llAddTips = (LinearLayout) findViewById(R.id.llAddTips);
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
        tvCustomerTest.setOnClickListener(this);
        tvDoctorTest.setOnClickListener(this);
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
                        HealthTipsEditAdapter.notifyDataSetChanged();
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
            case R.id.tvCustomerTest:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem2);
                break;
            case R.id.tvDoctorTest:
                DialogUtils.cycleDialog(this, (TextView) v, CheckSycle.cycleItem2);
                break;
            case R.id.tvDoctorScale:
                Intent choiceScaleIntent = new Intent(this, ChoiceScaleActivity.class);
                startActivityForResult(choiceScaleIntent, 10);
                break;
            case R.id.llAddTips:
                healthTipsList.add(new HealthTips());
                HealthTipsEditAdapter.notifyDataSetChanged();
                for (int i = 0; i < HealthTipsEditAdapter.getGroupCount(); i++) {
                    elvHealthTips.expandGroup(i);
                }
                break;
            case R.id.llAddMedicine:
                medicineList.add(new ImportantAdviceChild());
                medicineAdapter.notifyDataSetChanged();
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
        try {
            if (requestCode == 10) {
                if (resultCode == 1) {
                    List<Scale> list = (List<Scale>) data.getSerializableExtra("list");
                    selfScaleList.clear();
                    selfScaleList.addAll(list);
                    selfScaleAdapter.notifyDataSetChanged();
                } else if (resultCode == 2) {
                    List<Scale> list = (List<Scale>) data.getSerializableExtra("list");
                    doctorScaleList.clear();
                    doctorScaleList.addAll(list);
                    doctorScaleAdapter.notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.ToastMessage(PlanEditActivity.this, "解析出错啦，刷新下会有意外惊喜");
        }

    }


    private void saveInfo() {
        for (int i = 0; i < HealthTipsEditAdapter.getGroupCount(); i++) {
            elvHealthTips.expandGroup(i);
        }
        String delFlag = "1";
        String title = etTitle.getText().toString();
        if (StringUtils.isEmpty(title)) {
            if (isdraft) {
                delFlag = "0";
            } else {
                UIHelper.ToastMessage(this, "方案名称不能为空");
                return;
            }
        }
        String drugTherapy = etDrugTherapy.getText().toString();
        if (StringUtils.isEmpty(drugTherapy)) {
            if (isdraft) {
                delFlag = "0";
            } else {
                UIHelper.ToastMessage(this, "药物不良反应处理不能为空");
                return;
            }
        }
        String sideEffects = etSideEffects.getText().toString();
        if (StringUtils.isEmpty(sideEffects)) {
            if (isdraft) {
                delFlag = "0";
            } else {
                UIHelper.ToastMessage(this, "其他治疗不能为空");
                return;
            }
        }
        if (lvMedicine.getChildCount() == 0) {
            if (isdraft) {
                delFlag = "0";
            } else {
                UIHelper.ToastMessage(this, "请添加药品信息");
                return;
            }
        }

        ArrayList<ImportantAdviceChild> mList = new ArrayList<>();
        for (int i = 0; i < lvMedicine.getChildCount(); i++) {
            MedicineEditAdapter.ViewHolder vh = (MedicineEditAdapter.ViewHolder) lvMedicine.getChildAt(i).getTag();
            ImportantAdviceChild m = new ImportantAdviceChild();
            m.setMedicineUuid(vh.etName.getText().toString());
            m.setFood(vh.tvFoodRelation.getText().toString());
            m.setTimeNoon(vh.boolTimeNoon);
            m.setTimeNight(vh.boolTimeNight);
            m.setTimeMorning(vh.boolTimeMorning);
            m.setUuid(vh.uuid);
            ListView lvDosage = vh.lvDosage;
            ArrayList<MedicineDosage> mdList = new ArrayList<>();
            for (int j = 0; j < lvDosage.getChildCount(); j++) {
                MedicineDosageEditAdapter.ViewHolder mdVh = (MedicineDosageEditAdapter.ViewHolder) lvDosage.getChildAt(j).getTag();
                MedicineDosage md = new MedicineDosage();
                md.setDay(mdVh.etDay.getText().toString());
                md.setSize(mdVh.etSize.getText().toString());
                md.setUnit(mdVh.tvUnit.getText().toString());
                mdList.add(md);
            }
            m.setMd(mdList);
            mList.add(m);
        }

        ArrayList<CheckSycle> csList = new ArrayList<>();
        for (int i = 0; i < lvOtherSycle.getChildCount(); i++) {
            PlanCheckSycleAdapter.ViewHolder vh = (PlanCheckSycleAdapter.ViewHolder) lvOtherSycle.getChildAt(i).getTag();
            CheckSycle cs = new CheckSycle();
            cs.setName(vh.tvName.getText().toString());
            cs.setPeriod(vh.tvLiverCycle.getText().toString());
            csList.add(cs);
        }

        ArrayList<HealthTips> htList = new ArrayList<>();
        for (int i = 1; i < elvHealthTips.getChildCount(); i =i+2) {
            HealthTipsEditAdapter.ChildViewHolder vh = (HealthTipsEditAdapter.ChildViewHolder) elvHealthTips.getChildAt(i).getTag();
            HealthTips ht = new HealthTips();
            ht.setPeriod(vh.etDay.getText().toString());
            ht.setRest(vh.etOther.getText().toString());
            if (!TextUtils.isEmpty(vh.uuid)){
                ht.setUuid(vh.uuid);
            }
            htList.add(ht);
        }

        if (plan == null) {
            plan = new Plan();
        }
        plan.setPreceptName(title);
        plan.setDrugTherapy(drugTherapy);
        plan.setSideEffects(sideEffects);
        plan.setDoctorTest(doctorScaleList);
        plan.setSelfTest(selfScaleList);
        plan.setPeriod(cycleNum(tvFollowCycle));
        plan.setWeight(cycleNum(tvWeightCycle));
        plan.setHepatic(cycleNum(tvLiverCycle));
        plan.setBloodRoutine(cycleNum(tvBloodCycle));
        plan.setElectrocardiogram(cycleNum(tvEcgCycle));
        plan.setSelfPeriod(cycleNum(tvCustomerTest));
        plan.setDoctorPeriod(cycleNum(tvDoctorTest));

        plan.setDoctorAdvice(mList);
        plan.setOtherMap(csList);
        plan.setHealthGuide(htList);
        try {
            if (isAdd) {
                //新建
                addViditPrecept(plan, delFlag);
            } else {
                upViditPrecept(plan, delFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.ToastMessage(this, "提交失败");
        }

    }

    //新建随访方案
    private void addViditPrecept(Plan plan, String delFlag) {
        List<ImportantAdviceChild> advice = new ArrayList<>();
        for (int i = 0; i < plan.getDoctorAdvice().size(); i++) {
            advice.add(plan.getDoctorAdvice().get(i).toJsonBean());
        }
        Gson myJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String doctorAdvice = myJson.toJson(advice);
        PostPrams postPrams = toPostParams(
                toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()),//医生ID
                toParamsBaen("preceptName", plan.getPreceptName()),  //方案名称
                toParamsBaen("drugTherapy", plan.getDrugTherapy()),  //药物不良反应处理
                toParamsBaen("sideEffects", plan.getSideEffects()),     //其他治疗
                toParamsBaen("doctorAdvice", doctorAdvice),//药物信息
                toParamsBaen("otherMap", gson.toJson(plan.getOtherMap())),//其他自定义随访周期
                toParamsBaen("period", plan.getPeriod()),//随访周期
                toParamsBaen("electrocardiogram", plan.getElectrocardiogram()),//心电图周期
                toParamsBaen("hepatic", plan.getHepatic()),//肝功能周期
                toParamsBaen("bloodRoutine", plan.getBloodRoutine()), //血常规周期
                toParamsBaen("weight", plan.getWeight()), //体重功能周期
                toParamsBaen("selfPeriod", plan.getSelfPeriod()), //自评周期
                toParamsBaen("doctorPeriod", plan.getDoctorPeriod()), //医评周期周期
                toParamsBaen("selfTest", Scale.parseIdStr(plan.getSelfTest())),///自评量表
                toParamsBaen("doctorTest", Scale.parseIdStr(plan.getDoctorTest())),//医评量表
                toParamsBaen("healthGuide", gson.toJson(plan.getHealthGuide())),//健康小贴士
                toParamsBaen("delFlag", delFlag)//是否是完成的方案
        );
        toNomalNet(postPrams, BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.ADD_VISIT_PRECEPT, "2.0"), "正在添加随访方案");
    }


    private void upViditPrecept(Plan plan, String delFlag) {
        List<ImportantAdviceChild> advice = new ArrayList<>();
        for (int i = 0; i < plan.getDoctorAdvice().size(); i++) {
            advice.add(plan.getDoctorAdvice().get(i).toJsonBean());
        }
        Gson myJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String doctorAdvice = myJson.toJson(advice);
        PostPrams postPrams = toPostParams(
                toParamsBaen("visitUuid", plan.getVisitUuid()),  //方案id
                toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()),//医生ID
                toParamsBaen("preceptName", plan.getPreceptName()),//方案名称
                toParamsBaen("drugTherapy", plan.getDrugTherapy()),//药物不良反应处理
                toParamsBaen("sideEffects", plan.getSideEffects()),//其他治疗
                toParamsBaen("doctorAdvice", doctorAdvice),//药物信息
                toParamsBaen("otherMap", gson.toJson(plan.getOtherMap())),//其他自定义随访周期
                toParamsBaen("period", plan.getPeriod()),//随访周期
                toParamsBaen("electrocardiogram", plan.getElectrocardiogram()),//心电图周期
                toParamsBaen("hepatic", plan.getHepatic()),//肝功能周期
                toParamsBaen("bloodRoutine", plan.getBloodRoutine()), //血常规周期
                toParamsBaen("weight", plan.getWeight()), //体重功能周期
                toParamsBaen("selfPeriod", plan.getSelfPeriod()), //自评周期
                toParamsBaen("doctorPeriod", plan.getDoctorPeriod()), //医评周期周期

                toParamsBaen("selfTest", Scale.parseIdStr(plan.getSelfTest())),///自评量表
                toParamsBaen("doctorTest", Scale.parseIdStr(plan.getDoctorTest())),//医评量表
                toParamsBaen("healthGuide", gson.toJson(plan.getHealthGuide())),//健康小贴士
                toParamsBaen("healthGuideDelete", healthGuideDelete.toString()),///自评量表删除
                toParamsBaen("doctorAdviceDelete", medicineAdapter.getUuidDeleteSb().toString()),//医评量表删除
                toParamsBaen("ortherMapDelete", ortherMapDelete.toString()),//健康小贴士删除
                toParamsBaen("delFlag", delFlag)//是否是完成的方案
        );
        toNomalNet(postPrams, BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.EDIT_VISIT_PRECEPT, "2.0"), "正在修改随访方案");

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

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        UIHelper.ToastMessage(this, "保存成功");
        this.finish();
    }
}
