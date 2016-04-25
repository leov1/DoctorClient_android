package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.followupform.BadReactionRecord;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormOneRecord;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;
import com.hxqydyl.app.ys.bean.followupform.OtherCheckRecord;
import com.hxqydyl.app.ys.bean.followupform.WeightRecord;
import com.hxqydyl.app.ys.ui.fullshowview.FullShowGridView;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/27.
 */
public class FollowUpFormAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<FollowUpFormGroup> formList;
    private SeeHistoryButtonListener seeHistoryButtonListener;

    public FollowUpFormAdapter(Context context, ArrayList<FollowUpFormGroup> formList,SeeHistoryButtonListener seeHistoryButtonListener) {
        this.context = context;
        this.formList = formList;
        this.seeHistoryButtonListener = seeHistoryButtonListener;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_up_form_group_item, null);
        }
        ImageView ivGroupIcon = BaseViewHolder.get(convertView, R.id.ivGroupIcon);
        TextView tvGroupName = BaseViewHolder.get(convertView, R.id.tvGroupName);
        switch (((FollowUpFormGroup) getGroup(groupPosition)).getFormGroupType()) {
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
        ImageView ivExpandFlag = BaseViewHolder.get(convertView, R.id.ivExpandFlag);
        if (isExpanded) {
            ivExpandFlag.setImageResource(R.mipmap.icon_up_arrow);
        } else {
            ivExpandFlag.setImageResource(R.mipmap.icon_down_arrow);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_up_form_child_item, null);
            holder = new Holder();
            InjectUtils.injectView(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        setAllViewsGone(holder);
        FollowUpFormOneRecord record = (FollowUpFormOneRecord) getChild(groupPosition, childPosition);
        switch (((FollowUpFormGroup) getGroup(groupPosition)).getFormGroupType()) {
            case FollowUpFormGroup.Type.ILLNESS_CHANGE:
                holder.llIllnessChangeDetails.setVisibility(View.VISIBLE);
                bindIllnessChangeData((IllnessChange) record, holder.llIllnessChangeDetails);
                break;
            case FollowUpFormGroup.Type.WEIGHT_RECORD:
                holder.llWeightRecord.setVisibility(View.VISIBLE);
                bindWeightRecordData((WeightRecord)record,holder.llWeightRecord);
                break;
            case FollowUpFormGroup.Type.OTHER_CHECK_RECORD:
                holder.llOtherCheckAndResult.setVisibility(View.VISIBLE);
                bindOtherCheckAndResultData((OtherCheckRecord)record,holder.llOtherCheckAndResult);
                break;
            case FollowUpFormGroup.Type.EAT_MED_RECORD:
                if(record instanceof EatMedRecord) {
                    holder.llEatMedRecord.setVisibility(View.VISIBLE);
                    bindEatMedData((EatMedRecord)record,holder.llEatMedRecord);
                }else{
                    holder.llBadReactionRecord.setVisibility(View.VISIBLE);
                    bindBadReactionData((BadReactionRecord) record, holder.llBadReactionRecord);
                }
                break;
            case FollowUpFormGroup.Type.MEASURE_SELF_RECORD:
                holder.llFormMeasure.setVisibility(View.VISIBLE);
                bindFormMeasureData((MeasureFormRecord)record,holder.llFormMeasure);
                break;
            case FollowUpFormGroup.Type.DOC_MEASURE_RECORD:
                holder.llFormMeasure.setVisibility(View.VISIBLE);
                bindFormMeasureData((MeasureFormRecord)record,holder.llFormMeasure);
                break;
        }

        return convertView;
    }

    private void bindBadReactionData(BadReactionRecord record, LinearLayout llBadReactionRecord) {
        BadReactionRecordViewHolder viewHolder = new BadReactionRecordViewHolder();
        InjectUtils.injectView(viewHolder,llBadReactionRecord);
        viewHolder.tvOccurrTime.setText(record.getOccurrenceTime());
        viewHolder.tvDurationTime.setText(record.getDosageTime());
        viewHolder.tvSymptomsDes.setText(record.getFrequency());
        viewHolder.tvEffect.setText(record.getImpact());
    }

    private void bindFormMeasureData(MeasureFormRecord record, LinearLayout llFormMeasure) {
        MeasureFormRecordViewHolder viewHolder = new MeasureFormRecordViewHolder();
        InjectUtils.injectView(viewHolder,llFormMeasure);
        viewHolder.tvName.setText(record.getSubject());
        viewHolder.tvScore.setText(String.format(context.getString(R.string.score_xx),record.getScore()));
        viewHolder.tvResult.setText(String.format(context.getString(R.string.result_xx),record.getAnalys()));
        viewHolder.tvResultDescription.setText(String.format(context.getString(R.string.result_description_xx),record.getResultId()));
    }

    private void bindEatMedData(EatMedRecord record, LinearLayout llEatMedRecord) {
        EatMedRecordViewHolder viewHolder = new EatMedRecordViewHolder();
        InjectUtils.injectView(viewHolder,llEatMedRecord);
        viewHolder.tvMedName.setText(record.getProductName());
        viewHolder.tvEatTime.setText(record.getMedicalDateBegin() + "至" + record.getMedicalDateEnd());
        viewHolder.tvSingleAmount.setText(record.getSingleAmount());
        viewHolder.tvRate.setText(record.getFrequency());
        viewHolder.tvEatMethod.setText(record.getDirections());
    }

    private void bindOtherCheckAndResultData(OtherCheckRecord record, LinearLayout llOtherCheckAndResult) {
        OtherCheckAndResultViewHolder viewHolder = new OtherCheckAndResultViewHolder();
        InjectUtils.injectView(viewHolder, llOtherCheckAndResult);
        viewHolder.tvCheckName.setText(record.getName());
        boolean hasText = false;
        boolean hasPic = false;
        viewHolder.tvCheckResultText.setVisibility(View.GONE);
        viewHolder.lineBelowRetText.setVisibility(View.GONE);
        viewHolder.checkResultPic.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(record.getResult().getText())) {
            hasText = true;
            viewHolder.tvCheckResultText.setText(record.getResult().getText());
        }
        if (record.getResult().getPics().size() != 0) {
            hasPic = true;
            viewHolder.checkResultPic.setAdapter(new CaseHistoryAdapter(context, record.getResult().getPics(),null));
        }
        if (hasText && hasPic) {
            viewHolder.tvCheckResultText.setVisibility(View.VISIBLE);
            viewHolder.lineBelowRetText.setVisibility(View.VISIBLE);
            viewHolder.checkResultPic.setVisibility(View.VISIBLE);
        } else if (hasPic) {
            viewHolder.checkResultPic.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvCheckResultText.setVisibility(View.VISIBLE);
        }

    }

    private void bindWeightRecordData(WeightRecord record, LinearLayout llWeightRecord) {
        WeightRecordViewHolder viewHolder = new WeightRecordViewHolder();
        InjectUtils.injectView(viewHolder,llWeightRecord);
        viewHolder.tvWeight.setText("" + record.getWeight());
    }

    private void bindIllnessChangeData(IllnessChange change, LinearLayout llIllnessChangeDetails) {
        IllnessChangeViewHolder viewHolder = new IllnessChangeViewHolder();
        InjectUtils.injectView(viewHolder,llIllnessChangeDetails);
        if(change.getType() == IllnessChange.Type.SEE_HISTORY_BUTTON){
            viewHolder.tlOtherChange.setVisibility(View.GONE);
            viewHolder.tlUsualChange.setVisibility(View.GONE);
            viewHolder.llSeeOtherRecord.setVisibility(View.VISIBLE);
            viewHolder.llSeeOtherRecord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(seeHistoryButtonListener!=null){
                        seeHistoryButtonListener.onButtonClick();
                    }
                }
            });
            return;
        }else{
            viewHolder.llSeeOtherRecord.setVisibility(View.GONE);
        }
        String changeName = null;
        switch(change.getType()){
            case IllnessChange.Type.ILL:
                changeName = context.getString(R.string.illness_change);
                break;
            case IllnessChange.Type.FOOD:
                changeName = context.getString(R.string.food_change);
                break;
            case IllnessChange.Type.SLEEP:
                changeName = context.getString(R.string.sleep_change);
                break;
            case IllnessChange.Type.OTHER:
                changeName = context.getString(R.string.other_change);
                break;
        }
        String changeDescriptionTile = String.format(context.getString(R.string.xx_description),changeName);
        String status= null;
        switch (change.getStatus()) {
            case IllnessChange.Status.INVALID:
                status = context.getString(R.string.status_invalid);
                break;
            case IllnessChange.Status.BETTER:
                status = context.getString(R.string.status_better);
                break;
            case IllnessChange.Status.BEST:
                status = context.getString(R.string.status_best);
                break;
            default:
                break;
        }
        if(change.getType() != IllnessChange.Type.OTHER){
            viewHolder.tlOtherChange.setVisibility(View.GONE);
            viewHolder.tlUsualChange.setVisibility(View.VISIBLE);
            viewHolder.tvChangeName.setText(changeName);
            viewHolder.tvChangeStatus.setText(status);
            viewHolder.tvChangeDescriptionTitle.setText(changeDescriptionTile);
            viewHolder.tvChangeDescription.setText(change.getDescription());
        }else{
            viewHolder.tlOtherChange.setVisibility(View.VISIBLE);
            viewHolder.tlUsualChange.setVisibility(View.GONE);
            viewHolder.tvOtherChangeTitle.setText(changeName);
            viewHolder.tvOtherChangeDescription.setText(change.getDescription());
        }
    }

    private void setAllViewsGone(Holder holder) {
        holder.llIllnessChangeDetails.setVisibility(View.GONE);
        holder.llWeightRecord.setVisibility(View.GONE);
        holder.llOtherCheckAndResult.setVisibility(View.GONE);
        holder.llEatMedRecord.setVisibility(View.GONE);
        holder.llBadReactionRecord.setVisibility(View.GONE);
        holder.llFormMeasure.setVisibility(View.GONE);
    }

    class BadReactionRecordViewHolder{
        @InjectId(id = R.id.tvOccurrTime)
        TextView tvOccurrTime;
        @InjectId(id = R.id.tvDurationTime)
        TextView tvDurationTime;
        @InjectId(id = R.id.tvSymptomsDes)
        TextView tvSymptomsDes;
        @InjectId(id = R.id.tvEffect)
        TextView tvEffect;
    }

    class MeasureFormRecordViewHolder{
        @InjectId(id = R.id.tvName)
        TextView tvName;
        @InjectId(id = R.id.tvScore)
        TextView tvScore;
        @InjectId(id = R.id.tvResult)
        TextView tvResult;
        @InjectId(id = R.id.tvResultDescription)
        TextView tvResultDescription;
    }

    class EatMedRecordViewHolder{
        @InjectId(id = R.id.tvMedName)
        TextView tvMedName;
        @InjectId(id = R.id.tvEatTime)
        TextView tvEatTime;
        @InjectId(id = R.id.tvSingleAmount)
        TextView tvSingleAmount;
        @InjectId(id = R.id.tvRate)
        TextView tvRate;
        @InjectId(id = R.id.tvEatMethod)
        TextView tvEatMethod;
    }

    class OtherCheckAndResultViewHolder{
        @InjectId(id = R.id.tvCheckName)
        TextView tvCheckName;
        @InjectId(id = R.id.tvCheckResultText)
        TextView tvCheckResultText;
        @InjectId(id = R.id.lineBelowRetText)
        View lineBelowRetText;
        @InjectId(id = R.id.checkResultPic)
        FullShowGridView checkResultPic;

    }
    class WeightRecordViewHolder{
        @InjectId(id = R.id.tvWeight)
        TextView tvWeight;
    }
    class IllnessChangeViewHolder {
        // 常见变化（病情，饮食等）
        @InjectId(id = R.id.tlUsualChange)
        TableLayout tlUsualChange;
        @InjectId(id = R.id.tvChangeName)
        TextView tvChangeName;
        @InjectId(id = R.id.tvChangeStatus)
        TextView tvChangeStatus;
        @InjectId(id = R.id.tvChangeDescriptionTitle)
        TextView tvChangeDescriptionTitle;
        @InjectId(id = R.id.tvChangeDescription)
        TextView tvChangeDescription;
        // 其他变化
        @InjectId(id = R.id.tlOtherChange)
        TableLayout tlOtherChange;
        @InjectId(id = R.id.tvOtherChangeTitle)
        TextView tvOtherChangeTitle;
        @InjectId(id = R.id.tvOtherChangeDescription)
        TextView tvOtherChangeDescription;
        // 查看病情变化历史记录按钮
        @InjectId(id = R.id.llSeeOtherRecord)
        LinearLayout llSeeOtherRecord;

    }

    class Holder {
        @InjectId(id = R.id.llIllnessChangeDetails )
        LinearLayout llIllnessChangeDetails;
        @InjectId(id = R.id.llWeightRecord )
        LinearLayout llWeightRecord;
        @InjectId(id = R.id.llOtherCheckAndResult )
        LinearLayout llOtherCheckAndResult;
        @InjectId(id = R.id.llEatMedRecord )
        LinearLayout llEatMedRecord;
        @InjectId(id = R.id.llBadReactionRecord)
        LinearLayout llBadReactionRecord;
        @InjectId(id = R.id.llFormMeasure )
        LinearLayout llFormMeasure;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface SeeHistoryButtonListener{
        void onButtonClick();
    }
}
