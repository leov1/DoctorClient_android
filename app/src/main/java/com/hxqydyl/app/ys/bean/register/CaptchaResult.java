package com.hxqydyl.app.ys.bean.register;

import com.hxqydyl.app.ys.bean.Query;

/**
 * 手机验证码
 * Created by hxq on 2016/3/15.
 */
public class CaptchaResult {
    private Query query;
    private String captcha;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "CaptchaResult{" +
                "query=" + query +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
