package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.utils.imageloader.ImageLoaderFactory;
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
    private DeleteListener listener;

    public CaseHistoryAdapter(Context context, ArrayList<Pic> picList,DeleteListener listener) {
        this.context = context;
        this.picList = picList;
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.case_history_list_item, null);
        }
        final Pic pic = (Pic) getItem(position);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv);
        ImageLoaderFactory.getLoader().displayImage(iv,pic.getDisplayThumbUri(),null);
        ImageView ivDeletePic = BaseViewHolder.get(convertView,R.id.ivDeletePic);
        if(pic.isCanDel()){
            ivDeletePic.setVisibility(View.VISIBLE);
            ivDeletePic.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onDeleteClick(position);
                    }
                }
            });
        }else{
            ivDeletePic.setVisibility(View.GONE);
        }
        return convertView;
    }

    public interface DeleteListener{
        void onDeleteClick(int position);
    }
}
