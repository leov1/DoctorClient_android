package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.register.GoodChoiceActivity;
import com.hxqydyl.app.ys.bean.register.TagsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxq on 2016/2/26.
 */
public class TagsAdapter extends BaseAdapter {

    private Context context;
    private int clickTemp = -1;
    private List<TagsBean> tagList;
    OnItemClickClass onItemClickClass;

    public TagsAdapter( Context context, OnItemClickClass onItemClickClass,List<TagsBean> tagList) {
        this.tagList = tagList;
        this.context = context;
        this.onItemClickClass = onItemClickClass;
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tags_item, null);
        }
        TextView textView = BaseViewHolder.get(convertView,R.id.tv_tags_item);
        CheckBox checkBox = BaseViewHolder.get(convertView,R.id.tags_checkbox);
        textView.setText(tagList.get(position).getTagName());
        checkBox.setVisibility(View.GONE);

        //设置被选中和取消选中条目的状态
        if (clickTemp == position) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }
        convertView.setOnClickListener(new OnTextClick(position, checkBox, textView));
        return convertView;
    }

    /**点击多选的接口*/
    public interface OnItemClickClass{
        void OnItemClick(View v, int position, CheckBox checkBox, TextView textView);
    }

    /** 多选的接口实现类 */
    class OnTextClick implements View.OnClickListener {

        int position;
        CheckBox checkBox;
        TextView tvSelected = null;
        TextView textView = null;

        public OnTextClick(int position, CheckBox checkBox) {
            this.position = position;
            this.checkBox = checkBox;
        }

        public OnTextClick(int position, CheckBox checkBox, TextView textView) {
            this.position = position;
            this.checkBox = checkBox;
            this.textView = textView;
        }

        @Override
        public void onClick(View v) {
            if (tagList != null && onItemClickClass != null) {
                onItemClickClass.OnItemClick(v, position, checkBox, textView);
            }
        }
    }

}
