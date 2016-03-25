package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.register.ImageItem;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/3/16.
 */
public class EvpiPhotoAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private boolean shape;
    private Context context;
    private ArrayList<ImageItem> list;

    public boolean isShape(){
        return shape;
    }

    public void setShape(boolean shape){
        this.shape = shape;
    }

    public EvpiPhotoAdapter(Context context,ArrayList<ImageItem> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list.size() == 6){
            return 6;
        }
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_evpi_photo_gridview,parent,false);
        }
        ImageView img = BaseViewHolder.get(convertView,R.id.imge);
        if (position==list.size()){
            img.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.photo_rect_add));
            if (position == 6){
                img.setVisibility(View.GONE);
            }
        }else {
            img.setImageBitmap(list.get(position).getBitmap());
        }
        return convertView;
    }

    public void update(ArrayList<ImageItem> list){
        this.list = list;
        notifyDataSetChanged();
    }

}
