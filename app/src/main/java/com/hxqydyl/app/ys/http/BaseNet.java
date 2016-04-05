package com.hxqydyl.app.ys.http;

/**
 * Created by white_ash on 2016/4/3.
 */
public abstract class BaseNet {
    public static final int ON_SEND = 1;
    public static final int ON_RESPONSE = 2;
    public static final int ON_ERROR = 3;
    public static final int ON_PROGRESS = 4;
    public NetRequestListener listener;

    public BaseNet(NetRequestListener listener) {
        this.listener = listener;
    }

    public void callListener(int type, String url, Object obj) {
        if (listener != null) {
            switch (type) {
                case ON_SEND:
                    listener.onSend(url);
                    break;
                case ON_RESPONSE:
                    listener.onResponse(url, obj);
                    break;
                case ON_ERROR:
                    listener.onError(url, (Exception) obj);
                    break;
                case ON_PROGRESS:
                    listener.onProgress(url,(Float) obj);
                    break;
            }
        }
    }
}
