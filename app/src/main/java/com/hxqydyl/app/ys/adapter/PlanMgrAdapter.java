package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

/**
 * Created by hxq on 2016/3/10.
 */
public class PlanMgrAdapter extends BaseAdapter{

    private Context context;

    public PlanMgrAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 3;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
        }

        TextView name_tv = BaseViewHolder.get(convertView,R.id.name_tv);
        TextView tag_tv = BaseViewHolder.get(convertView,R.id.tag_tv);

        name_tv.setText("妄想症初期随访方案");
        tag_tv.setText("已关联1人");
        return convertView;
    }
}
