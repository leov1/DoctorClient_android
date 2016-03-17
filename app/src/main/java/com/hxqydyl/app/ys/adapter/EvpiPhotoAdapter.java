package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.register.Bimp;

/**
 * Created by hxq on 2016/3/16.
 */
public class EvpiPhotoAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int selectedPositon = -1;
    private boolean shape;
    private Context context;

    public boolean isShape(){
        return shape;
    }

    public void setShape(boolean shape){
        this.shape = shape;
    }

    public EvpiPhotoAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (Bimp.tempSelectBitmap.size() == 6){
            return 6;
        }
        return Bimp.tempSelectBitmap.size()+1;
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
            convertView = inflater.inflate(R.layout.item_evpi_photo_gridview,parent,false);
        }
        ImageView img = BaseViewHolder.get(convertView,R.id.imge);
        if (position==Bimp.tempSelectBitmap.size()){
            img.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.photo_rect_add));
            if (position == 6){
                img.setVisibility(View.GONE);
            }
        }else {
            img.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
        }
        return convertView;
    }

    public void update(){
        loading();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void loading(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (Bimp.max == Bimp.tempSelectBitmap.size()){
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    }else {
                        Bimp.max += 1;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();
    }
}
