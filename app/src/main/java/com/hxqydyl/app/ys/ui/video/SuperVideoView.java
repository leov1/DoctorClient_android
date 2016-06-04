package com.hxqydyl.app.ys.ui.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by alice_company on 2016/6/1.
 */
public class SuperVideoView extends VideoView{
    public SuperVideoView(Context context) {
        super(context);
    }

    public SuperVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //下面的代码是让视频的播放的长宽是根据你设置的参数来决定

        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
