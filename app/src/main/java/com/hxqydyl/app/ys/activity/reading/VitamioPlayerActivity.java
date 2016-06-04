package com.hxqydyl.app.ys.activity.reading;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.ui.video.MediaController;
import com.hxqydyl.app.ys.ui.video.SuperVideoPlayer;
import com.hxqydyl.app.ys.ui.video.Video;
import com.hxqydyl.app.ys.ui.video.VideoUrl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hxq on 2016/4/27.
 */
public class VitamioPlayerActivity extends AppCompatActivity {

    private static final String VIDEO_URL = "VideoUrl";
    private static final String VIDEO_TITLE = "VideoTitle";
    @Bind(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    private Intent intent;
    private String videoTitle;
    private String videoUrl;

    public static void startActivity(Context context,String sourceUrl,String duration){
        Intent intent = new Intent(context, VitamioPlayerActivity.class);
        intent.putExtra(VIDEO_URL, sourceUrl);
        intent.putExtra(VIDEO_TITLE, duration);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);

        intent = this.getIntent();
        if (intent != null) {
           videoTitle = intent.getStringExtra(VIDEO_TITLE);
           videoUrl = intent.getStringExtra(VIDEO_URL);
            this.setTitle(videoTitle);
        }

        mSuperVideoPlayer.setVisibility(View.VISIBLE);
        mSuperVideoPlayer.setAutoHideController(true);

        Video video = new Video();
        VideoUrl videoUrl1 = new VideoUrl();
        videoUrl1.setmFormatUrl(videoUrl);
        video.setmVideoName(videoTitle);
        video.setmPlayUrl(videoUrl1);
        mSuperVideoPlayer.loadAndPlay(video,0);
    }

}