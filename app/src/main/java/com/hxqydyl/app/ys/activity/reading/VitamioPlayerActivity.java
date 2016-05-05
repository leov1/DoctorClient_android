package com.hxqydyl.app.ys.activity.reading;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hxqydyl.app.ys.R;

/**
 * Created by hxq on 2016/4/27.
 */
public class VitamioPlayerActivity extends AppCompatActivity {

    private VideoView videoPlayView;
    private MediaController videoPlayController;
    private boolean isFirstError = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//       this.getSupportActionBar().hide();
        setContentView(R.layout.activity_video_play);
        this.initVideoPlay();
    }

    private void initVideoPlay() {
        this.videoPlayController = new MediaController(this);
        this.videoPlayView = (VideoView) this
                .findViewById(R.id.video_play_pldplayer);
        videoPlayView.setMediaController(videoPlayController);
        videoPlayController.setMediaPlayer(videoPlayView);
        videoPlayController.setAnchorView(videoPlayView);

        final String videoTitle = this.getIntent().getStringExtra("VideoTitle");
        final String videoUrl = this.getIntent().getStringExtra("VideoUrl");
        this.setTitle(videoTitle);

        //common settings
        videoPlayView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (isFirstError) {
                    finish();
                }
                isFirstError = !isFirstError;
                return false;
            }
        });
        videoPlayView.setOnInfoListener(new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });

        //video to play
        videoPlayView.setVideoURI(Uri.parse(videoUrl));
        videoPlayView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoPlayView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
            }
        });
    }
}