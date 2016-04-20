package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 * 我的任务
 */
public class MyTaskFrg extends BaseWebFragment implements BaseWebFragment.DoJsBridge {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("我的任务", view);
        setCustomInterceptor(this);
        setIsNeedLogin(false);
        webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_TASK));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isQuit_myTask){
            LoginManager.isQuit_myTask = false;
            webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_TASK));
        }
    }

    @Override
    public void doJs(String url) {
        if (url != null) {
                CommentWebActivity.toCommentWeb(url,null,getActivity(),true);
        }

    }
}
