package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 */
public class PlanSelfScaleAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public PlanSelfScaleAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.list = dataList;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plan_self_scale, parent, false);
        }
        TextView tvName = BaseViewHolder.get(convertView,R.id.tvName);
        tvName.setText(list.get(position));
        return convertView;
    }
}
