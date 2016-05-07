package com.hxqydyl.app.ys.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.ui.library.RefreshProgressWebView;
import com.hxqydyl.app.ys.ui.web.ProgressWebClient;
import com.hxqydyl.app.ys.ui.web.ProgressWebView;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hxq on 2016/3/26.
 */
public class BaseWebFragment extends BaseFragment {

    public View view;
    public RefreshProgressWebView webView;
    private boolean isCustom = false;
    private DoJsBridge doJsBridge;
    private boolean isNeedLogin = false;
    public WebChromeClient mChromeClient ;

    public void setIsNeedLogin(boolean isNeedLogin) {
        this.isNeedLogin = isNeedLogin;
    }

    public void setCustomInterceptor(DoJsBridge doJsBridge) {
        isCustom = true;
        this.doJsBridge = doJsBridge;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_web, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        webView = (RefreshProgressWebView) view.findViewById(R.id.webview);
        initWebSetting();
    }


    private void initWebSetting() {
        WebSettings webSettings = webView.getRefreshableView().getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = this.getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        mChromeClient = new WebClient(getActivity());
        webView.setPullToRefreshEnabled(false);

        webView.getRefreshableView().setWebViewClient(webViewClient);
        webView.getRefreshableView().setWebChromeClient(mChromeClient);

//        webView.setWebChromeClient(mChromeClient);
//        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);
    }

    public WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (isNeedLogin && TextUtils.isEmpty(LoginManager.getDoctorUuid())) {
                UIHelper.showLogin(getActivity());
            }
            super.onPageStarted(view, url, favicon);
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
                return true;
            }
            if (isCustom) {
                doJsBridge.doJs(url);
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
//                    topTv.setText(title);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
//            case "takephoto":
//                ll_popup.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.activity_translate_in));
//                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//                break;
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
//            case "saveImage":
//                new RemoteImageHelper().downloadImage2local(this, parameters, this);
//                break;
//            case "returnIndexPage":
//                returnIndexPage = true;
//                break;
//            case "fullPage":
//                String[] ps = new String[2];
//                ps = parameters.split("\\|");
//                String sourceUrl = ps[0];
//                String duration = ps[1];
//                Intent intent2 = new Intent(this, VideoPlayActivity.class);
//                intent2.putExtra("rtspUrl", sourceUrl);
//                intent2.putExtra("duration", duration);
//                startActivityForResult(intent2, FULLPLAY);
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
            case "setUserInfo"://
                //{"userType":"1","user":"xxxxxx"} 1(医生)|2(患者)
                JSONObject u = null;// aliasEdit.getText().toString().trim();
                try {
                    u = new JSONObject(parameters);
                    System.out.println("js--->" + parameters);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                String alias2 = u.optString("user");
//                if (TextUtils.isEmpty(alias2)) {
//                    UIHelper.ToastMessage(this.getActivity(), "别名不能为空");
//                    return;
//                }
//                if (!JushUtil.isValidTagAndAlias(alias2)) {
//                    UIHelper.ToastMessage(this.getActivity(), "别名无效");
//                    return;
//                }
//
                //调用JPush API设置Alias
//                mHandlerJpush.sendMessage(mHandlerJpush.obtainMessage(1, alias2));

                break;
        }
    }

    public interface DoJsBridge {
        void doJs(String url);
    }
    public class WebClient extends ProgressWebClient {
        public WebClient(Context context) {
            super(context,webView);
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                webView.onRefreshComplete();
            }
        }
        //扩展浏览器上传文件
        //3.0++版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        }

        //3.0--版本
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        }

        // For Android > 5.0
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
            return true;
        }
    }
}
