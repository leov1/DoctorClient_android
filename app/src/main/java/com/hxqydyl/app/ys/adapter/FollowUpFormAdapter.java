package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/27.
 */
public class FollowUpFormAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<FollowUpFormGroup> formList;

    public FollowUpFormAdapter(Context context, ArrayList<FollowUpFormGroup> formList) {
        this.context = context;
        this.formList = formList;
    }

    @Override
    public int getGroupCount() {
        return formList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return formList.get(groupPosition).getRecords().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return formList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return formList.get(groupPosition).getRecords().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_up_form_group_item,null);
        }
        ImageView ivGroupIcon = BaseViewHolder.get(convertView,R.id.ivGroupIcon);
        TextView tvGroupName = BaseViewHolder.get(convertView,R.id.tvGroupName);
        switch (((FollowUpFormGroup)getGroup(groupPosition)).getFormGroupType()){
            case FollowUpFormGroup.Type.ILLNESS_CHANGE:
                ivGroupIcon.setImageResource(R.mipmap.bingqingbianhua);
                tvGroupName.setText(context.getString(R.string.illness_change));
                break;
            case FollowUpFormGroup.Type.WEIGHT_RECORD:
                ivGroupIcon.setImageResource(R.mipmap.tizhongjilu);
                tvGroupName.setText(context.getString(R.string.weight_record));
                break;
            case FollowUpFormGroup.Type.OTHER_CHECK_RECORD:
                ivGroupIcon.setImageResource(R.mipmap.qitajianchajijieguo);
                tvGroupName.setText(context.getString(R.string.other_check_and_result));
                break;
            case FollowUpFormGroup.Type.EAT_MED_RECORD:
                ivGroupIcon.setImageResource(R.mipmap.fuyaojilu);
                tvGroupName.setText(context.getString(R.string.eat_med_record));
                break;
            case FollowUpFormGroup.Type.MEASURE_SELF_RECORD:
                ivGroupIcon.setImageResource(R.mipmap.zipingliangbiao);
                tvGroupName.setText(context.getString(R.string.form_self_measure));
                break;
            case FollowUpFormGroup.Type.DOC_MEASURE_RECORD:
                ivGroupIcon.setImageResource(R.mipmap.yipingliangbiao);
                tvGroupName.setText(context.getString(R.string.form_doctor_measure));
                break;
        }
        ImageView ivExpandFlag = BaseViewHolder.get(convertView,R.id.ivExpandFlag);
        if(isExpanded){
            ivExpandFlag.setImageResource(R.mipmap.icon_up_arrow);
        }else{
            ivExpandFlag.setImageResource(R.mipmap.icon_right_arrow);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_up_form_child_item,null);
            holder = new Holder();
            InjectUtils.injectView(holder,convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        setAllViewsGone(holder);
        switch(((FollowUpFormGroup)getGroup(groupPosition)).getFormGroupType()){
            case FollowUpFormGroup.Type.ILLNESS_CHANGE:
                holder.llIllnessChangeDetails.setVisibility(View.VISIBLE);

                break;
            case FollowUpFormGroup.Type.WEIGHT_RECORD:
                holder.llWeightRecord.setVisibility(View.VISIBLE);
                break;
            case FollowUpFormGroup.Type.OTHER_CHECK_RECORD:
                holder.llOtherCheckAndResult.setVisibility(View.VISIBLE);
                break;
            case FollowUpFormGroup.Type.EAT_MED_RECORD:
                holder.llEatMedRecord.setVisibility(View.VISIBLE);
                break;
            case FollowUpFormGroup.Type.MEASURE_SELF_RECORD:
                holder.llFormSelfMeasure.setVisibility(View.VISIBLE);
                break;
            case FollowUpFormGroup.Type.DOC_MEASURE_RECORD:
                holder.llFormDoctorMeasure.setVisibility(View.VISIBLE);
                break;
        }

        return convertView;
    }

    private void setAllViewsGone(Holder holder){
        holder.llIllnessChangeDetails.setVisibility(View.GONE);
        holder.llWeightRecord.setVisibility(View.GONE);
        holder.llOtherCheckAndResult.setVisibility(View.GONE);
        holder.llEatMedRecord.setVisibility(View.GONE);
        holder.llFormSelfMeasure.setVisibility(View.GONE);
        holder.llFormDoctorMeasure.setVisibility(View.GONE);
    }


    class Holder{
        LinearLayout llIllnessChangeDetails;
        LinearLayout llWeightRecord;
        LinearLayout llOtherCheckAndResult;
        LinearLayout llEatMedRecord;
        LinearLayout llFormSelfMeasure;
        LinearLayout llFormDoctorMeasure;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
