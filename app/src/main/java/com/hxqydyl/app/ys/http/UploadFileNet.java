package com.hxqydyl.app.ys.http;

import com.hxqydyl.app.ys.bean.Pic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by white_ash on 2016/4/4.
 */
public class UploadFileNet extends BaseNet {
    public UploadFileNet(NetRequestListener listener) {
        super(listener);
    }

    public void uploadPic(File[] files){
        if(files == null){
            return;
        }
        final String shortUrl = UrlConstants.UPLOAD_PIC;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post();
        for(int i =0;i<files.length;i++){
            postFormBuilder.addFile("files",files[i].getName(),files[i]);
        }
        postFormBuilder
                .url(UrlConstants.getWholeApiUrl(shortUrl))
                .addParams("thumbnail","true")
                .addHeader("Accept","application/json")
                .build()
                .execute(new Callback<ArrayList<Pic>>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND,shortUrl,null);
                    }

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);
                        callListener(ON_PROGRESS,shortUrl,Float.valueOf(progress));
                    }

                    @Override
                    public ArrayList<Pic> parseNetworkResponse(Response response) throws Exception {
                        ArrayList<Pic> pics = new ArrayList<Pic>();
                        JSONArray array = new JSONArray(response.body().string());
                        if(array!=null && array.length()>0){
                            for(int i=0;i<array.length();i++){
                                pics.add(jsonToPic(array.getJSONObject(i)));
                            }
                        }
                        return pics;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR,shortUrl,e);
                    }

                    @Override
                    public void onResponse(ArrayList<Pic> response) {
                        callListener(ON_RESPONSE,shortUrl,response);
                    }
                });
    }

    private Pic jsonToPic(JSONObject jsonObject) {
        Pic pic = new Pic();
        pic.setId(jsonObject.optString("id"));
        pic.setUrl(jsonObject.optString("url"));
        pic.setThumbUrl(jsonObject.optString("thumbnail"));
        return pic;
    }

}
