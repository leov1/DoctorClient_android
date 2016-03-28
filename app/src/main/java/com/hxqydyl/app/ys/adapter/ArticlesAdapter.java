package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

/**
 * Created by hxq on 2016/3/8.
 * 患教库adapter
 */
public class ArticlesAdapter extends BaseAdapter {

    private Context context;
    private String[] names = new String[]{"精神压力对血压有何影响？", "精神压力对血压有何影响？", "精神压力对血压有何影响？", "精神压力对血压有何影响？", "精神压力对血压有何影响？"};

    public ArticlesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
        }
        TextView title = BaseViewHolder.get(convertView,R.id.name_tv);
        TextView tag = BaseViewHolder.get(convertView,R.id.tag_tv);

        if (position == 3){
            tag.setText("未选用");
            tag.setTextColor(context.getResources().getColor(R.color.gray_light));
        }
        tag.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_right_arrow,0);
        tag.setCompoundDrawablePadding(20);
        return convertView;
    }
}
