package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.bean.AddressBook;
import com.hxqydyl.app.ys.bean.NewPatient;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.bean.response.SelectMobileResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wangchao36 on 16/3/21.
 * 添加患者
 */
public class PatientAddActivity extends BaseRequstActivity implements View.OnClickListener, TextWatcher, SweetAlertDialog.OnSweetClickListener {

    private ImageView ibAddressBook;//通讯录
    private EditText etPhone;//电话输入框
    private EditText etRealName;//真实姓名输入框
    private EditText etDiagnosis;//患者病情描述输入框
    private TextView tvGroupName;//群组展示
    private Button btnAdd;//保存患者
    private List<PatientGroup> pgList = null;//群组
    private String[] groupItem = null;//群组名称
    private String selectGroupId = "0";//群组id，默认0，临时分组-1
    private Patient patients;
    private String id;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);
        init();
        initListener();
        getGroups();
    }

    private void init() {
        initViewOnBaseTitle(getResources().getString(R.string.patient_add_title));
        ibAddressBook = (ImageView) findViewById(R.id.ibAddressBook);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etRealName = (EditText) findViewById(R.id.etRealName);
        etDiagnosis = (EditText) findViewById(R.id.etDiagnosis);
        tvGroupName = (TextView) findViewById(R.id.tvGroupName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        tvGroupName.setText("默认分组");
    }

    private void initListener() {
        setBackListener();
        ibAddressBook.setOnClickListener(this);
        tvGroupName.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        etPhone.addTextChangedListener(this);
        tvGroupName.setEnabled(false);
    }

    //验证用户手机
    public void getCustomerByMobile(String mobile) {
        if (mobile.length() >= 11) {
            toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("mobile", mobile)), SelectMobileResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_CUSTOMER_BY_MOBILE, "2.0"), "正在验证手机号");
        }
    }

    //添加随访关系
    public void addCustomer(String name, String mobile, String groupId,
                            String illnessDescription) {

        if (TextUtils.isEmpty(name)) {
            UIHelper.ToastMessage(this, "请填写患者姓名!");
        }
        if (TextUtils.isEmpty(mobile)) {
            UIHelper.ToastMessage(this, "请填写患者手机号!");
        }
        if (mobile.length() < 11) {
            UIHelper.ToastMessage(this, "请填写正确手机号！!");

        }
        if (TextUtils.isEmpty(illnessDescription)) {
            UIHelper.ToastMessage(this, "请填写患者病情描述!");
        }
        toNomalNet(toPostParams(
                toParamsBaen("id", id),
                toParamsBaen("uuid", TextUtils.isEmpty(uuid) ? "" : uuid),
                toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()),
                toParamsBaen("name", name),
                toParamsBaen("mobile", mobile),
                toParamsBaen("groupId", groupId),
                toParamsBaen("illnessDescription", illnessDescription)),
                BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.ADD_CUSTOMER, "2.0"), "正在添加患者");
    }

    //获取群组列表
    private void getGroups() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_GROUP, "1.0"), "正在获取群组列表");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddressBook:
                Intent intent = new Intent(this, AddressBookSelectActivity.class);
                this.startActivityForResult(intent, 0);
                break;
            case R.id.tvGroupName:
                groupDialog((TextView) v, groupItem);
                break;
            case R.id.btnAdd:
                savePatient();
                break;
        }
    }

    private void groupDialog(final TextView tv, final String[] items) {
        final NormalListDialog dialog = new NormalListDialog(this, items);
        dialog.title("请选择分组").titleBgColor(ContextCompat.getColor(this, R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(items[position]);
                dialog.dismiss();
                selectGroupId = pgList.get(position).getId();
            }
        });
    }

    public void savePatient() {
        final String mobile = etPhone.getText().toString();
        final String name = etRealName.getText().toString();
        final String diagnosis = etDiagnosis.getText().toString();
        addCustomer(name, mobile, selectGroupId, diagnosis);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0 && data != null) {
            AddressBook ab = (AddressBook) data.getSerializableExtra("ab");
            etPhone.setText(ab.getPhone());
            getCustomerByMobile(ab.getPhone());
        }
    }


    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                SelectMobileResponse smr = (SelectMobileResponse) bean;
                id = smr.value.getId() + "";
                switch (smr.value.getId()) {
                    case 1:
                        DialogUtils.showNormalDialog(this, "提示", smr.value.getMsg(), this);
                        patients = smr.value.getData();
                        btnAdd.setEnabled(false);
                        break;
                    case 2:
                        etRealName.setText(smr.value.getData().getCustomerName());
                        tvGroupName.setEnabled(true);
                        tvGroupName.setText("默认分组");
                        selectGroupId = "0";
                        uuid = smr.value.getData().getCustomerUuid();
                        btnAdd.setEnabled(true);

                        break;
                    case 3:
                        tvGroupName.setEnabled(false);
                        tvGroupName.setText("临时分组");
                        selectGroupId = "-1";
                        btnAdd.setEnabled(true);

                        break;
                    case 4:
                        UIHelper.ToastMessage(this, smr.value.getMsg());
                        btnAdd.setEnabled(false);
                        break;
                }
                break;
            case 2:
                UIHelper.ToastMessage(this, "添加成功");
                setResult(RESULT_OK);
                finish();
                break;
            case 3:
                PatientGroupResponse pgr = (PatientGroupResponse) bean;
                pgList = new ArrayList<>();
                PatientGroup patientGroup = new PatientGroup();
                patientGroup.setGroupId("0");
                patientGroup.setGroupName("默认分组");
                pgList.add(0, patientGroup);
                if (pgr.getRelist() != null && pgr.getRelist().size() > 0) {
                    pgList.addAll(pgr.getRelist());
                }
                groupItem = new String[pgList.size()];
                for (int i = 0; i < pgList.size(); i++) {
                    groupItem[i] = pgList.get(i).getGroupName();
                }
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getCustomerByMobile(etPhone.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    @Override
    public void onClick(SweetAlertDialog sweetAlertDialog) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);

        intent.putExtra(PatientDetailsActivity.KEY_PATIENT, patients);
        startActivity(intent);
        this.finish();
    }
}
