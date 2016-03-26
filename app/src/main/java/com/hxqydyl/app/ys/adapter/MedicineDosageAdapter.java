package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.plan.MedicineDosage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineDosageAdapter extends BaseAdapter{

    private Context context;
    private List<MedicineDosage> list;

    public MedicineDosageAdapter(Context context, List<MedicineDosage> list){
        this.context = context;
        this.list = list;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medicine_dosage, parent, false);
            holder = new ViewHolder();
            holder.etDay = (EditText) convertView.findViewById(R.id.etDay);
            holder.etSize = (EditText) convertView.findViewById(R.id.etSize);
            holder.tvUnit = (TextView) convertView.findViewById(R.id.tvUnit);
            holder.ibBtn = (ImageButton) convertView.findViewById(R.id.ibBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MedicineDosage md = list.get(position);
        holder.etDay.setText(String.valueOf(md.getDay()));
        holder.etSize.setText(String.valueOf(md.getSize()));
        holder.tvUnit.setText(md.getUnit());
        if (position == list.size() -1) {
            holder.ibBtn.setImageDrawable(convertView.getResources().getDrawable(R.mipmap.tianjiayongliang));
            holder.ibBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.add(new MedicineDosage());
                    MedicineDosageAdapter.this.notifyDataSetChanged();
                }
            });
        } else {
            holder.ibBtn.setImageDrawable(convertView.getResources().getDrawable(R.mipmap.shanchuyongliang));
            holder.ibBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    MedicineDosageAdapter.this.notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    public final class ViewHolder {
        public EditText etDay;
        public EditText etSize;
        public TextView tvUnit;
        public ImageButton ibBtn;
    }
}
