package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.MedicineDosageEditAdapter;
import com.hxqydyl.app.ys.adapter.MedicineEditAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdvice;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.xus.http.httplib.model.PostPrams;

import java.util.ArrayList;

/**
 * Created by wangchao36 on 16/3/22.
 * 编辑医嘱
 */
public class PatientAdviceEditActivity extends BaseRequstActivity implements View.OnClickListener {

    private EditText etDrugTherapy; // 不良反应
    private EditText etSideEffects; //其他治疗
    private LinearLayout llAddMedicine;  //   添加其他药品
    private ListView lvMedicine;
    private MedicineEditAdapter medicineAdapter;
    private ArrayList<ImportantAdviceChild> medicineList;        // 药品列表
    private Button btnSave;
    private ImportantAdvice advice = null;
    private String customerUuid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_advice_edit);
        Intent intent = getIntent();
        customerUuid = intent.getStringExtra("customerUuid");
        advice = (ImportantAdvice) intent.getSerializableExtra("advice");
        initViewOnBaseTitle("重要医嘱");
        initView();
        bindEvent();
        updateUIData();
    }

    private void initView() {
        etDrugTherapy = (EditText) findViewById(R.id.etDrugTherapy);
        etSideEffects = (EditText) findViewById(R.id.etSideEffects);
        llAddMedicine = (LinearLayout) findViewById(R.id.llAddMedicine);
        lvMedicine = (ListView) findViewById(R.id.lvMedicine);
        medicineList = new ArrayList<>();
        medicineList.add(new ImportantAdviceChild());
        medicineAdapter = new MedicineEditAdapter(this, medicineList, lvMedicine);
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
                medicineList.add(new ImportantAdviceChild());
                medicineAdapter.notifyDataSetChanged();
                break;
            case R.id.btnSave:
                saveInfo();
                break;
        }
    }

    private void saveInfo() {

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

        ArrayList<ImportantAdviceChild> mList = new ArrayList<>();
        for (int i = 0; i < lvMedicine.getChildCount(); i++) {
            MedicineEditAdapter.ViewHolder vh = (MedicineEditAdapter.ViewHolder) lvMedicine.getChildAt(i).getTag();
            ImportantAdviceChild m = new ImportantAdviceChild();
            m.setMedicineUuid(vh.etName.getText().toString());
            m.setFood(vh.tvFoodRelation.getText().toString());
            m.setTimeNoon(vh.boolTimeNoon);
            m.setTimeNight(vh.boolTimeNight);
            m.setTimeMorning(vh.boolTimeMorning);

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

        advice.setDrugReaction(drugTherapy);
        advice.setCureNote(sideEffects);

        advice.setChild(mList);
        advice.setCustomerUuid(customerUuid);
        advice.setVisitPreceptUuid(advice.getUuid());
        advice.setServiceStaffUuid(LoginManager.getDoctorUuid());
        Gson myJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = myJson.toJson(advice.toJsonBean());
        PostPrams postPrams = new PostPrams();
        postPrams.setPostStringPrams(json);
        String url = UrlConstants.getWholeApiUrl(UrlConstants.GET_ADVICE_SAVE, "1.0");
        toNomalNet(postPrams, BaseResponse.class, 1, url, "正在保存...");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        BaseResponse br = (BaseResponse) bean;
        if (br.message!=null&&br.message.contains("成功")) {
            UIHelper.ToastMessage(PatientAdviceEditActivity.this, "保持成功");
            finish();
        }
    }

    private void updateUIData() {
        if (advice == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
            return;
        }
        etDrugTherapy.setText(advice.getDrugReaction());
        etSideEffects.setText(advice.getCureNote());
        medicineList.clear();
        medicineList.addAll(advice.getChild());
        medicineAdapter.notifyDataSetChanged();
    }
}
