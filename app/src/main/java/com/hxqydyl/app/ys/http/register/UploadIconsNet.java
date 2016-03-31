package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import okhttp3.Call;

/**
 * 上传图片集
 * Created by hxq on 2016/3/22.
 */
public class UploadIconsNet {

    private OnUploadIconsListener listener;

    public void setListener(OnUploadIconsListener listener){
        this.listener = listener;
    }

    public interface OnUploadIconsListener{
        void requestUploadIconsSuc(Query query);
        void requestUploadIconsFail();
    }

    public void saveIcons(List<String> imgUris){
        System.out.println("response--->"+imgUris.get(0));
        OkHttpUtils.post()
                .url(Constants.UPLOAD_IMGS)
                .addParams("thumbnail", "false")
                .addFile("files", "deefeee.png", new File(imgUris.get(0)))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestUploadIconsFail();
                        System.out.println("onError--->");
                    }

                    @Override
                    public void onResponse(String response) {
                        listener.requestUploadIconsSuc(null);
                       System.out.println("response--->"+response);
                    }
                });

    }
}
