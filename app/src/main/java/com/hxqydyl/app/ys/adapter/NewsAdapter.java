package com.hxqydyl.app.ys.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hxqydyl.app.ys.fragment.VideoListFragment;

/**
 * Created by alice_company on 2016/5/31.
 */
public class NewsAdapter extends FragmentPagerAdapter {

    private String[] TITLES;
    public NewsAdapter(FragmentManager fm,String[] TITLES) {
        super(fm);
        this.TITLES = TITLES;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new VideoListFragment();
        }
        if (position == 1) {
            return new VideoListFragment();
        }
        return new VideoListFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
