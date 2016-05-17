package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.bean.AddressBook;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.CustomerNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/21.
 * 添加患者
 */
public class PatientAddActivity extends BaseRequstActivity implements View.OnClickListener {

    @InjectId(id = R.id.ibAddressBook)
    private ImageView ibAddressBook;
    @InjectId(id = R.id.etPhone)
    private EditText etPhone;
    @InjectId(id = R.id.etRealName)
    private EditText etRealName;
    @InjectId(id = R.id.etDiagnosis)
    private EditText etDiagnosis;
    @InjectId(id = R.id.tvGroupName)
    private TextView tvGroupName;
    @InjectId(id = R.id.btnAdd)
    private Button btnAdd;

    private PatientGroupNet patientGroupNet;

    private List<PatientGroup> pgList = null;
    private String[] groupItem = null;
    private String selectGroupId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);
        InjectUtils.injectView(this);
        initViewOnBaseTitle(getResources().getString(R.string.patient_add_title));
        setBackListener();

        ibAddressBook.setOnClickListener(this);
        tvGroupName.setOnClickListener(this);
        btnAdd.setOnClickListener(this);


        patientGroupNet = new PatientGroupNet(this);
        patientGroupNet.getPatientGroups(LoginManager.getDoctorUuid());
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
        dialog.title("请选择分组")
                .titleBgColor(getResources().getColor(R.color.color_home_topbar))
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0 && data != null) {
            AddressBook ab = (AddressBook) data.getSerializableExtra("ab");
            etPhone.setText(ab.getPhone());
            etRealName.setText(ab.getName());
            showDialog("");
            CustomerNet.getCustomerByMobile(ab.getPhone(), new FollowCallback(this) {
                @Override
                public void onResponse(String response) {
                    if (StringUtils.isEmpty(response)) {
                        UIHelper.ToastMessage(PatientAddActivity.this, "没有数据");
                        return;
                    }
                    try {
                        JSONObject object = JSONObject.parseObject(response);
                        JSONObject queryObj = object.getJSONObject("query");
                        String status = queryObj.getString("success");
                        if ("1".equals(status)) {
                            String name = object.getString("name");
                            etRealName.setText(name);
                        } else {
                            String msg = queryObj.getString("message");
                            Log.e("doctorClient", msg);
                            UIHelper.ToastMessage(PatientAddActivity.this, msg);
                            PatientAddActivity.this.dismissDialog();
                            onFail(status, msg);
                        }
                    } catch (Exception e) {
                        onFail("999999", "解析出错啦，重新刷新下吧");
                    }

                }

                @Override
                public void onFail(String status, String msg) {
                    super.onFail(status, msg);
                    dismissDialog();
                    UIHelper.ToastMessage(PatientAddActivity.this, msg);
                }
            });
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.GET_ALL_PATIENT_GROUP)) {
            pgList = (ArrayList<PatientGroup>) result;
            groupItem = new String[pgList.size()];
            for (int i = 0; i < pgList.size(); i++) {
                groupItem[i] = pgList.get(i).getGroupName();
            }
        }
    }

    public void savePatient() {
        final String mobile = etPhone.getText().toString();
        final String name = etRealName.getText().toString();
        final String diagnosis = etDiagnosis.getText().toString();

        if (selectGroupId == null) {
            Toast.makeText(PatientAddActivity.this, "请选择分组", Toast.LENGTH_SHORT).show();
            return;
        }
        CustomerNet.getCustomerByMobile(mobile, new FollowCallback(this) {
            @Override
            public void onResult(String result) {
                super.onResult(result);
                CustomerNet.addCustomer(name, mobile, selectGroupId, diagnosis,
                        new FollowCallback(PatientAddActivity.this) {
                            @Override
                            public void onResult(String result) {
                                Toast.makeText(PatientAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
            }

            @Override
            public void onFail(String status, String msg) {
                super.onFail(status, msg);
                dismissDialog();
                Toast.makeText(PatientAddActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
