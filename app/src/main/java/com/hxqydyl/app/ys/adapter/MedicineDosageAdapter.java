package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;

import java.util.List;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineDosageAdapter extends BaseAdapter{

    private Context context;
    private List<MedicineDosage> list;
    private ListView listView;
    private MedicineAdapter medicineAdapter;

    public MedicineDosageAdapter(Context context, List<MedicineDosage> list,
                                 ListView listView, MedicineAdapter medicineAdapter){
        this.context = context;
        this.list = list;
        this.listView = listView;
        this.medicineAdapter = medicineAdapter;
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
        return 0;
    }

    public void notifyDataSetChanged(boolean updateData) {
        if (updateData) {
            medicineAdapter.notifyDataSetChanged(true);
        }
        super.notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final MedicineDosage md = list.get(position);
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
        holder.etDay.setText(md.getDay());
        holder.etSize.setText(md.getSize());
        holder.tvUnit.setText(md.getUnit());
        holder.tvUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitDialog(holder.tvUnit);
            }
        });
        if (position == list.size() -1) {
            holder.ibBtn.setImageDrawable(convertView.getResources().getDrawable(R.mipmap.tianjiayongliang));
            holder.ibBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.add(new MedicineDosage("", "", "mg"));
                    MedicineDosageAdapter.this.notifyDataSetChanged(true);
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

    private void unitDialog(final TextView v) {
        final String[] unitArr = {"mg", "ml", "粒", "袋"};
        final NormalListDialog dialog = new NormalListDialog(context, unitArr);
        dialog.title("用量单位")
                .titleBgColor(context.getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                v.setText(unitArr[position]);
                dialog.dismiss();
            }
        });
    }

    public final class ViewHolder {
        public EditText etDay;
        public EditText etSize;
        public TextView tvUnit;
        public ImageButton ibBtn;
    }
}
