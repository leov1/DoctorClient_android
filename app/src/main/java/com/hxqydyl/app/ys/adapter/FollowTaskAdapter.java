package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.bean.follow.FollowTask;
import com.hxqydyl.app.ys.utils.imageloader.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 待处理随访任务adapter
 */
public class FollowTaskAdapter extends BaseAdapter{

    private Context context;
    private List<FollowTask> list;

    public FollowTaskAdapter(Context context, List<FollowTask> list){
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
        FollowTask fa = list.get(position);
        holder.tvName.setText(fa.getRealName());
        holder.tvAge.setText("年龄：" + fa.getAge() + "岁");
        holder.tvDay.setText(fa.getCreateTime());
        holder.tvQ.setText(fa.getIllnessDescription());
        if ("1".equals(fa.getSex())) {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_man_flag));
        } else {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.female));
        }
        String img=(fa.getImgUrl()!=null&&fa.getImgUrl().size()>0)?fa.getImgUrl().get(0):"";
        ImageLoaderFactory.getLoader().displayImage(holder.ivAvatar,img, null);

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
