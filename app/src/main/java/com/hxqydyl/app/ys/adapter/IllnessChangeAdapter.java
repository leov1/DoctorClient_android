package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.case_report.IllnessChangeRecordActivity;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/6.
 */
public class IllnessChangeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<IllnessChange> changes;

    public IllnessChangeAdapter(Context context, ArrayList<IllnessChange> changes) {
        this.context = context;
        this.changes = changes;
    }

    @Override
    public int getCount() {
        return changes.size();
    }

    @Override
    public Object getItem(int position) {
        return changes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IllnessChangeViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.illness_change_details, null);
            viewHolder = new IllnessChangeViewHolder();
            InjectUtils.injectView(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IllnessChangeViewHolder) convertView.getTag();
        }
        viewHolder.llSeeOtherRecord.setVisibility(View.GONE);
        IllnessChange change = (IllnessChange) getItem(position);
        String changeName = null;
        switch (change.getType()) {
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
        String changeDescriptionTile = String.format(context.getString(R.string.xx_description), changeName);
        String status = null;
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
        if (change.getType() != IllnessChange.Type.OTHER) {
            viewHolder.tlOtherChange.setVisibility(View.GONE);
            viewHolder.tlUsualChange.setVisibility(View.VISIBLE);
            viewHolder.tvChangeName.setText(changeName);
            viewHolder.tvChangeStatus.setText(status);
            viewHolder.tvChangeDescriptionTitle.setText(changeDescriptionTile);
            viewHolder.tvChangeDescription.setText(change.getDescription());
        } else {
            viewHolder.tlOtherChange.setVisibility(View.VISIBLE);
            viewHolder.tlUsualChange.setVisibility(View.GONE);
            viewHolder.tvOtherChangeTitle.setText(changeName);
            viewHolder.tvOtherChangeDescription.setText(change.getDescription());
        }
        return convertView;
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
}
