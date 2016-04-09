package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.SharedPreferences;

/**
 * Created by hxq on 2016/2/27.
 */
public class BaseFragment extends Fragment{

    public ImageView backImg;
    public ImageView rightImg;
    private TextView topTv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initViewOnBaseTitle(String title,View v) {
        try {
            backImg = (ImageView) v.findViewById(R.id.back_img);
            topTv = (TextView) v.findViewById(R.id.title_name);
            rightImg = (ImageView) v.findViewById(R.id.right_img);
            backImg.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(title)) {
                topTv.setText(title);
            } else {
                topTv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
