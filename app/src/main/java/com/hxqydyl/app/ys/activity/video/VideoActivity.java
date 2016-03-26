package com.hxqydyl.app.ys.activity.video;


import android.os.Bundle;
import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.utils.Constants;

/**
 * 视频列表
 */
public class VideoActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDataAndURL(Constants.GET_VIDEOS);
    }

    private void loadDataAndURL(String url) {
        webView.loadUrl(url);
    }
}
