package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联的患者
 */
public class PlanPatientListActivity extends BaseTitleActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView listView;
    private FollowApplyAdapter adapter;
    private List<FollowApply> list;
    private String preceptUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply);
        Intent intent = getIntent();
        preceptUuid = intent.getStringExtra("preceptUuid");
        initViews();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVisitApplyList();
    }

    private void initViews() {
        initViewOnBaseTitle("关联的患者");
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new FollowApplyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initListeners() {
        setBackListener(this);
    }

    private void getVisitApplyList() {
        showDialog("");
        FollowPlanNet.getCustomerVisitRecordByUuid(preceptUuid, new FollowCallback(this){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                if (FollowApplyNet.myDev)
                    result = "[" +
                        "    {" +
                        "        \"imgUrl\": \"http://101.201.150.49:7500/dev1/0/000/001/0000001978.fid\"," +
                        "        \"realName\": \"小马\"," +
                        "        \"applyUuid\": \"0ef34b3fabbd44b1b9e1d72c0350a552\"," +
                        "        \"customerUuid\": \"9ee56d1310b54baa97f5a8abbe85a0b1\"," +
                        "        \"createTime\": \"2016-03-19\"," +
                        "        \"illnessDescription\": \"illnessDescriptionillnessDescription\"," +
                        "        \"doctorUuid\": \"rvicestaff0000001961\"," +
                        "        \"sex\": \"1\"," +
                        "        \"age\": \"26\"" +
                        "    }" +
                        "]";

                dismissDialog();
                List<FollowApply> tmp = FollowApply.parseList(result);
                if (tmp.size() > 0) {
                    list.clear();
                    list.addAll(tmp);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(PlanPatientListActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, FollowApplyDetailActivity.class);
        intent.putExtra("applyUuid", list.get(position).getApplyUuid());
        intent.putExtra("avatar", list.get(position).getImgUrl());
        //startActivity(intent);
    }
}
