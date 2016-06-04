package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.video.VideoBean;
import com.hxqydyl.app.ys.utils.imageloader.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alice_company on 2016/5/31.
 */
public class VideoListAdapter extends BaseVideoAdapter {

    private Context context;
    private ArrayList<VideoBean> videoBeans;

    public VideoListAdapter(Context context, ArrayList<VideoBean> videoBeans){
        this.context = context;
        this.videoBeans = videoBeans;
    }

    @Override
    public int getCount() {
        return videoBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return videoBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null) {
            view = LinearLayout.inflate(context, R.layout.layout_item_video, null);
            holder = new Holder();
            ButterKnife.bind(holder, view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        VideoBean videoBean = (VideoBean) getItem(i);
        holder.text_data.setText(videoBean.getDate());
        holder.text_title.setText(videoBean.getName());
        ImageLoaderFactory.getLoader().displayImage(holder.img_logo,videoBean.getImg(),null);
        doSomethingAfterStore(false,holder.text_store,"2",context);
        return view;
    }

    class Holder {
        @Bind(R.id.img_logo)
        ImageView img_logo;
        @Bind(R.id.text_share)
        TextView text_share;
        @Bind(R.id.text_store)
        TextView text_store;
        @Bind(R.id.text_title)
        TextView text_title;
        @Bind(R.id.text_data)
        TextView text_data;
    }
}
