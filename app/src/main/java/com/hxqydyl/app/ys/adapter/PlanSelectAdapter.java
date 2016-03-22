package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.bean.plan.Plan;

import java.util.List;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PlanSelectAdapter extends BaseAdapter {
    private Context context;
    private List<Plan> planList;
    private int select = -1;

    public PlanSelectAdapter(Context context, List<Plan> planList) {
        this.context = context;
        this.planList = planList;
    }

    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int position) {
        return planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_group_select_item, null);
        RadioButton rbSelectGroup = BaseViewHolder.get(convertView, R.id.cbSelectGroup);
        if (position == select) {
            rbSelectGroup.setChecked(true);
        } else {
            rbSelectGroup.setChecked(false);
        }
        rbSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select != position) {
                    setSelect(position);
                }
            }
        });
        TextView tvGroupName = BaseViewHolder.get(convertView, R.id.tvGroupName);
        final PatientGroup patientGroup = (PatientGroup) getItem(position);
        tvGroupName.setText(patientGroup.getGroupName());
        return convertView;
    }

    public void setSelect(int position) {
        select = position;
        notifyDataSetChanged();
    }

    public int getSelect() {
        return select;
    }
}
