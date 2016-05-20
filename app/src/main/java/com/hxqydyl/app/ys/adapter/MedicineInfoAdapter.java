package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;

import java.util.List;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineInfoAdapter extends BaseAdapter {

    private Context context;
    private List<ImportantAdviceChild> list;

    public MedicineInfoAdapter(Context context, List<ImportantAdviceChild> list) {
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
        final ImportantAdviceChild m = list.get(position);
        m.initDate();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medicine, null);
            holder = new ViewHolder();
            holder.etName = (EditText) convertView.findViewById(R.id.etName);
            holder.ibDelete = (ImageButton) convertView.findViewById(R.id.ibDelete);
            holder.tvTimeMorning = (TextView) convertView.findViewById(R.id.tvTimeMorning);
            holder.tvTimeNoon = (TextView) convertView.findViewById(R.id.tvTimeNoon);
            holder.tvTimeNight = (TextView) convertView.findViewById(R.id.tvTimeNight);
            holder.tvFoodRelation = (TextView) convertView.findViewById(R.id.tvFoodRelation);
            holder.lvDosage = (ListView) convertView.findViewById(R.id.lvDosage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(m.getUuid()))
            holder.uuid = m.getUuid();
        holder.etName.setText(m.getMedicineUuid());
        holder.tvFoodRelation.setText(m.getFood());
        if (!TextUtils.isEmpty(m.getDirections())) {
            timeSelected(holder.tvTimeMorning, m.isTimeMorning(), holder);
            timeSelected(holder.tvTimeNoon, m.isTimeNoon(), holder);
            timeSelected(holder.tvTimeNight, m.isTimeNight(), holder);
        }
        List<MedicineDosage> mdList = m.getMd();
        MedicineDosageInfoAdapter adapter;
        if (mdList.size() == 0) {
            mdList.add(new MedicineDosage("", "", "mg"));
        }
        adapter = new MedicineDosageInfoAdapter(context, mdList);
        holder.lvDosage.setAdapter(adapter);

            holder.etName.setEnabled(false);
            holder.ibDelete.setVisibility(View.GONE);

        return convertView;
    }


    private void timeSelected(TextView tv, boolean bool, ViewHolder vh) {
        if (bool) {
            tv.setTextColor(context.getResources().getColor(R.color.white));
            tv.setBackgroundResource(R.drawable.btn_bg_selecter);
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.black));
            tv.setBackgroundResource(R.drawable.btn_medicine_time);
        }
        String text = tv.getText().toString();

        switch (text) {
            case "早":
                vh.boolTimeMorning = bool;
                break;
            case "中":
                vh.boolTimeNoon = bool;

                break;
            case "晚":
                vh.boolTimeNight = bool;
                break;
        }
    }

    public final class ViewHolder {
        public EditText etName;
        public ImageButton ibDelete;
        public TextView tvTimeMorning;
        public TextView tvTimeNoon;
        public TextView tvTimeNight;
        public TextView tvFoodRelation;    //与食物的关系，是饭前，饭后
        public ListView lvDosage;
        public String uuid;
        public boolean boolTimeMorning = false;
        public boolean boolTimeNoon = false;
        public boolean boolTimeNight = false;
    }
}
