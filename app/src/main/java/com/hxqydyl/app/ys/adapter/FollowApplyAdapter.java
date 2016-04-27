package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * 随访申请adapter
 * Created by hxq on 2016/3/10.
 */
public class FollowApplyAdapter extends BaseAdapter {

    private Context context;
    private List<FollowApply> list;
    private DisplayImageOptions options;

    public FollowApplyAdapter(Context context, List<FollowApply> list) {
        this.context = context;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(context, 50)))
                .showImageForEmptyUri(R.mipmap.portrait_man)
                .showImageOnFail(R.mipmap.portrait_man)
                .build();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow_apply, parent, false);
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
        if (!TextUtils.isEmpty( fa.getAge())){
            holder.tvAge.setText("年龄：" + fa.getAge() + "岁");
            holder.tvAge.setVisibility(View.VISIBLE);
        }else{
            holder.tvAge.setVisibility(View.GONE);
        }
        holder.tvDay.setText(fa.getCreateTime());
        holder.tvQ.setText(TextUtils.isEmpty(fa.getIllnessDescription())?"":"问题："+fa.getIllnessDescription());
        if ("1".equals(fa.getSex())) {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_man_flag));
        } else {
            holder.ivSex.setImageDrawable(context.getResources().getDrawable(R.mipmap.female));
        }
        ImageLoader.getInstance().displayImage(fa.getImgUrl(), holder.ivAvatar,options);

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
