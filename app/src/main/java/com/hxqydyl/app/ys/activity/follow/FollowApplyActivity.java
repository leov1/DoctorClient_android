package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;

/**
 * 随访申请
 */
public class FollowApplyActivity extends BaseTitleActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView listView;
    private FollowApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("随访申请");
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new FollowApplyAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initListeners() {
        setBackListener(this);
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
        intent.putExtra("patientId", 1);
        startActivity(intent);
    }
}