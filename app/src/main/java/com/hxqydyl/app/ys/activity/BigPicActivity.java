package com.hxqydyl.app.ys.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hxqydyl.app.ys.R;

import java.util.ArrayList;
import java.util.List;

import galleryfinal.wq.photo.mode.Images;

/**
 * Created by wangxu on 2016/4/26.
 */
public class BigPicActivity extends BaseTitleActivity {

    private ArrayList images;
    private int pos;

    public static void toBigPic(ArrayList<String> param1, int pos, Context context) {
        Bundle args = new Bundle();
        args.putStringArrayList("images", param1);
        args.putInt("pos", pos);
        Intent intent = new Intent(context, BigPicActivity.class);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            images = getIntent().getStringArrayListExtra("images");
            pos = getIntent().getIntExtra("pos", 0);

        }
        setContentView(R.layout.activity_big_pic);
        initView();
    }

    public void displayImage(String url, ImageView view) {
        Glide.with(this).load(url)
                .placeholder(com.hxqydyl.library.R.drawable.loadfaild)
                .into(view);
    }


    List<View> views = new ArrayList<>();
    ViewPager pager;
    MypageAdapter adapter;

    public void initView() {
        pager = (ViewPager) findViewById(com.hxqydyl.library.R.id.pager);
        initpage(images);
        initViewOnBaseTitle((pos + 1) + "/" + images.size());
        setBackListener();

    }

    private void initpage(List<Images> list) {
        if (list == null) {
            return;
        }
        views.clear();
        for (int i = 0; i < list.size(); i++) {
            View itemview = LayoutInflater.from(this).inflate(com.hxqydyl.library.R.layout.pageitem_view, null);
            ImageView iv_image = (ImageView) itemview.findViewById(com.hxqydyl.library.R.id.iv_image);
            displayImage(images.get(i).toString(), iv_image);
            views.add(itemview);
        }
        pager.removeAllViews();
        adapter = new MypageAdapter(views);
        pager.setAdapter(adapter);
        if (pos > 0 && pos < list.size()) {
            pager.setCurrentItem(pos, false);
        }
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                topTv.setText((position+1)+"/"+images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class MypageAdapter extends PagerAdapter {
        List<View> views;

        public MypageAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }

            return super.getItemPosition(object);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (views.size() > position) {
                container.removeView(views.get(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }
}
