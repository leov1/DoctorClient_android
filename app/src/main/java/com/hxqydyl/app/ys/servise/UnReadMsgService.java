package com.hxqydyl.app.ys.servise;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.MainActivity;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;


public class UnReadMsgService extends Service {

    private static final int NO_UUID_STATE_ORDER = 1;
    private static final int HAS_UUID_STATE_ORDER = 2;
    String lastMsgContentStr = "";
    Thread kUnReadThread;

   // String url = "/app/service/customer/%s/getUnreadConsultRecord";
   //String url = "/app/service/customer/%s/getConsultRecordContent";
    String url = "/app/service/customer/%s/getDoctorConsultRecordContent";

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情
            super.handleMessage(msg);
            if (msg.what == NO_UUID_STATE_ORDER) {

            }else if (msg.what == HAS_UUID_STATE_ORDER){

                getUnReadMsg(url);
            }
        }
    };

    public UnReadMsgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        setupBuilder();
        kUnReadThread = new Thread(new getUnReadMsgThreadRunnable());
        kUnReadThread.start();
    }

    public class getUnReadMsgThreadRunnable implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {

                    if (TextUtils.isEmpty(LoginManager.getDoctorUuid())) {

                        Thread.sleep(1000*20);// 线程暂停20秒，单位毫秒
                        Message message = new Message();
                        message.what = NO_UUID_STATE_ORDER;
                        handler.sendMessage(message);// 发送消息

                    }else {

                        Thread.sleep(1000*10);// 线程暂停10秒，单位毫秒
                        Message message = new Message();
                        message.what = HAS_UUID_STATE_ORDER;
                        handler.sendMessage(message);// 发送消息
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public void getUnReadMsg(String url) {

        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(url, "2.0"))
                .tag(this)
                .addParams("uuid", LoginManager.getDoctorUuid())
                .addParams("type", "2")
                .build()
                .execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {

                String resultStr = response.body().string();


                JSONObject jsonObj = JSON.parseObject(resultStr);

                if (!TextUtils.isEmpty(resultStr)) {
                    String kMsgStr =  (String) jsonObj.get("value");

                    if (!TextUtils.isEmpty(kMsgStr) && !kMsgStr.equals(lastMsgContentStr)) {
                        lastMsgContentStr = kMsgStr;

                        mBuilder.setContentText(lastMsgContentStr);
                        mNotificationManager.notify(1, mBuilder.build());
                    }
                }
                return resultStr;
            }

            @Override
            public void onError(Call call, Exception e) {


            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    public void setupBuilder() {

        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        mBuilder.setContentTitle("好心情")
                .setContentText("您有新的消息...") //设置通知栏显示内容
                .setContentIntent(pendingIntent2) //设置通知栏点击意图
                .setNumber(1) //设置通知集合的数量
                .setTicker("您有一条新的消息...") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON

    }

    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
}
