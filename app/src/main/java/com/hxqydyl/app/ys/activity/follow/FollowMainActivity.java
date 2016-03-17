package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * 随访主页
 */
public class FollowMainActivity extends BaseTitleActivity implements View.OnClickListener{

    private ImageView addBtn;
    private RelativeLayout applyBtn;
    private RelativeLayout mgrBtn;
    private RelativeLayout patientBtn;
    private RelativeLayout noticeBtn;
    private RelativeLayout articeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow);
        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("随访");
        addBtn = (ImageView) findViewById(R.id.right_img);
        addBtn.setVisibility(View.VISIBLE);

        applyBtn = (RelativeLayout) findViewById(R.id.rl_apply);
        mgrBtn = (RelativeLayout) findViewById(R.id.rl_mgr);
        patientBtn = (RelativeLayout) findViewById(R.id.rl_patient);
        noticeBtn = (RelativeLayout) findViewById(R.id.rl_notice);
        articeBtn = (RelativeLayout) findViewById(R.id.rl_articl);

    }

    private void initListeners() {
        setBackListener(this);
        applyBtn.setOnClickListener(this);
        mgrBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        articeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.rl_apply://随访申请
                Intent applyIntent = new Intent(FollowMainActivity.this,FollowApplyActivity.class);
                startActivity(applyIntent);
                break;
            case R.id.rl_mgr://随访方案管理
                Intent mgrIntent = new Intent(FollowMainActivity.this,PlanMgrActivity.class);
                startActivity(mgrIntent);
                break;
            case R.id.rl_notice://群发通知
                Intent noticeIntent = new Intent(FollowMainActivity.this,MassActivity.class);
                startActivity(noticeIntent);
                break;
            case R.id.rl_articl://患教库
                Intent articleIntent = new Intent(FollowMainActivity.this,ArticleActivity.class);
                startActivity(articleIntent);
                break;
        }
    }
}
