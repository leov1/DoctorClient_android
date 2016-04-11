package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 修改密码
 * Created by hxq on 2016/3/25.
 */
public class UpdatePasswordNet {

    private OnUpdatePasswordListener listener;

    public void setListener(OnUpdatePasswordListener listener) {
        this.listener = listener;
    }

    public interface OnUpdatePasswordListener {
        void requestUpdatePwSuc(Query query);

        void requestUpdatePwFail();
    }

    public void updatePassword(String mobile, String password, String captcha) {
        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.UPDATE_PASSWORD))
                .addParams("mobile", mobile)
                .addParams("password", password)
                .addParams("captcha", captcha)
                .addParams("callback", "hxq")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestUpdatePwFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            listener.requestUpdatePwSuc(JsonUtils.JsonUpdatePw(StringUtils.cutoutBracketToString(response)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
}
