package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.PatientGroup;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientGroupSelectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PatientGroup> patientGroupArrayList;
    private int select = -1;

    public PatientGroupSelectAdapter(Context context, ArrayList<PatientGroup> patientGroupArrayList) {
        this.context = context;
        this.patientGroupArrayList = patientGroupArrayList;
    }

    @Override
    public int getCount() {
        return patientGroupArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientGroupArrayList.get(position);
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
