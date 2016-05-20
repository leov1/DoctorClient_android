package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.HealthTips;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.List;

/**
 * Created by wangchao36 on 16/3/22.
 * 健康小贴士 适配器
 */
public class HealthTipsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<HealthTips> list;
    private ExpandableListView listView;

    public HealthTipsAdapter(Context context, List<HealthTips> list, ExpandableListView listView) {
        this.context = context;
        this.list = list;
        this.listView = listView;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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

    public void notifyDataSetChanged() {
//        if (updateData) {
//            for (int i = 1, j = 0;
//                    i < listView.getChildCount() && j < list.size();
//                    i+=2, j = (i-1)/2) {
//                ChildViewHolder vh = (ChildViewHolder) listView.getChildAt(i).getTag();
//                HealthTips m = list.get(j);
//                m.setPeriod(vh.etDay.getText().toString()+"");
//                m.setDiet(vh.etFood.getText().toString()+"");
//                m.setSleep(vh.etSleep.getText().toString()+"");
//                m.setSports(vh.etSport.getText().toString()+"");
//                m.setRest(vh.etOther.getText().toString()+"");
//            }
//        }
        BaseExpandableListAdapter adapter =
                (BaseExpandableListAdapter) listView.getExpandableListAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_health_tips_group, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        final HealthTips tips = list.get(groupPosition);
        if (StringUtils.isEmpty(tips.getPeriod())) {
            tvTitle.setText("新增");
        } else {
            tvTitle.setText("第" + tips.getPeriod() + "天");
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_health_tips_child, null);
            holder = new ChildViewHolder();
            holder.etDay = (EditText) convertView.findViewById(R.id.etDay);

            holder.etOther = (EditText) convertView.findViewById(R.id.etOther);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final HealthTips tips = list.get(groupPosition);
        holder.etDay.setText(String.valueOf(tips.getPeriod()));
        holder.etOther.setText(tips.getRest());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public final class ChildViewHolder {
        public EditText etDay;
        public EditText etOther;

    }

}
