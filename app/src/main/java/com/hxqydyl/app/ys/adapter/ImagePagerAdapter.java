package com.hxqydyl.app.ys.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.hxqydyl.app.ys.ui.imageDisplayFragment.ImageDisplayFragment;

import java.util.List;

/**
 * Created by hxq on 2016/3/21.
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter{
   private List<String> list;

    public ImagePagerAdapter(FragmentManager fm,List<String> list){
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return new ImageDisplayFragment().create(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    public void changeList(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
