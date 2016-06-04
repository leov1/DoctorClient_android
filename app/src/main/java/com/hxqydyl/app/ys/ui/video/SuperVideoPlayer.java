package com.hxqydyl.app.ys.ui.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alice_company on 2016/5/31.
 */
public class SuperVideoPlayer extends RelativeLayout implements MediaController.MediaControlImpl,MediaPlayer.OnPreparedListener
        ,MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener{

    private final int MSG_HIDE_CONTROLLER = 10;
    private final int MSG_UPDATE_PLAY_TIME = 11;

    @Bind(R.id.video_view)
    SuperVideoView mSuperVideoView;
    @Bind(R.id.controller)
    MediaController mMediaController;
    @Bind(R.id.progressbar)
    View mProgressBarView;

    //是否自动隐藏控制栏
    private boolean mAutoHideController = true;
    private Timer mUpdateTimer;
    private Context mContext;

    public SuperVideoPlayer(Context context) {
        this(context, null);
    }

    public SuperVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        View.inflate(context, R.layout.super_video_player_layout, this);
        ButterKnife.bind(this);
        mMediaController.setMediaControl(this);
        mSuperVideoView.setOnTouchListener(mOnTouchVideoListener);

    }

    public void setAutoHideController(boolean autoHideController) {
        mAutoHideController = autoHideController;
    }

    @Override
    public void onPlayTurn() {
        if (mSuperVideoView.isPlaying()) {
            pausePlay(true);
        } else {
            goOnPlay();
        }
    }

    @Override
    public void onProgressTurn(MediaController.ProgressState state, int progress) {
        if (state.equals(MediaController.ProgressState.START)) {
            mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        } else if (state.equals(MediaController.ProgressState.STOP)) {
            resetHideTimer();
        } else {
            int time = progress * mSuperVideoView.getDuration() / 100;
            mSuperVideoView.seekTo(time);
            updatePlayTime();
        }
    }

    /**
     * 加载并开始播放视频
     *
     * @param video videoUrl
     */
    public void loadAndPlay(Video video, int seekTime) {
        showProgressView(seekTime > 0);
        if (TextUtils.isEmpty(video.getmPlayUrl().getmFormatUrl())) {
            Log.e("TAG", "videoUrl should not be null");
            return;
        }
        mSuperVideoView.setOnPreparedListener(this);

        Uri uri = Uri.parse(video.getmPlayUrl().getmFormatUrl());
        mSuperVideoView.setVideoURI(uri);

        mSuperVideoView.setVisibility(VISIBLE);
        startPlayVideo(seekTime);
    }

    /**
     * 播放视频
     * should called after setVideoPath()
     */
    private void startPlayVideo(int seekTime) {
        if (null == mUpdateTimer) resetUpdateTimer();
        resetHideTimer();
        mSuperVideoView.setOnCompletionListener(this);
        mSuperVideoView.start();
        if (seekTime > 0) {
            mSuperVideoView.seekTo(seekTime);
        }
        mMediaController.setPlayState(MediaController.PlayState.PLAY);
    }

    /**
     * 关闭视频
     */
    public void close() {
        mMediaController.setPlayState(MediaController.PlayState.PAUSE);
        stopHideTimer(true);
        stopUpdateTimer();
        mSuperVideoView.pause();
        mSuperVideoView.stopPlayback();
        mSuperVideoView.setVisibility(GONE);
    }

    /**
     * 更新播放进度条
     */
    private void updatePlayProgress() {
        int allTime = mSuperVideoView.getDuration();
        int playTime = mSuperVideoView.getCurrentPosition();
        int loadProgress = mSuperVideoView.getBufferPercentage();
        int progress = playTime * 100 / allTime;
        mMediaController.setProgressBar(progress, loadProgress);
    }

    /**
     * 显示loading圈
     *
     * @param isTransparentBg isTransparentBg
     */
    private void showProgressView(Boolean isTransparentBg) {
        mProgressBarView.setVisibility(VISIBLE);
        if (!isTransparentBg) {
            mProgressBarView.setBackgroundResource(android.R.color.black);
        } else {
            mProgressBarView.setBackgroundResource(android.R.color.transparent);
        }
    }

    /**
     * 更新播放的进度时间
     */
    private void updatePlayTime() {
        int allTime = mSuperVideoView.getDuration();
        int playTime = mSuperVideoView.getCurrentPosition();
        mMediaController.setPlayProgressTxt(playTime, allTime);
    }

    public void pausePlay(boolean isShowController) {
        mSuperVideoView.pause();
        mMediaController.setPlayState(MediaController.PlayState.PAUSE);
        stopHideTimer(isShowController);
    }

    public void goOnPlay() {
        mSuperVideoView.start();
        mMediaController.setPlayState(MediaController.PlayState.PLAY);
        resetHideTimer();
        resetUpdateTimer();
    }

    private void resetHideTimer() {
        if (!isAutoHideController()) return;
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        int TIME_SHOW_CONTROLLER = 4000;
        mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, TIME_SHOW_CONTROLLER);
    }

    private void resetUpdateTimer() {
        mUpdateTimer = new Timer();
        int TIME_UPDATE_PLAY_TIME = 1000;
        mUpdateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_UPDATE_PLAY_TIME);
            }
        }, 0, TIME_UPDATE_PLAY_TIME);
    }

    private void stopUpdateTimer() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer = null;
        }
    }

    private void stopHideTimer(boolean isShowController) {
        mHandler.removeMessages(MSG_HIDE_CONTROLLER);
        mMediaController.clearAnimation();
        mMediaController.setVisibility(isShowController ? View.VISIBLE : View.GONE);
    }

    public boolean isAutoHideController() {
        return mAutoHideController;
    }

    private void showOrHideController() {
        if (mMediaController.getVisibility() == View.VISIBLE) {
            mMediaController.setVisibility(View.GONE);
            mMediaController.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_exit_from_bottom);
            mMediaController.startAnimation(animation);
        } else {
            mMediaController.setVisibility(View.VISIBLE);
            mMediaController.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_enter_from_bottom);
            mMediaController.startAnimation(animation);
            resetHideTimer();
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_UPDATE_PLAY_TIME) {
                updatePlayTime();
                updatePlayProgress();
            } else if (msg.what == MSG_HIDE_CONTROLLER) {
                showOrHideController();
            }
            return false;
        }
    });

    private OnTouchListener mOnTouchVideoListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                showOrHideController();
            }
            return true;
        }
    };

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START
                        || what == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING) {
                    mProgressBarView.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopUpdateTimer();
        stopHideTimer(true);
        mMediaController.playFinish(mSuperVideoView.getDuration());
        Toast.makeText(mContext, "视频播放完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int i1) {
        if (what != 38 && what != -38) {
            stopUpdateTimer();
            stopHideTimer(true);
        //    setPlayState(MediaController.PlayState.ERROR);
        }
        return true;
    }

}
