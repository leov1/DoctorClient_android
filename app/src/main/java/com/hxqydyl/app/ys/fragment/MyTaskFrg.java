package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.utils.Constants;
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
        setIsNeedLogin(true);
        webView.loadUrl("javascript:gm.user.setDoctor(" + LoginManager.getDoctorUuid() + ")");
        webView.loadUrl(Constants.MY_TASK);
    }

    @Override
    public void doJs(String url) {
        if (url != null) {
                CommentWebActivity.toCommentWeb(url,null,getActivity(),true);
        }

    }
}
