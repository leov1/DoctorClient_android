package com.hxqydyl.app.ys.activity.clinic;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.http.UrlConstants;

/**
 * 诊所
 */
public class ClinicActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.GET_CLINIC));
    }
}
