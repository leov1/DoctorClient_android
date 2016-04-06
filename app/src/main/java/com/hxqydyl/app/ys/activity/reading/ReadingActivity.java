package com.hxqydyl.app.ys.activity.reading;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.utils.Constants;

public class ReadingActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewOnBaseTitle("阅读");
        loadUrl(Constants.GET_READING);
    }

}
