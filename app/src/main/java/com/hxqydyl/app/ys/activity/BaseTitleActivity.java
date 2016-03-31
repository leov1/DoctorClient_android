package com.hxqydyl.app.ys.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseTitleActivity extends SwipeBackActivity {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    private ImageView backImg;
    private TextView topTv;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initViewOnBaseTitle(String title) {
        try {
            backImg = (ImageView) findViewById(R.id.back_img);
            topTv = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(title)) {
                topTv.setText(title);
            } else {
                topTv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackListener(View.OnClickListener l) {
        backImg.setOnClickListener(l);
    }

    public void setBackListener() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showDialog(String text){
        pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(text);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void dismissDialog(){
        if (pDialog != null && pDialog.isShowing()){
            pDialog.dismissWithAnimation();
        }
    }

}
