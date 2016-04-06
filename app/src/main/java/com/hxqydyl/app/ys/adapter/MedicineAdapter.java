package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;

import java.util.List;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private List<Medicine> list;

    public MedicineAdapter(Context context, List<Medicine> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
            holder = new ViewHolder();
            holder.etName = (EditText) convertView.findViewById(R.id.etName);
            holder.tvTimeMorning = (TextView) convertView.findViewById(R.id.tvTimeMorning);
            holder.tvTimeNoon = (TextView) convertView.findViewById(R.id.tvTimeNoon);
            holder.tvTimeNight = (TextView) convertView.findViewById(R.id.tvTimeNight);
            holder.tvFoodRelation = (TextView) convertView.findViewById(R.id.tvFoodRelation);
            holder.lvDosage = (ListView) convertView.findViewById(R.id.lvDosage);
            holder.tvFoodRelation.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Medicine m = list.get(position);
        holder.etName.setText(m.getName());
        holder.tvFoodRelation.setText(Medicine.items[m.getFoodRelation()]);

        MedicineDosageAdapter adapter = new MedicineDosageAdapter(context, list.get(position).getMdList());
        holder.lvDosage.setAdapter(adapter);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFoodRelation:
                foodRelationDialog((TextView)v);
                break;
        }
    }

    private void foodRelationDialog(final TextView v) {
        final NormalListDialog dialog = new NormalListDialog(context, Medicine.items);
        dialog.title("请选择与食物的关系")
                .titleBgColor(context.getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                v.setText(Medicine.items[position]);
                dialog.dismiss();
            }
        });
    }

    public final class ViewHolder {
        public EditText etName;
        public TextView tvTimeMorning;
        public TextView tvTimeNoon;
        public TextView tvTimeNight;
        public TextView tvFoodRelation;    //与食物的关系，是饭前，饭后
        public ListView lvDosage;
    }
}
