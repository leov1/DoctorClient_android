package com.hxqydyl.app.ys.http.sendMsg;

import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 群发
 * Created by hxq on 2016/4/5.
 */
public class SendMsgToPaNet {

    private OnSendMsgToPaListener listener;
    public void setListener(OnSendMsgToPaListener listener){
        this.listener = listener;
    }

    public interface OnSendMsgToPaListener{
        void sendMsgSuc(Query query);
        void sendMsgFail();
    }

    public void sendMsgToPa(String customerUuids,String doctorUuid,String content){

        OkHttpUtils.get().url(Constants.ADD_INNER_MSG)
                .addParams("customerUuids",customerUuids)
                .addParams("doctorUuid",doctorUuid)
                .addParams("content",content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        System.out.println("onError---");
                        listener.sendMsgFail();
                    }

                    @Override
                    public void onResponse(String response) {
                        System.out.println("onResponse---"+response);
                        try {
                            listener.sendMsgSuc(JsonUtils.JsonQuery(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
