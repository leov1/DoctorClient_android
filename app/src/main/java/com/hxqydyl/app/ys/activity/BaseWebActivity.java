package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.video.VideoPlayActivity;
import com.hxqydyl.app.ys.ui.ProgressWebView;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网页activity基类
 * Created by hxq on 2016/3/25.
 */
public class BaseWebActivity extends BaseTitleActivity {
    public ProgressWebView webView;
    private boolean isNeedLogin = false;
    private OnLoginSuccess onLoginSuccess;
    private Intent intent;

    public void setIsNeedLogin(boolean isNeedLogin, OnLoginSuccess onLoginSuccess) {
        this.isNeedLogin = isNeedLogin;
        this.onLoginSuccess = onLoginSuccess;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
        initWebSetting();
        setWebBackListener(webView);
    }

    private void initViews() {
        initViewOnBaseTitle("阅读");
        webView = (ProgressWebView) findViewById(R.id.webview);
        initWebSetting();
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    private void initWebSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
//        webView.setWebChromeClient(mChromeClient);
//        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);
    }

    public WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (isNeedLogin && TextUtils.isEmpty(LoginManager.getDoctorUuid())) {
                UIHelper.showLogin(BaseWebActivity.this);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.loadUrl("javascript:gm.user.setDoctor('" + LoginManager.getDoctorUuid() + "')");

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println("goodm---->" + url);
            if (url.contains("goodm://")) {//自定义js协议
                Pattern pl = Pattern.compile("goodm://([a-zA-Z0-9]+)(/[\\w\\W]*)?");
                Matcher ml = pl.matcher(url);
                String functionname = "", paramater = "";
                if (ml.find()) {
                    functionname = ml.group(1);
                    paramater = ml.group(2);
                }
                if (!TextUtils.isEmpty(paramater)) {
                    paramater = paramater.substring(1);
                }
                SetJsBridge(functionname, paramater);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private void SetJsBridge(String functionname, String parameters) {
        switch (functionname) {
            case "setTitle":
                try {
                    String title = URLDecoder.decode(parameters, "UTF-8");
                    if (title.length() > 10) {
                        title = title.substring(0, 10) + "...";
                    }
                    topTv.setText(title);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
//            case "getFriendList":
//                new ContactHelper().init(this, this);
//                break;
//            case "setLeftMenu":
//                IniLeftMenu(parameters);
//                break;
//            case "setRightMenu":
//                IniRightMenu(parameters);
//                break;
//            case "share":
//                showsharePop();
//                shareobj = new JSONObject(parameters);
//                break;
//            case "openContacts":
//                Intent intent = new Intent(this, ContactListActivity.class);
//                startActivityForResult(intent, CONTRACTCODE);
//                break;
//            case "takephoto":
//                ll_popup.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.activity_translate_in));
//                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//                break;
//            case "saveImage":
//                new RemoteImageHelper().downloadImage2local(this, parameters, this);
//                break;
//            case "returnIndexPage":
//                returnIndexPage = true;
//                break;
            case "fullPage":
                String[] ps = new String[2];
                ps = parameters.split("\\|");
                String sourceUrl = ps[0];
                String duration = ps[1];
                intent = new Intent(this, VideoPlayActivity.class);
                intent.putExtra("VideoUrl", sourceUrl);
                intent.putExtra("VideoTitle", duration);
//                startActivityForResult(intent, FULLPLAY);
                startActivity(intent);
                break;
//            case "openScan":
//                Intent intent3 = new Intent();
//                intent3.setClass(MainActivity.this, MipcaActivityCapture.class);
//                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(intent3, SCANNIN_GREQUEST_CODE);
//                break;
//            case "openUrl":
//                if (!parameters.contains("http")) {
//                    parameters = "http://" + parameters;
//                }
//                Uri uri = Uri.parse(parameters);
//                Intent a = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(a);
//                break;
//            case "payment":
//                JSONObject obj = new JSONObject(parameters);
//                if (obj.optInt("payType") == 1)//1(支付宝)|2(微信),
//                {
//                    AlipayUtils.getInstance().pay(this, parameters, mHandler);
//                } else if (obj.optInt("payType") == 2)//
//                {
//                    Tools.GetWxPayOrderInfo(MainActivity.this, obj.optString("order"), this);
//                }
//                break;
//            case "sendUser":
//                String alias = parameters;// aliasEdit.getText().toString().trim();
//                if (TextUtils.isEmpty(alias)) {
//                    Toast.makeText(MainActivity.this, "别名不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!JushUtil.isValidTagAndAlias(alias)) {
//                    Toast.makeText(MainActivity.this, "别名无效", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //调用JPush API设置Alias
//                mHandlerJpush.sendMessage(mHandlerJpush.obtainMessage(MSG_SET_ALIAS, alias));
//                break;
//            case "setUserInfo"://
//                //{"userType":"1","user":"xxxxxx"} 1(医生)|2(患者)
//                JSONObject u = new JSONObject(parameters);// aliasEdit.getText().toString().trim();
//                String alias2 = u.optString("user");
//                if (TextUtils.isEmpty(alias2)) {
//                    UIHelper.ToastMessage(this, "别名不能为空");
//                    return;
//                }
//                if (!JushUtil.isValidTagAndAlias(alias2)) {
//                    UIHelper.ToastMessage(this, "别名无效");
//                    return;
//                }
//
//                //调用JPush API设置Alias
//                mHandlerJpush.sendMessage(mHandlerJpush.obtainMessage(MSG_SET_ALIAS, alias2));

//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedLogin){
            if (!TextUtils.isEmpty(LoginManager.getDoctorUuid())){
                onLoginSuccess.onLoginSuccess();
            }else{
                onLoginSuccess.onLoginfail();
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnLoginSuccess {
        void onLoginSuccess();

        void onLoginfail();

    }
}
