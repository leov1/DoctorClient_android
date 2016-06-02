package com.hxqydyl.app.ys.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.hxqydyl.app.ys.R;

/**
 * Created by wangxu on 2016/6/2.
 */
public class TranslateActivity extends BaseRequstActivity {
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_translate);
        initViewOnBaseTitle(title + "");
       setBackListener();
    }
}
