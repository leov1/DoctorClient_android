package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.hxqydyl.app.ys.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医评量表选择adapter
 * Created by hxq on 2016/3/9.
 */
public class ChoiceScaleAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private static Map<Integer, Boolean> isSelectMap;

    public ChoiceScaleAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
        isSelectMap = new HashMap<>();
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
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choice_scale, parent, false);
            holder = new ViewHolder();
            holder.checkBox = BaseViewHolder.get(convertView, R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setText(list.get(position));
        Boolean bool = isSelectMap.get(position);
        if (bool != null && bool) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        return convertView;
    }

    public final class ViewHolder {
        public CheckBox checkBox;
    }

    public static Map<Integer, Boolean> getIsSelectMap() {
        return isSelectMap;
    }

    public static void setIsSelectMap(Map<Integer, Boolean> isSelectMap) {
        ChoiceScaleAdapter.isSelectMap = isSelectMap;
    }
}
