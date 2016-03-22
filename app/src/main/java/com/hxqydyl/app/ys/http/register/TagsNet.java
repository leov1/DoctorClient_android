package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import org.json.JSONException;

/**
 * 标签
 * Created by hxq on 2016/3/21.
 */
public class TagsNet {

    private OnTagsListener listener;

    public void setListener(OnTagsListener listener){
        this.listener = listener;
    }

    public interface OnTagsListener{
        void requestTagsSuc(TagsResultBean tagsResultBean);
        void requestTagsFail();
    }

    public void obtainTags(){
        System.out.println("tagsResultBean--->"+Constants.GET_TAGS);
        OkHttpClientManager.getAsyn(Constants.GET_TAGS, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                listener.requestTagsFail();
            }

            @Override
            public void onResponse(String response) throws JSONException {
                TagsResultBean tagsResultBean = JsonUtils.JsonTagsResult(response);
                listener.requestTagsSuc(tagsResultBean);
            }
        });
    }
}
