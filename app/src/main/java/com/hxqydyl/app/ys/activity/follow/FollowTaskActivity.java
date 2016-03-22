package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.adapter.FollowTaskAdapter;

/**
 * 随访任务
 */
public class FollowTaskActivity extends BaseTitleActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

    private ListView listView;
    private FollowTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_apply);
        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("待处理随访任务");
        setBackListener();
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new FollowTaskAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initListeners() {
        setBackListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, FollowApplyDetailActivity.class);
        intent.putExtra("patientId", 1);
        startActivity(intent);
    }
}
