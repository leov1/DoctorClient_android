package com.hxqydyl.app.ys.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by alice_company on 2016/6/17.
 */
public class TimeCount extends CountDownTimer {

    private Button btn;

    /**
     * 参数依次为总时长,和计时的时间间隔
     */
    public TimeCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.btn.setText(millisUntilFinished / 1000 + "秒");
        this.btn.setEnabled(false);
    }

    @Override
    public void onFinish() {
        this.btn.setText("重新发送");
        this.btn.setEnabled(true);
    }
}
