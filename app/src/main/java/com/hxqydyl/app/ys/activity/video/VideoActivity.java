package com.hxqydyl.app.ys.activity.video;


import android.os.Bundle;
import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.http.UrlConstants;

/**
 * 视频列表
 */
public class VideoActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.GET_VIDEOS));
    }

}
