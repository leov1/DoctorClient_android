package com.hxqydyl.app.ys.ui.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hxqydyl.app.ys.R;

/**
 * 带进度条的webview
 * Created by hxq on 2016/4/5.
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView{
    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, 8, 0, 0));
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.wevbview_progressbar));
        addView(progressbar);
        setWebChromeClient(new ProgressWebClient(progressbar));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
