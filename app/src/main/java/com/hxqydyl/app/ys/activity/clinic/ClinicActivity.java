package com.hxqydyl.app.ys.activity.clinic;

import android.app.Activity;
import android.os.Bundle;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.utils.Constants;

/**
 * 诊所
 */
public class ClinicActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.loadUrl(Constants.GET_CLINIC);
    }
}
