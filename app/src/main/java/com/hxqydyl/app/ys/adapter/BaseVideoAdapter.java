package com.hxqydyl.app.ys.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

/**
 * Created by alice_company on 2016/5/31.
 */
public class BaseVideoAdapter extends BaseAdapter {

    /**
     * 收藏
     * @param isStore
     * @param textStore
     * @param num
     * @param context
     */
    public void doSomethingAfterStore(boolean isStore, TextView textStore, String num, Context context) {
        if (isStore) {
            textStore.setTextColor(context.getResources().getColor(R.color.red));
            textStore.setCompoundDrawablesWithIntrinsicBounds(null, context.getResources().getDrawable(R.mipmap.radio_button_bg_checked), null, null);
        } else {
            textStore.setTextColor(context.getResources().getColor(R.color.black));
            textStore.setCompoundDrawablesWithIntrinsicBounds(null, context.getResources().getDrawable(R.mipmap.radio_button_bg_normal), null, null);
        }
        textStore.setText(num);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
