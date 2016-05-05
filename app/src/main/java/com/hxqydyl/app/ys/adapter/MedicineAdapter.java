package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdvice;
import com.hxqydyl.app.ys.bean.follow.plan.ImportantAdviceChild;
import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 药品用量adapter
 * Created by hxq on 2016/3/9.
 */
public class MedicineAdapter extends BaseAdapter {

    private Context context;
    private List<ImportantAdviceChild> list;
    private ListView listView;
    private boolean isEdit;
    private StringBuffer uuidDeleteSb;

    public MedicineAdapter(Context context, List<ImportantAdviceChild> list, ListView listView, boolean isEdit) {
        this.context = context;
        this.list = list;
        this.listView = listView;
        this.isEdit = isEdit;
        uuidDeleteSb = new StringBuffer();
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

    public void notifyDataSetChanged(boolean updateData) {
        if (updateData && isEdit) {
            for (int i = 0; i < listView.getChildCount() && i < list.size(); i++) {
                ViewHolder vh = (ViewHolder) listView.getChildAt(i).getTag();
                ImportantAdviceChild m = list.get(i);
                m.setMedicineUuid(vh.etName.getText().toString());

                List<MedicineDosage> mdList = m.getMd();
                if (mdList == null) {
                    mdList = new ArrayList<>();
                    m.setMd(mdList);
                }
                for (int j = 0; j < vh.lvDosage.getChildCount() && i < mdList.size(); j++) {
                    MedicineDosageAdapter.ViewHolder mdVh =
                            (MedicineDosageAdapter.ViewHolder) vh.lvDosage.getChildAt(j).getTag();
                    MedicineDosage md = mdList.get(j);
                    md.setDay(mdVh.etDay.getText().toString());
                    md.setSize(mdVh.etSize.getText().toString());
                    md.setUnit(mdVh.tvUnit.getText().toString());
                }
            }

        }
        super.notifyDataSetChanged();
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
        MedicineDosageAdapter adapter;
        if (mdList.size() == 0) {
            mdList.add(new MedicineDosage("", "", "mg"));
        }
        adapter = new MedicineDosageAdapter(context, mdList, holder.lvDosage, this, isEdit);
        holder.lvDosage.setAdapter(adapter);
        if (isEdit) {
            holder.etName.setEnabled(true);
            holder.ibDelete.setVisibility(View.VISIBLE);
            setTimeClick(holder.tvTimeMorning, m, holder);
            setTimeClick(holder.tvTimeNoon, m, holder);
            setTimeClick(holder.tvTimeNight, m, holder);
            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImportantAdviceChild m = list.get(position);
                    if (StringUtils.isNotEmpty(m.getMedicineUuid())) {
                        if (uuidDeleteSb.length() > 0) uuidDeleteSb.append(",");
                        uuidDeleteSb.append(m.getMedicineUuid());
                    }
                    list.remove(position);
                    MedicineAdapter.this.notifyDataSetChanged(true);
                }
            });
            holder.tvFoodRelation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodRelationDialog((TextView) v, m);
                }
            });
        } else {
            holder.etName.setEnabled(false);
            holder.ibDelete.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void foodRelationDialog(final TextView v, final ImportantAdviceChild m) {
        final NormalListDialog dialog = new NormalListDialog(context, Medicine.items);
        dialog.title("请选择与食物的关系")
                .titleBgColor(context.getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                v.setText(Medicine.items[position]);
                m.setFood(v.getText().toString());
                dialog.dismiss();
            }
        });
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

    private void setTimeClick(final TextView tv, final ImportantAdviceChild m, final ViewHolder vh) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                String text = tv.getText().toString();
                boolean bool = false;
                switch (text) {
                    case "早":
                        m.setTimeMorning(!m.isTimeMorning());
                        bool = m.isTimeMorning();
                        break;
                    case "中":
                        m.setTimeNoon(!m.isTimeNoon());
                        bool = m.isTimeNoon();

                        break;
                    case "晚":
                        m.setTimeNight(!m.isTimeNight());
                        bool = m.isTimeNight();
                        break;
                }
                timeSelected(tv, bool, vh);
            }
        });
    }

    public StringBuffer getUuidDeleteSb() {
        return uuidDeleteSb;
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
