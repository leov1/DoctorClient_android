package com.hxqydyl.app.ys.http.register;

import com.hxqydyl.app.ys.bean.register.IconBean;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 上传图片集
 * Created by hxq on 2016/3/22.
 */
public class UploadIconsNet {

    private OnUploadIconsListener listener;

    public void setListener(OnUploadIconsListener listener) {
        this.listener = listener;
    }

    public interface OnUploadIconsListener {
        void requestUploadIconsSuc(ArrayList<IconBean> iconBeans);

        void requestUploadIconsFail();
    }

    public void saveIcons(List<String> imgUris) {
        System.out.println("response--->" + imgUris.get(0));

        PostFormBuilder postFormBuilder = OkHttpUtils.post()
                .url(UrlConstants.getWholeApiUrl(UrlConstants.UPLOAD_IMGS))
                .addParams("thumbnail", "false")
                .addHeader("accept", "text/html;q=0.9, text/plain;q=0.8, text/xml, application/xml, application/json, application/xhtml+xml, application/octet-stream, */*;q=0.5");

        for (int i = 0; i < imgUris.size(); i++) {
            postFormBuilder = postFormBuilder.addFile("files", "take_pic" + i + ".png", new File(imgUris.get(i)));
        }

        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.requestUploadIconsFail();
                        System.out.println("onError--->" + call.toString() + "---" + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("response--->" + response);
                        try {
                            listener.requestUploadIconsSuc(JsonUtils.JsonIcons(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
}
