package com.hxqydyl.app.ys.activity.reading;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadingActivity extends BaseTitleActivity implements View.OnClickListener{

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initViews();
        initListeners();
        Random random=new Random();
        loadDataAndURL("http://admin.hxqydyl.com/html/thedoctorinformation/index.shtml?rnd="+random);
    }

    private void loadDataAndURL(String url) {
        webView.loadUrl(url);
    }

    private void initViews(){
        initViewOnBaseTitle("阅读");
        webView = (WebView) findViewById(R.id.webview);
        init();
    }

    private void initListeners() {
        setBackListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }

    WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("goodm://")){//自定义js协议
                Pattern pl = Pattern.compile("goodm://([a-zA-Z0-9]+)(/[\\w\\W]*)?");
                Matcher ml = pl.matcher(url);
                String functionname = "",paramater = "";
                if (ml.find()){
                    functionname = ml.group(1);
                    paramater = ml.group(2);
                }
                if (!TextUtils.isEmpty(paramater)){
                    paramater = paramater.substring(1);
                }
                SetJsBridge(functionname,paramater);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private static final String CLIENT_INTERFACE_NAME = "local_obj";
    private void init() {
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏
//        webView = (WebView) findViewById(R.id.MywebView);
 //       tvTitle = (TextView) findViewById(R.id.title_txt);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
//        String ua = Util.GetUserDefineAgent(this);
//        if(ua!=null){
//            webSettings.setUserAgentString(ua);
//        }
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        // 开启插件（对flash的支持）
        //webSettings.setPluginState(PluginState.ON);//.setPluginsEnabled(true);
        //webSettings.setRenderPriority(RenderPriority.HIGH);
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webSettings.setLoadWithOverviewMode(true);
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(mChromeClient);

        //webView.setHorizontalScrollBarEnabled(false);
        //webView.setVerticalScrollBarEnabled(false);

        webView.addJavascriptInterface(this, CLIENT_INTERFACE_NAME);
//        btn_back = (ImageView) findViewById(R.id.title_back_btn);
        //btn_back.setOnClickListener(this);
//        mRefreshImg = (ImageView) findViewById(R.id.webView_refresh_img);
//        sharebtn =  (ImageView)findViewById(R.id.share_btn);
//        //sharebtn.setOnClickListener(this);
//        // 实例化
//        wxApi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
//        wxApi.registerApp(Constants.WX_APP_ID);
//        //图片上传
//        pop = new PopupWindow(MainActivity.this);
//        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
//        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        pop.setFocusable(true);
//        pop.setOutsideTouchable(true);
//        pop.setContentView(view);
//        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
//        Button bt1 = (Button) view
//                .findViewById(R.id.item_popupwindows_camera);
//        Button bt2 = (Button) view
//                .findViewById(R.id.item_popupwindows_Photo);
//        Button bt3 = (Button) view
//                .findViewById(R.id.item_popupwindows_cancel);
//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        bt1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                photo();
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        bt2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent local = new Intent();
//                local.setType("image/*");
//                local.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(local, TAKEFILE);
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
//        bt3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });
    }
    private WebChromeClient mChromeClient = new WebChromeClient() {
        private View myView = null;
        private CustomViewCallback myCallback = null;
        // Android 使WebView支持HTML5 Video（全屏）播放的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }
            ViewGroup parent = (ViewGroup) webView.getParent();
            parent.removeView(webView);
            parent.addView(view);
            myView = view;
            myCallback = callback;
            mChromeClient = this;
        }

        @Override
        public void onHideCustomView() {
            if (myView != null) {
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView(webView);
                myView = null;
            }
        }
    };
    private void SetJsBridge(String functionname, String parameters) {
        switch (functionname) {
            case "hiddenHeadView":
//                findViewById(R.id.titleid).setVisibility(View.GONE);
                break;
            case "showHeadView":
//                findViewById(R.id.titleid).setVisibility(View.VISIBLE);
                break;
            case "setTitle":
                try {
                    String title = URLDecoder.decode(parameters, "UTF-8");
                    if(title.length()>10){
                        title=title.substring(0,10)+"...";
                    }
     //               tvTitle.setText(title);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
//            case "getFriendList":
//                new ContactHelper().init(this,this);
//                break;
//            case "setLeftMenu":
//                IniLeftMenu(parameters);
//                break;
//            case "setRightMenu":
//                IniRightMenu(parameters);
//                break;
//            case "share":
//                showsharePop();
//                shareobj= new JSONObject(parameters);
//                break;
//            case "openContacts":
//                Intent intent = new Intent(this, ContactListActivity.class);
//                startActivityForResult(intent,CONTRACTCODE);
//                break;
//            case "takephoto":
//                ll_popup.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.activity_translate_in));
//                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//                break;
//            case "saveImage":
//                new RemoteImageHelper().downloadImage2local(this,parameters, this);
//                break;
//            case "returnIndexPage":
//                returnIndexPage=true;
//                break;
//            case "fullPage":
//                String[] ps = new String[2];
//                ps=parameters.split("\\|");
//                String sourceUrl = ps[0];
//                String duration = ps[1];
//                Intent intent2 = new Intent(this, VideoPlayActivity.class);
//                intent2.putExtra("rtspUrl", sourceUrl);
//                intent2.putExtra("duration", duration);
//                startActivityForResult(intent2,FULLPLAY);
//                break;
//            case "openScan":
//                Intent intent3 = new Intent();
//                intent3.setClass(MainActivity.this, MipcaActivityCapture.class);
//                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(intent3, SCANNIN_GREQUEST_CODE);
//                break;
            case "openUrl":
                if(!parameters.contains("http")){
                    parameters="http://"+parameters;
                }
                Uri uri =  Uri.parse(parameters);
                Intent a = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(a);
                break;
//            case "payment":
//                JSONObject obj = new JSONObject(parameters);
//                if(obj.optInt("payType")==1)//1(支付宝)|2(微信),
//                {
//                    AlipayUtils.getInstance().pay(this, parameters, mHandler);
//                }
//                else if(obj.optInt("payType")==2)//
//                {
//                    Tools.GetWxPayOrderInfo(MainActivity.this,obj.optString("order"),this);
//                }
//                break;
//            case "sendUser":
//                String alias =parameters;// aliasEdit.getText().toString().trim();
//                if (TextUtils.isEmpty(alias)) {
//                    Toast.makeText(MainActivity.this, "别名不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!JushUtil.isValidTagAndAlias(alias)) {
//                    Toast.makeText(MainActivity.this,"别名无效", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                //调用JPush API设置Alias
//                mHandlerJpush.sendMessage(mHandlerJpush.obtainMessage(MSG_SET_ALIAS, alias));
//                break;
//            case "setUserInfo"://
//                //{"userType":"1","user":"xxxxxx"} 1(医生)|2(患者)
//                JSONObject u = new  JSONObject(parameters);// aliasEdit.getText().toString().trim();
//                String alias2 = u.optString("user");
//                if (TextUtils.isEmpty(alias2)) {
//                    Toast.makeText(MainActivity.this,"别名不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!JushUtil.isValidTagAndAlias(alias2)) {
//                    Toast.makeText(MainActivity.this,"别名无效", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                //调用JPush API设置Alias
//                mHandlerJpush.sendMessage(mHandlerJpush.obtainMessage(MSG_SET_ALIAS, alias2));
//
//                break;
        }
    }
}
