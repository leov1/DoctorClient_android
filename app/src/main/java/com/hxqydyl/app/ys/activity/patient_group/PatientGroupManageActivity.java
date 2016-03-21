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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientGroupAdapter;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;

import java.util.ArrayList;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

public class PatientGroupManageActivity extends BaseTitleActivity implements View.OnClickListener ,DialogUtils.SavePatientGroupListener{
    private RelativeLayout rlAddPatientGroup;
    private SwipeMenuListView lvPatientGroup;
    private PatientGroupAdapter patientGroupAdapter;
    private ArrayList<PatientGroup> patientGroupArrayList = new ArrayList<PatientGroup>();
    private Dialog addPatientGroupDialog;

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
                patientGroupArrayList.remove(index);
                patientGroupAdapter.notifyDataSetChanged();
                return false;
            }
        });
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
        patientGroupArrayList.add(new PatientGroup(groupName));
        patientGroupAdapter.notifyDataSetChanged();
    }
}
