package com.hxqydyl.app.ys.http.login;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.QueryResultBean;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by hxq on 2016/3/25.
 */
public class UpdatePasswordNet {

    private OnUpdatePasswordListener listener;
    public void setListener(OnUpdatePasswordListener listener){
        this.listener = listener;
    }

    public interface OnUpdatePasswordListener{
        void requestUpdatePwSuc(Query query);
        void requestUpdatePwFail();
    }

    public void updatePassword(String mobile,String password,String captcha){
        Map<String,String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("captcha", captcha);
        params.put("callback", "hxq");
        System.out.println("response--->" + params.toString());
//        params.put("mobile","13671050634");
//        params.put("password", "111111");
        OkHttpClientManager.postAsyn(Constants.LOGIN_URL, params, new ResultCallback<QueryResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestUpdatePwFail();
                System.out.println("response--->");
            }

            @Override
            public void onResponse(QueryResultBean response) throws JSONException {
                listener.requestUpdatePwSuc(response.getQuery());
                System.out.println("response--->" + response.toString());
            }
        });
    }
}
