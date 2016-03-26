package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.Pic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/24.
 */
public class CaseHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Pic> picList;
    private ImageLoader loader;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public CaseHistoryAdapter(Context context, ArrayList<Pic> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.case_history_list_item, null);
        }
        final Pic pic = (Pic) getItem(position);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv);
        ImageLoader.getInstance().displayImage(pic.getDisplayThumbUri(),iv,options);
        return convertView;
    }
}
