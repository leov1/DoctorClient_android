package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 *我的任务
 */
public class MyTaskFrg extends BaseWebFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("我的任务", view);
        webView.loadUrl("javascript:gm.user.setDoctor("+ LoginManager.getDoctorUuid()+")");
        webView.loadUrl(Constants.MY_TASK);
    }

}
