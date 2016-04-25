package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.MedicineAdapter;
import com.hxqydyl.app.ys.bean.Advice;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdvice;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.response.ImportantAdviceResponse;
import com.hxqydyl.app.ys.http.PatientAdviceNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.xus.http.httplib.model.GetParams;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 医嘱
 */
public class PatientAdviceInfoActivity extends BaseRequstActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvDrugTherapy; // 不良反应
    private TextView tvSideEffects; //其他治疗

    private ListView lvMedicine;
    private MedicineAdapter medicineAdapter;
    private List<ImportantAdviceChild> medicineList;        // 药品列表

    private String customerUuid = null;    //患者uuid
    //    private Advice advice = null;
    private ImportantAdvice advice = null;
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
        GetParams getParams = new GetParams();
        getParams.addHeader("Accept", "application/json");
//        String uri="http://172.168.1.53/mobile/doctor/visit/advice/2.0/search/bbeee6220c404708a9c23eb7e5122f27/9ee56d1310b54baa97f5a8abbe85a0b1";
//        toNomalNet(getParams, ImportantAdviceResponse.class, 1, uri, "正在获取医嘱");

        toNomalNetStringBack(getParams, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_ADVICE_SEARCH, "1.0", LoginManager.getDoctorUuid(), visitUuid), "正在获取医嘱");

//        PatientAdviceNet.adviceSearch(visitUuid, new FollowCallback(this) {
//            @Override
//            public void onResponse(String response) {
//                if (FollowApplyNet.myDev)
//                    response = "{" +
//                            "  \"drugReaction\" : \"你\"," +
//                            "  \"cureNote\" : \"你\"," +
//                            "  \"child\" : [" +
//                            "    {" +
//                            "      \"medicineUuid\" : \"\"," +
//                            "      \"directions\" : \"早\"," +
//                            "      \"dosage\" : \"5|mg\"," +
//                            "      \"frequency\" : \"1\"," +
//                            "      \"unit\" : \"\"," +
//                            "      \"food\" : \"饭前服用\"" +
//                            "    }" +
//                            "  ]," +
//                            "  \"uuid\" : \"1ff22cc9fe064bc9a1a7afb7b1e24619\"" +
//                            "}";
//                advice = Advice.parseDetailJson(response);
//                dismissDialog();
//                handler.sendEmptyMessage(100);
//            }
//
//            @Override
//            public void onError(Call call, Exception e) {
//                super.onError(call, e);
//                UIHelper.ToastMessage(PatientAdviceInfoActivity.this, "数据加载失败");
//                dismissDialog();
//                onResponse("t");
//            }
//        });
    }

    @Override
    public void onSuccessToString(String json, int flag) {
        advice = gson.fromJson(json, ImportantAdvice.class);
        handler.sendEmptyMessage(100);
    }

    private void updateUIData() {
        if (advice == null) {
            UIHelper.ToastMessage(this, "数据加载失败");
            return;
        }
        tvDrugTherapy.setText(advice.getDrugReaction());
        tvSideEffects.setText(advice.getCureNote());

        medicineList.clear();
        medicineList.addAll(advice.getChild());

        medicineAdapter.notifyDataSetChanged();
    }
}
