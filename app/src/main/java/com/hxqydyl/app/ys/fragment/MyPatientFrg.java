package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;

import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 *我的患者
 */
public class MyPatientFrg extends BaseWebFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("我的患者", view);
        webView.loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_PATIENT));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isQuit_myPatient){
            LoginManager.isQuit_myPatient = false;
            webView.loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.MY_PATIENT));
        }
    }
}
