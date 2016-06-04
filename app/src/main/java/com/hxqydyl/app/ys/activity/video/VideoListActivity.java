package com.hxqydyl.app.ys.activity.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.NewsAdapter;
import com.hxqydyl.app.ys.fragment.VideoListFragment;
import com.hxqydyl.app.ys.ui.tabstrip.PagerSlidingTabStrip;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 讲堂
 */
public class VideoListActivity extends BaseRequstActivity {

    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private FragmentPagerAdapter adapter;
    private static String[] TITLES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);

        initDatas();
        initViews();
    }

    private void initViews() {
        adapter = new NewsAdapter(getSupportFragmentManager(), TITLES);
        viewPager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        tabStrip.setViewPager(viewPager);
    }

    private void initDatas() {
        TITLES = getResources().getStringArray(R.array.video_titles);
    }

}
