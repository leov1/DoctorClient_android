package com.hxqydyl.app.ys.activity.patient_group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupSelectAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientGroupSelectActivity extends BaseRequstActivity implements View.OnClickListener
        , DialogUtils.SavePatientGroupListener {
    @InjectId(id = R.id.lvPatientGroup)
    private ListView lvPatientGroup;
    @InjectId(id = R.id.right_txt_btn)
    private TextView queryBtn;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
    private PatientGroupSelectAdapter patientGroupSelectAdapter;

    @InjectId(id = R.id.bAddPatientGroup)
    private Button bAddPatientGroup;

    public static void newIntent(Activity activity,ArrayList<PatientGroup> patientGroups,int requestCode){
        Intent intent = new Intent(activity, PatientGroupSelectActivity.class);
        intent.putExtra(PatientGroupManageActivity.GROUPS_INFO_KEY, patientGroups);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_group_select);
        InjectUtils.injectView(this);

        initViewOnBaseTitle(getString(R.string.select_group));
        queryBtn.setVisibility(View.VISIBLE);
        queryBtn.setText("确认");
        setBackListener(this);
        queryBtn.setOnClickListener(this);

        patientGroupSelectAdapter = new PatientGroupSelectAdapter(this, patientGroupArrayList);
        lvPatientGroup.setAdapter(patientGroupSelectAdapter);
        lvPatientGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientGroupSelectAdapter.setSelect(position);
            }
        });

        bAddPatientGroup.setOnClickListener(this);
        initListData();
    }

    private void initListData() {
        ArrayList<PatientGroup> groups = (ArrayList<PatientGroup>) getIntent().getSerializableExtra(PatientGroupManageActivity.GROUPS_INFO_KEY);
        if (groups != null && groups.size() > 0) {
            patientGroupArrayList.addAll(groups);
            patientGroupSelectAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                this.finish();
                break;
            case R.id.bAddPatientGroup:
                showInputDialog();
                break;
            case R.id.right_txt_btn:
                int groupSelcect = patientGroupSelectAdapter.getSelect();
                if (-1 != groupSelcect){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(PatientGroupManageActivity.GROUPS_INFO_KEY, patientGroupArrayList.get(groupSelcect));
                    setResult(RESULT_OK, resultIntent);
                }else {
                    setResult(RESULT_CANCELED);
                }
                finish();
                break;
        }
    }

    //获取群组列表
    private void getGroups() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_GROUP, "1.0"), "正在获取群组列表");
    }

    private void addGroups(String groupName) {
        toNomalNet(toPostParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("groupName", groupName)), BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.ADD_PATIENT_GROUP, "1.0"), "正在添加");
    }

    private void showInputDialog() {
        DialogUtils.showAddPatientGroupDialog(this, this);
    }


    @Override
    public void onSaveGroup(String groupName) {
        addGroups(groupName);
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                UIHelper.ToastMessage(this, "添加成功");
                getGroups();
                setResult(RESULT_OK);
                break;
            case 3:
                PatientGroupResponse pgr = (PatientGroupResponse) bean;
                if (pgr.getRelist() != null) {
                    patientGroupArrayList.clear();
                    patientGroupArrayList.addAll(pgr.getRelist());
                    patientGroupSelectAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
