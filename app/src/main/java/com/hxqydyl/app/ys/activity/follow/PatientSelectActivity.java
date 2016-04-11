package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientSelectAdapter;
import com.hxqydyl.app.ys.bean.article.ArticleResult;
import com.hxqydyl.app.ys.bean.article.Child;
import com.hxqydyl.app.ys.bean.article.Group;
import com.hxqydyl.app.ys.http.sendMsg.PatientGroupNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.fullshowview.FullShowExpandableListView;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 挑选患者页面
 */
public class PatientSelectActivity extends BaseTitleActivity implements View.OnClickListener, PatientGroupNet.OnPatientGroupListener {

    private ExpandableListView expand_lv;
    private View mHeader;
    private View mFooter;
    private CheckBox checkbox_all;
    private Button btn_send_patient;
    private TextView text_head;
    private PatientSelectAdapter adapter;
    private ArrayList<Group> groups = new ArrayList<>();

    private PatientGroupNet patientGroupNet;

    private ArrayList<String> strsUuid = new ArrayList<>();
    private ArrayList<String> strName = new ArrayList<>();

    private Intent intent;
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_select);

        initHeadView();
        initViews();
        initListeners();
        getDatas();
    }

    private void initListeners() {
        btn_send_patient.setOnClickListener(this);
    }

    private void initHeadView() {
        patientGroupNet = new PatientGroupNet();
        patientGroupNet.setListener(this);

        mHeader = View.inflate(this, R.layout.head_textview, null);
        text_head = (TextView) mHeader.findViewById(R.id.text_head);
        mFooter = View.inflate(this, R.layout.foot_patient_layout, null);
        checkbox_all = (CheckBox) mFooter.findViewById(R.id.checkbox_all);
        btn_send_patient = (Button) mFooter.findViewById(R.id.btn_send_patient);
    }

    private void initViews() {
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
        System.out.println("groups---->" + groups.toString());
        adapter = new PatientSelectAdapter(this, groups, checkbox_all);
        expand_lv.setGroupIndicator(null);
        expand_lv.addHeaderView(mHeader);
        expand_lv.addFooterView(mFooter);
        expand_lv.setAdapter(adapter);
    }

    /**
     * 获取网络数据
     */
    private void getDatas() {
        patientGroupNet.getPatientGroup(LoginManager.getDoctorUuid());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_patient:
                selectPatients();
                if (strsUuid.size() == 0) {
                    UIHelper.ToastMessage(PatientSelectActivity.this, "请选择患者");
                    return;
                }

                intent = new Intent(PatientSelectActivity.this, MassActivity.class);
                intent.putExtra("customerUuids", StringUtils.listToString(strsUuid, ','));
                intent.putExtra("customerNames", StringUtils.listToString(strName, ','));

                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    /**
     * 将选择的人放在一起
     *
     * @return
     */
    private void selectPatients() {
        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            for (int j = 0; j < group.getChildrenCount(); j++) {
                Child child = group.getChildItem(j);
                if (child.getChecked()) {
                    strName.add(child.getCustomerName());
                    strsUuid.add(child.getCustomerUuid());
                }
            }
        }
    }

    @Override
    public void patientGroupSuc(ArticleResult articleResult) {
        if (articleResult == null) return;
        if (articleResult.getQuery().getSuccess().equals("1")) {
            groups = articleResult.getGroups();
            System.out.println("group--->" + groups.toString());
            adapter.updataUI(groups);
            return;
        }
        UIHelper.ToastMessage(PatientSelectActivity.this, "请求出错");
    }

    @Override
    public void patientGroupFail() {
        UIHelper.ToastMessage(PatientSelectActivity.this, "请求出错");
    }
}
