package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.PatientGroup;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/18.
 */
public class PatientGroupAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PatientGroup> patientGroupArrayList;

    public PatientGroupAdapter(Context context, ArrayList<PatientGroup> patientGroupArrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_group_item, null);
        TextView tvGroupName = BaseViewHolder.get(convertView, R.id.tvGroupName);
        final PatientGroup patientGroup = (PatientGroup) getItem(position);
        tvGroupName.setText(patientGroup.getGroupName());
        return convertView;
    }
}
