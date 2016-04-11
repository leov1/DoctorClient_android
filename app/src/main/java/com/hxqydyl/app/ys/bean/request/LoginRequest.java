package com.hxqydyl.app.ys.bean.request;


import com.hxqydyl.app.ys.http.UrlConstants;

/**
 * Created by wangxu on 2016/4/7.
 */
public class LoginRequest extends BaseRequest {
    public LoginRequest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
    public String mobile;
    public String password;
    public String callback = UrlConstants.CALLBACK;

}
