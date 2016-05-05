package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hxqydyl.app.ys.bean.js.BaseJsBean;


/**
 * Created by admin on 2016/4/6.
 */
public class CommentWebActivity extends BaseWebActivity implements BaseWebActivity.OnLoginSuccess {
    private String url;

    public static void toCommentWeb(String url, String title, Activity a, boolean isNeedLogin) {
        Intent intent = new Intent(a, CommentWebActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("title", title);
        }
        intent.putExtra("isNeedLogin", isNeedLogin);
        intent.putExtra("url", url);
        a.startActivity(intent);
    }
    public static void toCommentWeb(String url, String title, Activity a, boolean isNeedLogin,String className) {
        Intent intent = new Intent(a, CommentWebActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("title", title);
        }
        intent.putExtra("beanPath", BaseJsBean.getPackgeName(className));
        intent.putExtra("isNeedLogin", isNeedLogin);
        intent.putExtra("url", url);
        a.startActivity(intent);
    }

    public static void toCommentWebForResult(String url,Activity activity,int resultCode,boolean isNeedLogin){
        Intent intent = new Intent(activity,CommentWebActivity.class);
        intent.putExtra("isNeedLogin", isNeedLogin);
        intent.putExtra("url",url);
        activity.startActivityForResult(intent, resultCode);
    }

    //
    public static void toCommentWebForResult(String url,Activity activity,int resultCode){
        toCommentWebForResult(url,activity,resultCode,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        setIsNeedLogin(getIntent().getBooleanExtra("isNeedLogin", false), this);
        loadUrl(url);
    }

    @Override
    public void onLoginSuccess() {
       loadUrl(url);
    }

    @Override
    public void onLoginfail() {
        this.finish();
    }
}
