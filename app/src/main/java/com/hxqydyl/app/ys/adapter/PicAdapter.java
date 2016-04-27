package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by wangxu on 2016/4/26.
 */
public class PicAdapter extends BaseAdapter {
    private List<String> imgs;
    private Context context;
    private DisplayImageOptions options;


    public PicAdapter(Context context, List<String> imgs) {
        this.context = context;
        this.imgs = imgs;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(context, 50)))
                .showImageForEmptyUri(R.drawable.default_image)
                .showImageOnFail(R.drawable.default_image)
                .build();
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public String getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_evpi_photo_gridview, null);
        }
        ImageView imageView = BaseViewHolder.get(convertView, R.id.imge);
        ImageLoader.getInstance().displayImage(getItem(position),imageView,options);
        return convertView;
    }
}
