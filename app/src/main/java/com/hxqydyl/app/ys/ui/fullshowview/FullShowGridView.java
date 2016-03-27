package com.hxqydyl.app.ys.ui.fullshowview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/3/25.
 */
public class FullShowGridView extends GridView {
    public FullShowGridView(Context context) {
        super(context);
    }

    public FullShowGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullShowGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
