package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.video.CommentBean;

import java.util.ArrayList;

/**
 * Created by alice_company on 2016/5/31.
 */
public class VideoCommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentBean> commentBeen;

    public VideoCommentAdapter(Context context, ArrayList<CommentBean> commentBeen) {
        this.context = context;
        this.commentBeen = commentBeen;
    }

    @Override
    public int getCount() {
        return commentBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return commentBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_video_comment, null);
        }
        TextView text_name = BaseViewHolder.get(view, R.id.text_name);
        TextView text_date = BaseViewHolder.get(view, R.id.text_date);
        TextView text_comment = BaseViewHolder.get(view, R.id.text_comment);

        return view;
    }
}
