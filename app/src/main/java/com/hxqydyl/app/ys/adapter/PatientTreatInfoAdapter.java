package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/20.
 */
public class PatientTreatInfoAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<PatientTreatInfo> patientTreatInfoList;
    public PatientTreatInfoAdapter(Context context, ArrayList<PatientTreatInfo> patientTreatInfoList){
        this.context = context;
        this.patientTreatInfoList = patientTreatInfoList;
    }

    @Override
    public int getCount() {
        return patientTreatInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientTreatInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_treat_info_item,null);
        }
        final PatientTreatInfo treatInfo = (PatientTreatInfo) getItem(position);
        TextView tvTreatTime = BaseViewHolder.get(convertView,R.id.tvTreatTime);
        ImageView ivTreatIcon = BaseViewHolder.get(convertView,R.id.ivTreatIcon);
        TextView tvTreatName = BaseViewHolder.get(convertView,R.id.tvTreatName);
        TextView tvUnreadIcon = BaseViewHolder.get(convertView,R.id.tvUnreadIcon);
        tvTreatTime.setText(treatInfo.getTime());
        switch(treatInfo.getTreatType()){
            case PatientTreatInfo.TREAT_TYPE_MEN_ZHEN:
                tvTreatName.setText(context.getString(R.string.men_zhen_bing_li));
                ivTreatIcon.setImageResource(R.mipmap.menzhenbingli);
                break;
            case PatientTreatInfo.TREAT_TYPE_ZHU_YUAN:
                tvTreatName.setText(context.getString(R.string.zhu_yuan_bing_li));
                ivTreatIcon.setImageResource(R.mipmap.zhuyuanbingli);
                break;
            case PatientTreatInfo.TREAT_TYPE_BIAO_DAN:
                tvTreatName.setText(context.getString(R.string.sui_fang_biao_dan));
                ivTreatIcon.setImageResource(R.mipmap.suifangbiaodan);
                break;
        }
        if(treatInfo.isUnread()){
            tvUnreadIcon.setVisibility(View.VISIBLE);
        }else{
            tvUnreadIcon.setVisibility(View.GONE);
        }

        return convertView;
    }
}
