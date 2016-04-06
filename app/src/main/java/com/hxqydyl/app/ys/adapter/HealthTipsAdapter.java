package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 健康小贴士 适配器
 */
public class HealthTipsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<HealthTips> list;

    public HealthTipsAdapter(Context context, List<HealthTips> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_health_tips_group, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        final HealthTips tips = list.get(groupPosition);
        tvTitle.setText("第" + tips.getDay() + "天");
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_health_tips_child, null);
            holder = new ChildViewHolder();
            holder.etDay = (EditText) convertView.findViewById(R.id.etDay);
            holder.etFood = (EditText) convertView.findViewById(R.id.etFood);
            holder.etSport = (EditText) convertView.findViewById(R.id.etSport);
            holder.etSleep = (EditText) convertView.findViewById(R.id.etSleep);
            holder.etOther = (EditText) convertView.findViewById(R.id.etOther);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final HealthTips tips = list.get(groupPosition);
        holder.etDay.setText(String.valueOf(tips.getDay()));
        holder.etFood.setText(tips.getFood());
        holder.etSport.setText(tips.getSport());
        holder.etSleep.setText(tips.getSleep());
        holder.etOther.setText(tips.getOther());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ChildViewHolder {
        public EditText etDay;
        public EditText etFood;
        public EditText etSport;
        public EditText etSleep;
        public EditText etOther;

    }

}
