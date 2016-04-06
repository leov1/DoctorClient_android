package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.article.Child;
import com.hxqydyl.app.ys.bean.article.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxq on 2016/3/28.
 */
public class PatientSelectAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Context context = null;
    private ArrayList<Group> groupList;
    private CheckBox checkbox_all;

    public PatientSelectAdapter(Context context, final ArrayList<Group> groups, final CheckBox checkbox_all) {
        this.context = context;
        this.groupList = groups;
        this.checkbox_all = checkbox_all;
        checkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    for (int i = 0;i<groupList.size(); i++){
                        Group group = groupList.get(i);
                        group.setChecked(isChecked);
                       for (int j = 0;j<group.getChildrenCount();j++){
                           Child child = group.getChildItem(j);
                           child.setChecked(isChecked);
                       }
                    }
                notifyDataSetChanged();
            }
        });
    }

    public void updataUI(ArrayList<Group> groups) {
        this.groupList = groups;
        notifyDataSetChanged();
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).getChildrenCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
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
        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_patient_select_group, parent, false);
        }
        CheckBox checkbox = BaseViewHolder.get(convertView, R.id.checkbox);
        TextView group_text = BaseViewHolder.get(convertView, R.id.group_text);
        ImageView imge = BaseViewHolder.get(convertView, R.id.imge);
        group_text.setText(group.getGroupName());
        checkbox.setChecked(group.getChecked());
        checkbox.setOnClickListener(new Group_CheckBox_Click(groupPosition));
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Child child = groupList.get(groupPosition).getChildItem(childPosition);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_patient_select_child, parent, false);
        }

        CheckBox checkbox = BaseViewHolder.get(convertView, R.id.checkbox);
        ImageView ivPatientPortrait = BaseViewHolder.get(convertView, R.id.ivPatientPortrait);
        TextView tvPatientName = BaseViewHolder.get(convertView, R.id.tvPatientName);
        ImageView ivSexFlag = BaseViewHolder.get(convertView, R.id.ivSexFlag);
        TextView tvPatientAge = BaseViewHolder.get(convertView, R.id.tvPatientAge);
        TextView tvDescription = BaseViewHolder.get(convertView, R.id.tvDescription);

        tvPatientName.setText(child.getCustomerName());
        checkbox.setChecked(child.getChecked());

        checkbox.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 收缩列表时要处理的东西都放这儿
     */
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    /**
     * 展开列表时要处理的东西都放这儿
     */
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        handleClick(childPosition, groupPosition);
        return true;
    }

    class Group_CheckBox_Click implements View.OnClickListener {

        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public void onClick(View v) {
            groupList.get(groupPosition).toggle();

            // 将children的isChicked全面设成跟group一样
            int childrenCount = groupList.get(groupPosition).getChildrenCount();
            boolean groupIsChecked = groupList.get(groupPosition).getChecked();
            for (int i = 0; i < childrenCount; i++) {
                groupList.get(groupPosition).getChildItem(i).setChecked(groupIsChecked);
            }
            notifyDataSetChanged();
        }
    }

    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        @Override
        public void onClick(View v) {
            handleClick(childPosition, groupPosition);
        }
    }

    public void handleClick(int childPosition, int groupPosition) {
        groupList.get(groupPosition).getChildItem(childPosition).toggle();

        int childrenCount = groupList.get(groupPosition).getChildrenCount();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!groupList.get(groupPosition).getChildItem(i).getChecked())
                childrenAllIsChecked = false;
        }
        groupList.get(groupPosition).setChecked(childrenAllIsChecked);
        notifyDataSetChanged();
    }

}
