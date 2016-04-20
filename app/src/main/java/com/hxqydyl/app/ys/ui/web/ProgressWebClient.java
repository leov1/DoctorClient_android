package com.hxqydyl.app.ys.ui.web;

import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by wangxu on 2016/4/15.
 */
public class ProgressWebClient extends android.webkit.WebChromeClient {
    private ProgressBar progressbar;

    public  ProgressWebClient(ProgressBar progressbar) {
        this.progressbar = progressbar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            if (progressbar.getVisibility() == View.GONE)
                progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}

