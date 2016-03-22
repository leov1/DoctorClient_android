package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hxqydyl.app.ys.R;

/**
 * 待处理随访任务adapter
 */
public class FollowTaskAdapter extends BaseAdapter{

    private Context context;

    public FollowTaskAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow_apply,parent,false);
        }

        return convertView;
    }
}
