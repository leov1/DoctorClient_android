package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

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

        OkHttpUtils
                .post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.GET_TAGS))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                         listener.requestTagsFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        TagsResultBean tagsResultBean = null;
                        try {
                            tagsResultBean = JsonUtils.JsonTagsResult(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.requestTagsSuc(tagsResultBean);
                    }
                });
    }
}
