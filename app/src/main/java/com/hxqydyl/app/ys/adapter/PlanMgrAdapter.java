package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;

import java.util.List;

/**
 * Created by hxq on 2016/3/10.
 */
public class PlanMgrAdapter extends BaseAdapter {

    private Context context;
    private List<Plan> planList;
    private boolean isShowTag;

    public PlanMgrAdapter(Context context, List<Plan> planList) {
        this(context, planList, true);
    }

    public PlanMgrAdapter(Context context, List<Plan> planList, boolean isShowTag) {
        this.context = context;
        this.planList = planList;
        this.isShowTag = isShowTag;
    }

    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        }

        TextView name_tv = BaseViewHolder.get(convertView, R.id.name_tv);
        TextView tag_tv = BaseViewHolder.get(convertView, R.id.tag_tv);
        final Plan plan = planList.get(position);
        name_tv.setText(plan.getPreceptName());
//        if (isShowTag) {
//            tag_tv.setText("已关联" + plan.getNum() + "人");
//        } else {
//            tag_tv.setText("");
//        }
        tag_tv.setText("");
        return convertView;
    }
}
