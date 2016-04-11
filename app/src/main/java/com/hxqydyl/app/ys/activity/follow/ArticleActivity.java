package com.hxqydyl.app.ys.activity.follow;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.BaseWebActivity;

import com.hxqydyl.app.ys.http.UrlConstants;


/**
 * 患教库列表
 */
public class ArticleActivity extends BaseWebActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewOnBaseTitle("患教库");
        loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.PATIENT_EDUCATION));
    }
}
