package com.hxqydyl.app.ys.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.reading.VitamioPlayerActivity;
import com.hxqydyl.app.ys.bean.response.ImageResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.library.RefreshProgressWebView;
import com.hxqydyl.app.ys.ui.web.ProgressWebClient;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.xus.http.httplib.model.PostPrams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import galleryfinal.wq.photo.widget.PickConfig;

/**
 * 网页activity基类
 * Created by hxq on 2016/3/25.
 */
public class BaseWebActivity extends BaseRequstActivity {

    public RefreshProgressWebView webView;
    public boolean isNeedLogin = false;
    private OnLoginSuccess onLoginSuccess;
    private Intent intent;
    private String beanPath;
    private String webIsAvatar;


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
        setWebBackListener(webView.getRefreshableView());
    }

    private void initViews() {
        if (getIntent().hasExtra("beanPath")) {
            beanPath = getIntent().getStringExtra("beanPath");
        }
        initViewOnBaseTitle("加载中...");
        webView = (RefreshProgressWebView) findViewById(R.id.webview);
        initWebSetting();
    }

    public void loadUrl(String url) {
        webView.getRefreshableView().loadUrl(url);
    }

    private void initWebSetting() {
        WebSettings webSettings = webView.getRefreshableView().getSettings();
        webView.setPullToRefreshEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        mChromeClient = new WebClient(this);
        webView.getRefreshableView().setWebViewClient(webViewClient);
//        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);
    }

    public WebChromeClient mChromeClient;

    public WebViewClient webViewClient = new WebViewClient() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            webView.getRefreshableView().loadUrl("file:///android_asset/demo.html");
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
            // Redirect to deprecated method, so you can use it in all SDK versions
//            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
        }

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
            webView.getRefreshableView().loadUrl("javascript:gm.user.setDoctor('" + LoginManager.getDoctorUuid() + "')");
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
                if (!TextUtils.isEmpty(beanPath)) {
                    DoJsBean(functionname, paramater, beanPath);
                }
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private void DoJsBean(String functionname, String parameters, String beanPath) {
        try {
            Class cls = Class.forName(beanPath);
            Object obj = cls.newInstance();
            Class[] param = new Class[3];
            param[0] = FragmentActivity.class;
            param[1] = String.class;
            param[2] = String.class;
            Method med = cls.getMethod("doJs", param);
            med.invoke(obj, this, functionname, parameters);
        } catch (Exception e) {

        }

    }

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
            case "logout":
                UIHelper.ToastMessage(this, "退出登陆");
                LoginManager.quitLogin();
                Intent intent = new Intent();
                intent.putExtra("isLoginOut", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case "fullPage":
                String[] ps = new String[2];
                ps = parameters.split("\\|");
                String sourceUrl = ps[0];
                String duration = ps[1];

                intent = new Intent(this, VitamioPlayerActivity.class);
                intent.putExtra("VideoUrl", sourceUrl);
                intent.putExtra("VideoTitle", duration);
                startActivity(intent);
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
            case "takephoto":
                webIsAvatar = parameters;
                access(PickConfig.MODE_SINGLE_PICK,1);
                break;
//            case "saveImage":
//                new RemoteImageHelper().downloadImage2local(this, parameters, this);
//                break;
//            case "returnIndexPage":
//                returnIndexPage = true;
//                break;

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
        if (isNeedLogin) {
            if (!TextUtils.isEmpty(LoginManager.getDoctorUuid())) {
                onLoginSuccess.onLoginSuccess();
                isNeedLogin = false;
            } else {
                onLoginSuccess.onLoginfail();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.getRefreshableView().canGoBack()) {
            webView.getRefreshableView().goBack();
        }else {
            super.onBackPressed();
        }
    }

    public interface OnLoginSuccess {
        void onLoginSuccess();

        void onLoginfail();

    }

    private void uploadFile(final String filePath) {
        showDialog("正在上传图片");
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PostPrams postPrams = toPostFileParams(toParamsBaen("thumbnail", "true"));
                        File file = new File(filePath);
                        postPrams.addFile(file.getName(), file);
                        toNomalNet(postPrams, ImageResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.UPLOAD_IMGS, "1.0"), null);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        ImageResponse ir = (ImageResponse) bean;
        webView.getRefreshableView().loadUrl("javascript:gm.user.getuserimg('" + ir.value.get(0).getThumbnail() + "','" + ir.value.get(0).getId() + "','" + webIsAvatar + "')");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //为什么不在这处理图片呢？因为处理图片比较耗时，如果在这里处理图片，从图库或者拍照Activity时会不流畅，很卡卡卡，试试就知道了
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            this.intent = intent;
            ArrayList<String> paths = intent.getStringArrayListExtra("data");
            for (int i = 0; i < paths.size(); i++) {
                if (!TextUtils.isEmpty(paths.get(i))) {
                    uploadFile(paths.get(i));
                }
            }

        }
    }


    public class WebClient extends ProgressWebClient {
        public WebClient(Context context) {
            super(context, webView);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                webView.onRefreshComplete();
            }
        }

    }
}
