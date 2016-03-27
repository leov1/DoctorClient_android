package com.hxqydyl.app.ys.ui.fullshowview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by white_ash on 2016/3/28.
 */
public class FullShowListView extends ListView {
    public FullShowListView(Context context) {
        super(context);
    }

    public FullShowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullShowListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
