package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.homepage.PageIconBean;
import com.hxqydyl.app.ys.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hxq on 2016/2/25.
 * 首页viewpager适配器
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<PageIconBean> list;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public GalleryPagerAdapter(Context context,ArrayList<PageIconBean> list){
        this.mContext = context;
        this.list = list;
    }

    public void update(ArrayList<PageIconBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        System.out.println("----instantiateItem------");
        if (list.size() != 0){
            position %= list.size();
            ImageView item = new ImageView(mContext);
            PageIconBean pageIconBean = list.get(position);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            imageLoader.displayImage(pageIconBean.getImageUrl(), item,Utils.initImageLoader(R.drawable.default_image,true));
            container.addView(item);
            return item;
        }

//
//        final int pos = position;
//        item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HouseDetailActivity.this, ImageGalleryActivity.class);
//                intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
//                intent.putExtra("position", pos);
//                startActivity(intent);
//            }
//        });

        return null;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
    }

}
