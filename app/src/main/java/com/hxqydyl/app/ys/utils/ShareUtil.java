package com.hxqydyl.app.ys.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxu on 2016/6/1.
 */
public class ShareUtil {
    public static ShareUtil util;
    private Activity context;
    private String[] allOptionsMenuTexts = {"微信好友", "朋友圈"};
    private int[] allOptionsMenuIcons = {R.drawable.umeng_socialize_wechat,
            R.drawable.umeng_socialize_wxcircle
    };
    private SHARE_MEDIA[] displaylist = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE
    };

    public synchronized static ShareUtil getIntense(Activity context) {
        util = new ShareUtil(context);
        umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(AppContext.getInstance(), " 分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {

                Toast.makeText(AppContext.getInstance(), " 分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(AppContext.getInstance(), " 您分享了取消", Toast.LENGTH_SHORT).show();
            }
        };
        return util;
    }

    public ShareUtil(Activity context) {
        this.context = context;
    }

    private static UMShareListener umShareListener;

    public void showShareDialog(ShareBean bean) throws UnsupportedEncodingException {
        showDialog(bean);
//        Log.e("wangxu", "showShareDialog");
//        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
//                {
//
//                };
////        , SHARE_MEDIA.SINA,
////        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
//        new ShareAction(context).setDisplayList(displaylist)
//                .withText(URLDecoder.decode(bean.getDesc(), "UTF-8"))
//                .withTitle(URLDecoder.decode(bean.getTitle(), "UTF-8"))
//                .withTargetUrl(bean.getLink()).withMedia(TextUtils.isEmpty(bean.getImg()) ? new UMImage(context, R.mipmap.ic_logo) : new UMImage(context, bean.getImg()))
//                .setListenerList(umShareListener)
//                .open();
    }


    public void showDialog(final ShareBean bean) {
        final Dialog dialog = new Dialog(context, R.style.FullScreenDialog);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //获取自定义布局
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View menuView = layoutInflater.inflate(R.layout.dialog_share, null);
        //获取GridView组件并配置适配器
        GridView gridView = (GridView) menuView.findViewById(R.id.gridview);
        Button chance = (Button) menuView.findViewById(R.id.chance);
        chance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        SimpleAdapter menuSimpleAdapter = createSimpleAdapter(allOptionsMenuTexts, allOptionsMenuIcons);
        gridView.setAdapter(menuSimpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialog.dismiss();
                new ShareAction(context)
                        .setPlatform(displaylist[position])
                        .setCallback(umShareListener)
                        .withText(TextUtils.isEmpty(bean.getDesc()) ? "" : bean.getDesc())
                        .withTitle(TextUtils.isEmpty(bean.getTitle()) ? "" : bean.getTitle())
                        .withTargetUrl(TextUtils.isEmpty(bean.getLink()) ? "" : bean.getLink()).withMedia(TextUtils.isEmpty(bean.getImg()) ? new UMImage(context, R.mipmap.ic_logo) : new UMImage(context, bean.getImg()))
                        .setListenerList(umShareListener)
                        .share();
            }
        });

        dialog.addContentView(menuView, params);
        //创建对话框并显示
//        AlertDialog     dialog  =new AlertDialog.Builder(context,R.style.popupDialog).setView(menuView).show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        dialog.show();
    }


    public SimpleAdapter createSimpleAdapter(String[] menuNames, int[] menuImages) {
        List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
        String[] fromsAdapter = {"item_text", "item_image"};
        int[] tosAdapter = {R.id.item_text, R.id.item_image};
        for (int i = 0; i < menuNames.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(fromsAdapter[0], menuNames[i]);
            map.put(fromsAdapter[1], menuImages[i]);
            data.add(map);
        }
        SimpleAdapter SimpleAdapter = new SimpleAdapter(context, data, R.layout.share_item, fromsAdapter, tosAdapter);
        return SimpleAdapter;
    }
}
