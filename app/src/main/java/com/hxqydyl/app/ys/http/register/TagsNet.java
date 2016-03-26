package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
        System.out.println("tagsResultBean--->" + Constants.GET_TAGS);

        OkHttpUtils
                .post()
                .url(Constants.GET_TAGS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
     //                   mListener.requestLoginNetFail(Constants.REQUEST_FAIL);
                    }

                    @Override
                    public void onResponse(String response) {
//               mListener.requestLoginNetSuccess(JsonUtils.JsonLoginData(response));
                    }
                });

//        OkHttpClientManager.postAsyn(Constants.GET_TAGS, (Map)null, new ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//                System.out.println("onError--->");
//            }
//
//            @Override
//            public void onResponse(String response) throws JSONException {
//                System.out.println("tagsResultBean--->" + response);
//                TagsResultBean tagsResultBean = JsonUtils.JsonTagsResult(response);
//                listener.requestTagsSuc(tagsResultBean);
//            }
//        });
    }
}
