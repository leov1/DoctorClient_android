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
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 挑选患者页面
 */
public class PatientSelectActivity extends BaseTitleActivity {

    private ExpandableListView expand_lv;
    private View mHeader;
    private View mFooter;
    private CheckBox checkbox_all;
    private Button btn_send_patient;
    private TextView text_head;
    private PatientSelectAdapter adapter;
    private ArrayList<Group> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_select);

        initHeadView();
        getDatas();
        initViews();

    }

    private void initHeadView() {
        mHeader = View.inflate(this,R.layout.head_textview,null);
        text_head = (TextView) mHeader.findViewById(R.id.text_head);
        mFooter = View.inflate(this,R.layout.foot_patient_layout,null);
        checkbox_all = (CheckBox) mFooter.findViewById(R.id.checkbox_all);
        btn_send_patient = (Button) mFooter.findViewById(R.id.btn_send_patient);
    }

    private void initViews() {
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
        System.out.println("groups---->"+groups.toString());
        adapter = new PatientSelectAdapter(this,groups);
        expand_lv.setGroupIndicator(null);
        expand_lv.addHeaderView(mHeader);
        expand_lv.addFooterView(mFooter);
        expand_lv.setAdapter(adapter);
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

        System.out.println("doctorUuid--->" + LoginManager.getDoctorUuid());
        OkHttpUtils.get().url(Constants.GET_PATIENT_GROUP).addParams("doctorUuid", "6d3f252bc13e432f9fdc8a81a2ff425a")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                System.out.println("onError--->" + LoginManager.getDoctorUuid());
            }

            @Override
            public void onResponse(String onError) {
                System.out.println("doctorUuid--->" + onError);
            }
        });
    }


}
