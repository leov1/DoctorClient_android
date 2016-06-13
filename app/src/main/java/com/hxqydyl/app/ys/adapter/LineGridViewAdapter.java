package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;


/**
 * Created by hxq on 2016/2/25.
 */
public class LineGridViewAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = {"讲堂", "阅读", "诊所", "随访"};
    public int[] imgs = {R.mipmap.app_media, R.mipmap.app_read, R.mipmap.app_clinic, R.mipmap.app_follow};

    public LineGridViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.home_grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        ImageView yet_open = BaseViewHolder.get(convertView, R.id.yet_open);
        iv.setBackgroundResource(imgs[position]);
        if (position == 2 || position == 3) {
            yet_open.setVisibility(View.VISIBLE);
        }
        tv.setText(img_text[position]);
        return convertView;
    }
}
