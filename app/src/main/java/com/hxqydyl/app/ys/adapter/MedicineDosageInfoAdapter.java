package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;

import java.util.List;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineDosageInfoAdapter extends BaseAdapter {

    private Context context;
    private List<MedicineDosage> list;

    public MedicineDosageInfoAdapter(Context context, List<MedicineDosage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final MedicineDosage md = list.get(position);
        if (convertView == null) {
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
        holder.etDay.setText(md.getDay());
        holder.etSize.setText(md.getSize());
        holder.tvUnit.setText(md.getUnit());
        holder.etDay.setEnabled(false);
        holder.etSize.setEnabled(false);
        holder.ibBtn.setVisibility(View.GONE);

        return convertView;
    }
    public final class ViewHolder {
        public EditText etDay;
        public EditText etSize;
        public TextView tvUnit;
        public ImageButton ibBtn;
    }
}
