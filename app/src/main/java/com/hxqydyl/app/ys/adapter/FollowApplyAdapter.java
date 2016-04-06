package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 随访申请adapter
 * Created by hxq on 2016/3/10.
 */
public class FollowApplyAdapter extends BaseAdapter{

    private Context context;
    private List<FollowApply> list;

    public FollowApplyAdapter(Context context, List<FollowApply> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
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
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow_apply,parent,false);
            holder = new ViewHolder();
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.head_img);
            holder.ivSex = (ImageView) convertView.findViewById(R.id.img_sex);
            holder.tvName = (TextView) convertView.findViewById(R.id.head_name);
            holder.tvAge = (TextView) convertView.findViewById(R.id.tvAge);
            holder.tvDay = (TextView) convertView.findViewById(R.id.tvDay);
            holder.tvQ = (TextView) convertView.findViewById(R.id.tvQ);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FollowApply fa = list.get(position);
        holder.tvName.setText(fa.getRealName());
        holder.tvAge.setText(fa.getAge() + "岁");
        holder.tvDay.setText(fa.getCreateTime());
        holder.tvQ.setText(fa.getIllnessDescription());
        if ("1".equals(fa.getSex())) {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_man_flag));
        } else {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.female));
        }
        ImageLoader.getInstance().displayImage(fa.getImgUrl(), holder.ivAvatar);

        return convertView;
    }

    public final class ViewHolder {
        public ImageView ivAvatar;
        public TextView tvName;
        public ImageView ivSex;

        public TextView tvAge;
        public TextView tvDay;
        public TextView tvQ;
    }

}
