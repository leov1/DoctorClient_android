package com.hxqydyl.app.ys.fragment;

import android.os.Bundle;

import com.hxqydyl.app.ys.activity.CommentWebActivity;
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
        webView.loadUrl(Constants.USER_INFO);
    }

    @Override
    public void doJs(String url) {
        if (url != null){
            CommentWebActivity.toCommentWeb(url,null,getActivity(),true);
        }
    }
}
