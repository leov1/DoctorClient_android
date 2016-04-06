package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ExpandableListView;
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
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * Created by wangchao36 on 16/3/22.
 * 编辑随访方案
 */
public class PlanEditActivity extends BaseTitleActivity implements View.OnClickListener{

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
    private List<Medicine> medicineList;

    private SwipeMenuListView lvOtherSycle;
    private PlanCheckSycleAdapter planCheckSycleAdapter;
    private List<CheckSycle> checkSycleList;

    private ListView lvSelfScale;
    private ListView lvDoctorScale;
    private PlanSelfScaleAdapter selfScaleAdapter;
    private PlanSelfScaleAdapter doctorScaleAdapter;

    private List<String> selfScaleList;
    private List<String> doctorScaleList;

    private ExpandableListView elvHealthTips;
    private HealthTipsAdapter healthTipsAdapter;
    private List<HealthTips> healthTipsList;
    private LinearLayout llAddTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_edit);
        initViewOnBaseTitle("新增方案");
        initView();
        bindEvent();
    }

    private void initView() {
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
        medicineAdapter = new MedicineAdapter(this, medicineList);
        lvMedicine.setAdapter(medicineAdapter);

        selfScaleList = new ArrayList<>();
        selfScaleAdapter = new PlanSelfScaleAdapter(this, selfScaleList, tvSelfScaleLine);
        lvSelfScale.setAdapter(selfScaleAdapter);
        doctorScaleList = new ArrayList<>();
        doctorScaleAdapter = new PlanSelfScaleAdapter(this, doctorScaleList, tvDoctorScaleLine);
        lvDoctorScale.setAdapter(doctorScaleAdapter);

        elvHealthTips = (ExpandableListView) findViewById(R.id.elvHealthTips);
        elvHealthTips.setGroupIndicator(null);
        healthTipsList = new ArrayList<>();
        healthTipsList.add(new HealthTips());
        healthTipsAdapter = new HealthTipsAdapter(this, healthTipsList);
        elvHealthTips.setAdapter(healthTipsAdapter);
        llAddTips = (LinearLayout) findViewById(R.id.llAddTips);
        for(int i = 0; i < healthTipsAdapter.getGroupCount(); i++){
            elvHealthTips.expandGroup(i);
        }
    }

    private void bindEvent() {
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
                        checkSycleList.remove(position);
                        planCheckSycleAdapter.notifyDataSetChanged();
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
                healthTipsAdapter.notifyDataSetChanged();
                for(int i = 0; i < healthTipsAdapter.getGroupCount(); i++){
                    elvHealthTips.expandGroup(i);
                }
                break;
            case R.id.llAddMedicine:
                medicineList.add(new Medicine());
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 1) {
                List<String> list = data.getStringArrayListExtra("list");
                selfScaleList.clear();
                selfScaleList.addAll(list);
                selfScaleAdapter.notifyDataSetChanged();
            } else if(resultCode == 2) {
                List<String> list = data.getStringArrayListExtra("list");
                doctorScaleList.clear();
                doctorScaleList.addAll(list);
                doctorScaleAdapter.notifyDataSetChanged();
            }
        }
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
