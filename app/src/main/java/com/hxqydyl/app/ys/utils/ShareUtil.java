package com.hxqydyl.app.ys.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.ShareBean;
import com.hxqydyl.app.ys.common.AppContext;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by wangxu on 2016/6/1.
 */
public class ShareUtil {
    public static ShareUtil util;
    private Activity context;

    public synchronized static ShareUtil getIntense(Activity context) {
        if (util == null) {
            util = new ShareUtil(context);
            umShareListener=  new UMShareListener() {
                @Override
                public void onResult(SHARE_MEDIA platform) {
                    Log.e("wangxu", platform + " 分享成功啦");

                    Toast.makeText(AppContext.getInstance(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(SHARE_MEDIA platform, Throwable t) {
                    Log.e("wangxu", platform + " 分享失败啦");

                    Toast.makeText(AppContext.getInstance(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(SHARE_MEDIA platform) {
                    Log.e("wangxu", platform + " 分享取消了");
                    Toast.makeText(AppContext.getInstance(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                }
            };
        }
        return util;
    }

    public ShareUtil(Activity context) {
        this.context = context;
    }

    private static  UMShareListener umShareListener ;

    public void showShareDialog(ShareBean bean) throws UnsupportedEncodingException {
        Log.e("wangxu","showShareDialog");
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(context).setDisplayList(displaylist)
                .withText(URLDecoder.decode(bean.getDesc(),"UTF-8"))
                .withTitle(URLDecoder.decode(bean.getTitle(),"UTF-8"))
                .withTargetUrl(bean.getLink()).withMedia(TextUtils.isEmpty(bean.getImg())?new UMImage(context, R.mipmap.ic_logo):new UMImage(context, bean.getImg()))
                .setListenerList(umShareListener)
                .open();
    }
}
