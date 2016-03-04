package com.hxqydyl.app.ys.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

public class BaseTitleActivity extends SwipeBackActivity {

    private ImageView backImg;
    private TextView topTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initViewOnBaseTitle(String title){
        try{
            backImg = (ImageView) findViewById(R.id.back_img);
            topTv = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(title)){
                topTv.setText(title);
            }else{
                topTv.setVisibility(View.GONE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setBackListener(View.OnClickListener l){
        backImg.setOnClickListener(l);
    }
}