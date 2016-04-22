package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by hxq on 2016/3/16.
 */
public class EvpiPhotoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> list;

    public EvpiPhotoAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        if (list.size() == 6) {
            return 6;
        }
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_evpi_photo_gridview, parent, false);
        }

        ImageView img = BaseViewHolder.get(convertView, R.id.imge);
        if (position == list.size()) {
            img.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.photo_rect_add));
        } else {
            ImageLoader.getInstance().displayImage("file:/" + list.get(position), img);
        }
        return convertView;
    }

}
