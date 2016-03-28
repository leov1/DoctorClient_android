package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PatientSelectAdapter;
import com.hxqydyl.app.ys.bean.article.Child;
import com.hxqydyl.app.ys.bean.article.Group;
import com.hxqydyl.app.ys.ui.fullshowview.FullShowExpandableListView;

import java.util.ArrayList;

/**
 * 挑选患者页面
 */
public class PatientSelectActivity extends BaseTitleActivity {

    private FullShowExpandableListView expand_lv;
    private View mHeader;
    private CheckBox checkbox_all;
    private Button btn_send_patient;
    private TextView text_head;
    private PatientSelectAdapter adapter;
    private ArrayList<Group> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_select);

        getDatas();
        initViews();
        initHeadView();
    }

    private void initHeadView() {
        mHeader = View.inflate(this,R.layout.head_textview,null);
        text_head = (TextView) mHeader.findViewById(R.id.text_head);
        expand_lv.addHeaderView(mHeader);
    }

    private void getDatas(){
        groups = new ArrayList<>();
        for (int i = 0;i<3;i++){
            Group group = new Group("","家人"+i);
            for (int j = 0;j<3;j++){
                Child child = new Child("","","弟弟"+j);
                group.addChildrenItem(child);
            }
            groups.add(group);
        }
    }

    private void initViews() {
        expand_lv = (FullShowExpandableListView) findViewById(R.id.expand_lv);
        btn_send_patient = (Button)findViewById(R.id.btn_send_patient);
        checkbox_all = (CheckBox) findViewById(R.id.checkbox_all);
        System.out.println("groups---->"+groups.toString());
        adapter = new PatientSelectAdapter(this,groups);
        expand_lv.setGroupIndicator(null);
        expand_lv.setAdapter(adapter);
    }


}
