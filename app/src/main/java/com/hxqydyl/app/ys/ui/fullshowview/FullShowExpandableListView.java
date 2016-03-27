package com.hxqydyl.app.ys.ui.fullshowview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by white_ash on 2016/3/22.
 */
public class FullShowExpandableListView extends ExpandableListView {
    public FullShowExpandableListView(Context context) {
        super(context);
    }

    public FullShowExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullShowExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
