package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.HealthTipsAdapter;
import com.hxqydyl.app.ys.adapter.MedicineAdapter;
import com.hxqydyl.app.ys.adapter.PlanSelfScaleAdapter;
import com.hxqydyl.app.ys.bean.plan.HealthTips;
import com.hxqydyl.app.ys.bean.plan.Medicine;

import java.util.ArrayList;
import java.util.List;

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

    private TextView tvAddMedicine;  //   添加其他药品
    private TextView tvSelfScale;  //   添加自评量表
    private TextView tvDoctorScale;  // 添加医评量表

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> medicineList;

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

    final String[] cycleItem1 = {"1周", "2周", "3周", "4周"};
    final String[] cycleItem2 = {"1周", "2周", "4周", "8周"};

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

        tvAddMedicine = (TextView) findViewById(R.id.tvAddMedicine);
        tvSelfScale = (TextView) findViewById(R.id.tvSelfScale);
        tvDoctorScale = (TextView) findViewById(R.id.tvDoctorScale);

        lvMedicine = (ListView) findViewById(R.id.lvMedicine);
        lvSelfScale = (ListView) findViewById(R.id.lvSelfScale);
        lvDoctorScale = (ListView) findViewById(R.id.lvDoctorScale);

        medicineList = new ArrayList<>();
        medicineList.add(new Medicine());
        medicineAdapter = new MedicineAdapter(this, medicineList);
        lvMedicine.setAdapter(medicineAdapter);

        selfScaleList = new ArrayList<>();
        selfScaleAdapter = new PlanSelfScaleAdapter(this, selfScaleList);
        lvSelfScale.setAdapter(selfScaleAdapter);
        doctorScaleList = new ArrayList<>();
        doctorScaleAdapter = new PlanSelfScaleAdapter(this, doctorScaleList);
        lvDoctorScale.setAdapter(doctorScaleAdapter);

        elvHealthTips = (ExpandableListView) findViewById(R.id.elvHealthTips);
        healthTipsList = new ArrayList<>();
        healthTipsList.add(new HealthTips());
        healthTipsAdapter = new HealthTipsAdapter(this, healthTipsList);
        elvHealthTips.setAdapter(healthTipsAdapter);
        llAddTips = (LinearLayout) findViewById(R.id.llAddTips);
    }

    private void bindEvent() {
        tvFollowCycle.setOnClickListener(this);
        tvWeightCycle.setOnClickListener(this);
        tvEcgCycle.setOnClickListener(this);
        tvBloodCycle.setOnClickListener(this);
        tvLiverCycle.setOnClickListener(this);
        tvAddMedicine.setOnClickListener(this);
        tvSelfScale.setOnClickListener(this);
        tvDoctorScale.setOnClickListener(this);
        llAddTips.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFollowCycle:
                cycleDialog((TextView) v, cycleItem1);
                break;
            case R.id.tvWeightCycle:
                cycleDialog((TextView) v, cycleItem1);
                break;
            case R.id.tvEcgCycle:
                cycleDialog((TextView) v, cycleItem2);
                break;
            case R.id.tvBloodCycle:
                cycleDialog((TextView) v, cycleItem2);
                break;
            case R.id.tvLiverCycle:
                cycleDialog((TextView) v, cycleItem2);
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
                break;
            case R.id.tvAddMedicine:
                medicineList.add(new Medicine());
                medicineAdapter.notifyDataSetChanged();
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

    /**
     * 弹窗，选择周期
     * @param tv
     * @param items
     */
    private void cycleDialog(final TextView tv, final String[] items) {
        final NormalListDialog dialog = new NormalListDialog(this, items);
        dialog.title("请选择周期")
                .titleBgColor(getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(items[position]);
                dialog.dismiss();
            }
        });
    }
}
