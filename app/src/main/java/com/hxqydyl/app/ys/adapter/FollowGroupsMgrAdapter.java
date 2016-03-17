package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

import ui.swipemenulistview.BaseSwipListAdapter;

/**
 * 随访分组adapter
 * Created by hxq on 2016/3/9.
 */
public class FollowGroupsMgrAdapter  extends BaseSwipListAdapter{

    private String[] list = new String[]{"妄想症","遗忘症","衰弱症"};
    private Context context;
    public FollowGroupsMgrAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow_group,parent,false);
        }

        TextView tv = BaseViewHolder.get(convertView,R.id.tv_name);
        tv.setText(list[position]);
        return convertView;
    }
}
