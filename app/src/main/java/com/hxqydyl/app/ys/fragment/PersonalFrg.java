package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 * 个人中心
 */
public class PersonalFrg extends BaseWebFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("个人中心", view);
        webView.loadUrl(Constants.USER_INFO);
    }
}
