package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;

/**
 * 随访申请
 */
public class FollowApplyActivity extends BaseTitleActivity implements View.OnClickListener{

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

}
