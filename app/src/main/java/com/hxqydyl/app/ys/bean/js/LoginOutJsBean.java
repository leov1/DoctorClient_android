package com.hxqydyl.app.ys.bean.js;

import android.support.v4.app.FragmentActivity;

import com.hxqydyl.app.ys.activity.BaseWebActivity;
import com.hxqydyl.app.ys.ui.UIHelper;

/**
 * Created by wangxu on 2016/4/11.
 * //本类只是示例
 */
public class LoginOutJsBean extends BaseJsBean {


    @Override
    public void doJs(BaseWebActivity activity, String functionname, String parameters) {
        if (functionname.equals("logout")) {
            UIHelper.ToastMessage(activity, "点击了logout");
        }
    }
}
