package com.hxqydyl.app.ys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.video.VideoDetailActivity;
import com.hxqydyl.app.ys.adapter.VideoListAdapter;
import com.hxqydyl.app.ys.bean.video.VideoBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class VideoListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View view;
    @Bind(R.id.listView_video)
    ListView listView_video;

    private VideoListAdapter adapter;
    private ArrayList<VideoBean> videoBeans;

    public static VideoListFragment newInstance(String param1, String param2) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,view);

        initDatas();
        initViews();
        initListeners();
    }

    private void initListeners() {
        listView_video.setOnItemClickListener(this);
    }

    private void initViews() {
        adapter = new VideoListAdapter(this.getContext(), videoBeans);
        listView_video.setAdapter(adapter);
    }

    private void initDatas() {
        videoBeans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            videoBeans.add(new VideoBean(String.format("http://img10.3lian.com/sc6/show02/67/27/0%s.jpg",i+1),"司马梅-抑郁症的神经生化基础及优化治疗","2016-01-08 16:19:44","2","1"));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), VideoDetailActivity.class));
    }
}
