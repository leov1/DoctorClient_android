package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.hxqydyl.app.ys.R;

/**
 * 医评量表选择adapter
 * Created by hxq on 2016/3/9.
 */
public class ChoiceScaleAdapter extends BaseAdapter{

    private Context context;
    private String[] list = new String[]{"医评量表","医评量表","医评量表","医评量表","医评量表"};

    public ChoiceScaleAdapter(Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choice_scale,parent,false);
        }
        CheckBox checkBox = BaseViewHolder.get(convertView,R.id.checkbox);
        checkBox.setText(list[position]);
        return convertView;
    }
}
