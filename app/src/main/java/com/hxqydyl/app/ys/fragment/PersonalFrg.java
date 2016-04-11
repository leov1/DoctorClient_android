package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;
import android.view.View;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.activity.MainActivity;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.Constants;
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
        webView.loadUrl(Constants.USER_INFO + LoginManager.getDoctorUuid());
        rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentWebActivity.toCommentWebForResult(Constants.USER_SETTING, getActivity(), UIHelper.LOGINOUT_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isQuit_user){
            LoginManager.isQuit_user = false;
            webView.loadUrl(Constants.USER_INFO + LoginManager.getDoctorUuid());
        }
    }

    @Override
    public void doJs(String url) {
        if (url != null){
            CommentWebActivity.toCommentWeb(url, null, getActivity(), true);
        }
    }
}
