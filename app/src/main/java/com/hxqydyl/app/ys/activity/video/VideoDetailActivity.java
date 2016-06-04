package com.hxqydyl.app.ys.activity.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.adapter.VideoCommentAdapter;
import com.hxqydyl.app.ys.bean.video.CommentBean;
import com.hxqydyl.app.ys.ui.video.VideoDetailView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoDetailActivity extends Activity {

    @Bind(R.id.listView_comment)
    ListView listView;

    private VideoDetailView headView;
    private VideoCommentAdapter adapter;
    private ArrayList<CommentBean> commentBeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);

        initDatas();
        initViews();
        initListeners();
    }

    private void initDatas() {
        commentBeen = new ArrayList<>();

    }

    private void initViews() {
        headView = new VideoDetailView(this);
        listView.addHeaderView(headView);
        adapter = new VideoCommentAdapter(this,commentBeen);
        listView.setAdapter(adapter);

        headView.setText_people_name("dddddddd");
        headView.setText_date("2016");
        headView.setText_course_info("574585");
        headView.setText_people_place("555lll");
    }

    private void initListeners() {
    }
}
