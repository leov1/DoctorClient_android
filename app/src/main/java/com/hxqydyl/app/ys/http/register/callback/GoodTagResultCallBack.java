package com.hxqydyl.app.ys.http.register.callback;

import com.hxqydyl.app.ys.bean.register.GoodTagResultBean;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by hxq on 2016/3/28.
 */
public abstract class GoodTagResultCallBack extends Callback<GoodTagResultBean>{
    @Override
    public GoodTagResultBean parseNetworkResponse(Response response) throws IOException
    {
        return null;
    }
}
