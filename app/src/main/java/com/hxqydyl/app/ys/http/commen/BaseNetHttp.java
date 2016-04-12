package com.hxqydyl.app.ys.http.commen;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by admin on 2016/4/7.
 */
public class BaseNetHttp {


    public Gson gson;

    public enum HttpType {
        HttpGet, HttpPost, HttpPostString, HttpDelect, HttpPut, HttpPostile
    }

    public interface OnResumeListener {
        void onSuccess(Object o);

        void onFail(String s);
    }

    public <T> void getNetForCommen(HttpType type, RequestModel<T> requestModel) {
        if (gson == null) {
            gson = new Gson();
        }
        if (type == HttpType.HttpGet) {
            toGet(requestModel);
        } else if (type == HttpType.HttpPost) {
            toPost(requestModel);
        } else if (type == HttpType.HttpDelect) {

        } else if (type == HttpType.HttpPut) {

        } else if (type == HttpType.HttpPostile) {

        }
    }

    public <T> void toGet(final RequestModel<T> requestModel) {
        GetBuilder getBuilder = OkHttpUtils.get().url("");
        try {
            if (requestModel.getMaps()!=null)
            addParams(requestModel.getMaps(), getBuilder);
        } catch (Exception e) {
            requestModel.getOnResumeListener().onFail("请求map转换失败");
        }
        getBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        requestModel.getOnResumeListener().onFail(e.toString() + "");
                    }

                    @Override
                    public void onResponse(String response) {
                        requestModel.getOnResumeListener().onSuccess(gson.fromJson(response, requestModel.getT()));
                    }
                });
    }


    public <T> void toPost(final RequestModel<T> requestModel) {
        try {
            OkHttpUtils
                    .post()
                    .url(requestModel.getUrl())
                    .params(requestModel.getMaps())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            requestModel.getOnResumeListener().onFail(e.toString() + "");
                        }

                        @Override
                        public void onResponse(String response) {
                           Object o=   gson.fromJson(StringUtils.cutoutBracketToString(response), requestModel.getT());
                            requestModel.getOnResumeListener().onSuccess(o);
                        }
                    });
        } catch (Exception e1) {
            requestModel.getOnResumeListener().onFail("请求map转换失败");
        }

    }

    public void addParams(Map<String, String> map, GetBuilder builder) {
        if (map != null) {
            for (String key : map.keySet()) {
                builder.addParams(key, map.get(key));
            }
        }
    }


}
