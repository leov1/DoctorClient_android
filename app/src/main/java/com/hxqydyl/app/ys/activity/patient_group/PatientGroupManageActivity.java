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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.http.PatientGroupNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Collection;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

public class PatientGroupManageActivity extends BaseTitleActivity implements View.OnClickListener ,DialogUtils.SavePatientGroupListener{
    public static final String GROUPS_INFO_KEY = "groups_info";
    private RelativeLayout rlAddPatientGroup;
    private SwipeMenuListView lvPatientGroup;
    private PatientGroupAdapter patientGroupAdapter;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
    private Dialog addPatientGroupDialog;
    private PatientGroupNet patientGroupNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_group_manage);

//        初始化标题栏
        initViewOnBaseTitle(getString(R.string.patient_group_manage));
        setBackListener(this);
//        添加按钮
        rlAddPatientGroup = (RelativeLayout) findViewById(R.id.rlAddPatientGroup);
        rlAddPatientGroup.setOnClickListener(this);
//        分组列表
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
                if(patientGroup!=null) {
                    patientGroupNet.deletePatientGroup(patientGroup.getId());
                }
                return false;
            }
        });
        lvPatientGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PatientGroup patientGroup = (PatientGroup) parent.getItemAtPosition(position);
//                if(patientGroup!=null) {
//                    patientGroupNet.renamePatientGroup(patientGroup.getId(),"new name");
//                }
                showInputDialog();
            }
        });
        initListData();
    }

    private void initListData() {
        ArrayList<PatientGroup> groups = (ArrayList<PatientGroup>) getIntent().getSerializableExtra(GROUPS_INFO_KEY);
        if (groups != null && groups.size() > 0) {
            patientGroupArrayList.addAll(groups);
            patientGroupAdapter.notifyDataSetChanged();
        }else{
            patientGroupNet = new PatientGroupNet(this);
            patientGroupNet.getPatientGroups("d000688d038b476384a408c17ad25faa");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAddPatientGroup:
                showInputDialog();
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }

    private void showInputDialog() {
        DialogUtils.showAddPatientGroupDialog(this,this);
    }


    @Override
    public void onSaveGroup(String groupName) {
        patientGroupNet.addPatientGroup("d000688d038b476384a408c17ad25faa",groupName);
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.GET_ALL_PATIENT_GROUP)) {
            patientGroupArrayList.clear();
            patientGroupArrayList.addAll((ArrayList<PatientGroup>) result);
            patientGroupAdapter.notifyDataSetChanged();
        } else {
            patientGroupNet.getPatientGroups("d000688d038b476384a408c17ad25faa");
        }
    }
}
