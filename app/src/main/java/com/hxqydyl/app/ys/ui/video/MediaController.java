package com.hxqydyl.app.ys.ui.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alice_company on 2016/5/31.
 */
public class MediaController extends FrameLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    @Bind(R.id.pause)
    ImageView mPlayImg;
    @Bind(R.id.progress)
    SeekBar mProgressSeekBar;

    @Bind(R.id.current)
    TextView currentTimeTextView;
    @Bind(R.id.total)
    TextView totalTimeTextView;

    @Bind(R.id.fullscreen)
    ImageView mExpandImg;

    private MediaControlImpl mMediaControl;

    public MediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaController(Context context) {
        this(context, null);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initData();
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.layout_video_controller, this);
        ButterKnife.bind(this);
    }

    private void initData() {
        mProgressSeekBar.setOnSeekBarChangeListener(this);
        mPlayImg.setOnClickListener(this);
        mExpandImg.setOnClickListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
        if (isFromUser)
            mMediaControl.onProgressTurn(ProgressState.DOING, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mMediaControl.onProgressTurn(ProgressState.START, 0);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mMediaControl.onProgressTurn(ProgressState.STOP, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pause:
                mMediaControl.onPlayTurn();
                break;
            case R.id.fullscreen:

                break;
        }
    }

    public void setMediaControl(MediaControlImpl mediaControl) {
        mMediaControl = mediaControl;
    }

    public void setPlayState(PlayState playState) {
        if (playState.equals(PlayState.PLAY)) {
            mPlayImg.setImageResource(R.drawable.jc_click_pause_selector);
        } else if (playState.equals(PlayState.ERROR)) {
            mPlayImg.setImageResource(R.drawable.jc_click_error_selector);
        } else {
            mPlayImg.setImageResource(R.drawable.jc_click_play_selector);
        }
    }

    public void setPlayProgressTxt(int nowSecond, int allSecond) {
        currentTimeTextView.setText(StringUtils.stringForTime(nowSecond));
        totalTimeTextView.setText(StringUtils.stringForTime(allSecond));
    }

    public void playFinish(int allTime) {
        mProgressSeekBar.setProgress(0);
        setPlayProgressTxt(0, allTime);
        setPlayState(PlayState.PAUSE);
    }

    public void setProgressBar(int progress, int secondProgress) {
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;
        if (secondProgress < 0) secondProgress = 0;
        if (secondProgress > 100) secondProgress = 100;
        mProgressSeekBar.setProgress(progress);
        mProgressSeekBar.setSecondaryProgress(secondProgress);
    }

    /**
     * 播放状态 播放 暂停
     */
    public enum PlayState {
        PLAY, PAUSE, ERROR
    }

    public enum ProgressState {
        START, DOING, STOP
    }


    public interface MediaControlImpl {
        void onPlayTurn();

        void onProgressTurn(ProgressState state, int progress);
    }
}
