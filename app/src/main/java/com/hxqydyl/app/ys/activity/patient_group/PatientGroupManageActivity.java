package com.hxqydyl.app.ys.activity.patient_group;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.Collection;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

public class PatientGroupManageActivity extends BaseRequstActivity implements View.OnClickListener, DialogUtils.SavePatientGroupListener, DialogUtils.RenameGroupListener {
    public static final String GROUPS_INFO_KEY = "groups_info";
    private RelativeLayout rlAddPatientGroup;
    private SwipeMenuListView lvPatientGroup;
    private PatientGroupAdapter patientGroupAdapter;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
//    private PatientGroupNet patientGroupNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_group_manage);
        initViewOnBaseTitle(getString(R.string.patient_group_manage));
        setBackListener(this);
        rlAddPatientGroup = (RelativeLayout) findViewById(R.id.rlAddPatientGroup);
        lvPatientGroup = (SwipeMenuListView) findViewById(R.id.lvPatientGroup);
        patientGroupAdapter = new PatientGroupAdapter(this, patientGroupArrayList);
        lvPatientGroup.setAdapter(patientGroupAdapter);
        lvPatientGroup.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem menuItem = new SwipeMenuItem(PatientGroupManageActivity.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.color_2080ba, null)));
                } else {
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.color_2080ba)));
                }
                menuItem.setIcon(R.mipmap.ic_delete_patient_group);
                menuItem.setWidth(DensityUtils.dp2px(PatientGroupManageActivity.this, 50));
                menu.addMenuItem(menuItem);
            }
        });
        lvPatientGroup.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                PatientGroup patientGroup = (PatientGroup) patientGroupAdapter.getItem(position);
                if (patientGroup != null) {
                    delectGroups(patientGroup.getGroupId());
                }
                return false;
            }
        });
        lvPatientGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientGroup patientGroup = (PatientGroup) parent.getItemAtPosition(position);
                DialogUtils.showRenamePatientGroupDialog(PatientGroupManageActivity.this, PatientGroupManageActivity.this, patientGroup.getGroupId(),patientGroup.getGroupName());
            }
        });
        rlAddPatientGroup.setOnClickListener(this);

        initListData();
    }

    private void initListData() {
        ArrayList<PatientGroup> groups = (ArrayList<PatientGroup>) getIntent().getSerializableExtra(GROUPS_INFO_KEY);
        if (groups != null && groups.size() > 0) {
            patientGroupArrayList.addAll(groups);
            patientGroupAdapter.notifyDataSetChanged();
        } else {
            getGroups();
        }
    }

    //获取群组列表
    private void getGroups() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_GROUP, "1.0"), "正在获取群组列表");

    }

    private void delectGroups(String groupID) {
        toNomalNet(toPostParams(toParamsBaen("groupId", groupID)), BaseResponse.class, 4, UrlConstants.getWholeApiUrl(UrlConstants.DELETE_PATIENT_GROUP, "1.0"), "正在删除");

    }

    private void addGroups(String groupName) {
        toNomalNet(toPostParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("groupName", groupName)), BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.ADD_PATIENT_GROUP, "1.0"), "正在添加");
        //    patientGroupNet.addPatientGroup(LoginManager.getDoctorUuid(), groupName);
    }

    private void updateGroups(String groupId, String groupName) {
        toNomalNet(toPostParams(toParamsBaen("groupId", groupId), toParamsBaen("groupName", groupName)), BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.RENAME_PATIENT_GROUP, "1.0"), "正在修改");
        //        patientGroupNet.renamePatientGroup(groupId,groupName);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAddPatientGroup:
                DialogUtils.showAddPatientGroupDialog(this, this);
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onSaveGroup(String groupName) {
        addGroups(groupName);
    }

    @Override
    public void onConfirmRename(String groupId, String groupName) {
        updateGroups(groupId, groupName);
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                UIHelper.ToastMessage(this, "添加成功");
                getGroups();
                break;
            case 2:
                UIHelper.ToastMessage(this, "修改成功");

                getGroups();
                setResult(RESULT_OK);
                break;
            case 3:
                PatientGroupResponse pgr = (PatientGroupResponse) bean;
                if (pgr.getRelist() != null && pgr.getRelist().size() > 0) {
                    patientGroupArrayList.clear();
                    patientGroupArrayList.addAll(pgr.getRelist());
                    patientGroupAdapter.notifyDataSetChanged();
                }
                break;
            case 4:
                UIHelper.ToastMessage(this, "删除成功");

                getGroups();
                setResult(RESULT_OK);
                break;
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.GET_ALL_PATIENT_GROUP)) {
            patientGroupArrayList.clear();
            patientGroupArrayList.addAll((ArrayList<PatientGroup>) result);
            patientGroupAdapter.notifyDataSetChanged();
        } else {
            getGroups();
            setResult(RESULT_OK);
        }
    }


}
