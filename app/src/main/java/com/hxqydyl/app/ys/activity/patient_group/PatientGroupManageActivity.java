package com.hxqydyl.app.ys.activity.patient_group;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.PatientGroupResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

public class PatientGroupManageActivity extends BaseRequstActivity implements View.OnClickListener
        , DialogUtils.SavePatientGroupListener
        , DialogUtils.RenameGroupListener
        , DialogUtils.DeletePatientGroupListener {
    public static final String GROUPS_INFO_KEY = "groups_info";
    private RelativeLayout rlAddPatientGroup;
    private SwipeMenuListView lvPatientGroup;
    private PatientGroupAdapter patientGroupAdapter;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
    private ArrayList<PatientGroup> groups;

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
        lvPatientGroup.setFocusable(false);
        lvPatientGroup.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem menuItem = new SwipeMenuItem(PatientGroupManageActivity.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.color_fff26c4f, null)));
                } else {
                    menuItem.setBackground(new ColorDrawable(getResources().getColor(R.color.color_fff26c4f)));
                }
     //           menuItem.setIcon(R.mipmap.ic_delete_patient_group);
                // set item width
                menuItem.setWidth(DensityUtils.dp2px(PatientGroupManageActivity.this,90));
                // set item title
                menuItem.setTitle("删除");
                // set item title fontsize
                menuItem.setTitleSize(18);
                // set item title font color
                menuItem.setTitleColor(Color.WHITE);
                menuItem.setWidth(DensityUtils.dp2px(PatientGroupManageActivity.this, 60));
                menu.addMenuItem(menuItem);
            }
        });
        lvPatientGroup.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                PatientGroup patientGroup = (PatientGroup) patientGroupAdapter.getItem(position);
                if (patientGroup != null) {
                    String id = patientGroup.getGroupId();
                    if (judgeHasItem(id)) {
                        DialogUtils.showDeletePatientGroupDialog(PatientGroupManageActivity.this, PatientGroupManageActivity.this, id);
                    } else {
                        delectGroups(patientGroup.getGroupId());
                    }
                }
                return false;
            }
        });
        lvPatientGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientGroup patientGroup = (PatientGroup) parent.getItemAtPosition(position);
                DialogUtils.showRenamePatientGroupDialog(PatientGroupManageActivity.this, PatientGroupManageActivity.this, patientGroup.getGroupId(), patientGroup.getGroupName());
            }
        });
        rlAddPatientGroup.setOnClickListener(this);

        initListData();
    }

    //判断分组中是否有患者
    private boolean judgeHasItem(String groupId) {
        if (groups != null && groups.size() != 0) {
            for (PatientGroup group : groups) {
                if (group.getGroupId().equals(groupId) && group.isHasPatient())
                    return true;
            }
        }
        return false;
    }

    private void initListData() {
        groups = (ArrayList<PatientGroup>) getIntent().getSerializableExtra(GROUPS_INFO_KEY);
        if (groups != null && groups.size() > 0) {
            groups.remove(0);
            patientGroupArrayList.addAll(groups);
            patientGroupAdapter.notifyDataSetChanged();
        }
    }

    //获取群组列表
    private void getGroups() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PatientGroupResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_ALL_PATIENT_GROUP, "1.0"), "正在获取群组列表");
    }

    private void delectGroups(String groupId) {
        toNomalNet(toPostParams(toParamsBaen("groupId", groupId)), BaseResponse.class, 4, UrlConstants.getWholeApiUrl(UrlConstants.DELETE_PATIENT_GROUP, "1.0"), "正在删除");
    }

    private void addGroups(String groupName) {
        toNomalNet(toPostParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("groupName", groupName)), BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.ADD_PATIENT_GROUP, "1.0"), "正在添加");
    }

    private void updateGroups(String groupId, String groupName) {
        toNomalNet(toPostParams(toParamsBaen("groupId", groupId), toParamsBaen("groupName", groupName)), BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.RENAME_PATIENT_GROUP, "1.0"), "正在修改");
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
    public void onDeleteGroup(String groupId) {
        delectGroups(groupId);
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                requestResult("添加成功");
                break;
            case 2:
                requestResult("修改成功");
                break;
            case 3:
                PatientGroupResponse pgr = (PatientGroupResponse) bean;
                if (pgr.getRelist() != null) {
                    patientGroupArrayList.clear();
                    patientGroupArrayList.addAll(pgr.getRelist());
                    patientGroupAdapter.notifyDataSetChanged();
                }
                break;
            case 4:
                requestResult("删除成功");
                break;
        }
    }

    /**
     * 添加，修改，删除成功之后的操作
     *
     * @param str
     */
    private void requestResult(String str) {
        UIHelper.ToastMessage(this, str);
        getGroups();
        setResult(RESULT_OK);
    }

}
