package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;
import android.view.View;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 * 个人中心
 */
public class PersonalFrg extends BaseWebFragment implements BaseWebFragment.DoJsBridge{

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewOnBaseTitle("个人中心", view);
        setCustomInterceptor(this);
        rightImg.setVisibility(View.VISIBLE);
        webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.USER_INFO) + LoginManager.getDoctorUuid());
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentWebActivity.toCommentWebForResult(UrlConstants.getWholeApiUrl(UrlConstants.USER_SETTING), getActivity(), UIHelper.LOGINOUT_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isQuit_user){
            LoginManager.isQuit_user = false;
            webView.getRefreshableView().loadUrl(UrlConstants.getWholeApiUrl(UrlConstants.USER_INFO) + LoginManager.getDoctorUuid());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            webView.getRefreshableView().reload();
    }

    @Override
    public void doJs(String url) {
        if (url != null){
            CommentWebActivity.toCommentWeb(url, null, getActivity(), true);
        }
    }
}
