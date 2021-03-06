package com.hxqydyl.app.ys.ui.scrollviewandgridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by hxq on 2016/3/9.
 */
public class MyScrollExpandableListView extends ExpandableListView {

    public MyScrollExpandableListView(Context context) {
        super(context);
    }

    public MyScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
