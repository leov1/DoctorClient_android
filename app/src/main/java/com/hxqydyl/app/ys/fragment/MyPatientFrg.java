package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 *我的患者
 */
public class MyPatientFrg extends BaseWebFragment implements BaseWebFragment.DoJsBridge{

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("我的患者", view);
        setCustomInterceptor(this);
        webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_PATIENT));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isQuit_myPatient){
            LoginManager.isQuit_myPatient = false;
            webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_PATIENT));
        }
    }

    @Override
    public void doJs(String url) {
        if (url != null) {
            CommentWebActivity.toCommentWeb(url,null,getActivity(),true);
        }
    }
}
