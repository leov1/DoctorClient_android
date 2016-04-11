package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hxqydyl.app.ys.utils.LoginManager;


/**
 * Created by admin on 2016/4/6.
 */
public class CommentWebActivity extends BaseWebActivity implements BaseWebActivity.OnLoginSuccess {
    private String url;
    private String title;
    private boolean isfrist = true;   //应该需要传所需的方法对象

    public static void toCommentWeb(String url, String title, Activity a, boolean isNeedLogin) {
        Intent intent = new Intent(a, CommentWebActivity.class);
        intent.putExtra("isNeedLogin", isNeedLogin);
        intent.putExtra("url", url);
        a.startActivity(intent);
    }

    //
    public static void toCommentWebForResult(String url,Activity activity,int resultCode){
        Intent intent = new Intent(activity,CommentWebActivity.class);
        intent.putExtra("url",url);
        activity.startActivityForResult(intent, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("wangxu",TextUtils.isEmpty(LoginManager.getDoctorUuid())+"");

        url = getIntent().getStringExtra("url");
        setIsNeedLogin(getIntent().getBooleanExtra("isNeedLogin", false), this);
        loadUrl(url);
    }

    @Override
    public void onLoginSuccess() {
        if (!isfrist) {
            loadUrl(url);
        }
        isfrist=false;
    }

    @Override
    public void onLoginfail() {
        this.finish();
    }
}
