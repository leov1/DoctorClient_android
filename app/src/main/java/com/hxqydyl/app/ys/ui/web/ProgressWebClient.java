package com.hxqydyl.app.ys.ui.web;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.hxqydyl.app.ys.R;

/**
 * Created by wangxu on 2016/4/15.
 */
public class ProgressWebClient extends android.webkit.WebChromeClient {
    private ProgressBar progressbar;
    public  ProgressWebClient(ProgressBar progressbar) {
        this.progressbar = progressbar;
    }
    public  ProgressWebClient(Context context,ViewGroup v) {
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 8, 0, 0));
        progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.wevbview_progressbar));
        v.addView(progressbar);
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

