package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医评量表选择adapter
 * Created by hxq on 2016/3/9.
 */
public class ChoiceHosAdapter extends BaseAdapter {

    private Context context;
    private static List<HospitalsBean> list;
    private static Map<Integer, Boolean> isSelectMap;
    private static int pos = -1;

    public ChoiceHosAdapter(Context context, List<HospitalsBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choice_scale, parent, false);
            holder = new ViewHolder();
            holder.checkBox = BaseViewHolder.get(convertView, R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setText(list.get(position).getHospitalName());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    if (pos != -1) {
                        isSelectMap.put(pos, false);
                    }
                    pos = position;
                    isSelectMap.put(pos, true);
                }else{
                    if(pos==position){
                        isSelectMap.put(position, false);
                        pos=-1;
                    }
                }
                notifyDataSetChanged();
            }

        });
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

    public  HospitalsBean getIsSelect() {
        return pos==-1?null:list.get(pos);
    }


}
