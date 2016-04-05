package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/6.
 */
public class IllnessChangeRecordAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<IllnessChangeRecord> changeRecords;

    public IllnessChangeRecordAdapter(Context context, ArrayList<IllnessChangeRecord> changeRecords) {
        this.context = context;
        this.changeRecords = changeRecords;
    }

    @Override
    public int getCount() {
        return changeRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return changeRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_illness_change_record, null);
        }
        IllnessChangeRecord record = (IllnessChangeRecord) getItem(position);
        TextView tvChangeStatus = BaseViewHolder.get(convertView, R.id.tvChangeStatus);
        tvChangeStatus.setText(String.format(context.getString(R.string.illness_change_status_in_record_list), record.getStatus()));
        TextView tvTime = BaseViewHolder.get(convertView, R.id.tvTime);
        tvTime.setText(record.getTime());
        TextView tvQuestion = BaseViewHolder.get(convertView, R.id.tvQuestion);
        tvQuestion.setText(String.format(context.getString(R.string.illness_change_question), record.getDescription()));
        return convertView;
    }
}
