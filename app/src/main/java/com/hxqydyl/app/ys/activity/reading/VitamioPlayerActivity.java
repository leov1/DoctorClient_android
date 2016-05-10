package com.hxqydyl.app.ys.activity.reading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by hxq on 2016/4/27.
 */
public class VitamioPlayerActivity extends AppCompatActivity {

    @InjectId(id = R.id.custom_videoplayer_standard)
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_play);
        InjectUtils.injectView(this);

        intent = this.getIntent();
        if (intent != null) {
            final String videoTitle = intent.getStringExtra("VideoTitle");
            final String videoUrl = intent.getStringExtra("VideoUrl");
            this.setTitle(videoTitle);

            InjectUtils.injectView(this);
            jcVideoPlayerStandard.setUp(videoUrl, videoTitle);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}