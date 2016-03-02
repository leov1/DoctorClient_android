package com.hxq.hxq.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxq.hxq.R;

/**
 * Created by hxq on 2016/2/25.
 * 首页viewpager适配器
 */
public class GalleryPagerAdapter extends PagerAdapter {
    private int[] imageViewIds = new int[]{R.mipmap.house_background,R.mipmap.house_background,R.mipmap.house_background,R.mipmap.house_background};
    private Context mContext;

    public GalleryPagerAdapter(Context context){
        super();
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return imageViewIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(mContext);
        item.setImageResource(imageViewIds[position]);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);
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

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}
