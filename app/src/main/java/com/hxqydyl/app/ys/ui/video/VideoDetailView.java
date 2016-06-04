package com.hxqydyl.app.ys.ui.video;

import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alice_company on 2016/5/31.
 */
public class VideoDetailView extends LinearLayout {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.text_date)
    TextView text_date;
    @Bind(R.id.text_people_name)
    TextView text_people_name;
    @Bind(R.id.text_people_place)
    TextView text_people_place;
    @Bind(R.id.text_course_info)
    TextView text_course_info;

    public VideoDetailView(Context context) {
        this(context,null);
    }

    public VideoDetailView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.include_video_detail, this);
        ButterKnife.bind(this);
    }

    public void setText_title(String str) {
        text_title.setText(str);
    }

    public void setText_date(String str) {
        text_date.setText(str);
    }

    public void setText_people_name(String str) {
        text_people_name.setText(str);
    }

    public void setText_people_place(String str) {
        text_people_place.setText(str);
    }

    public void setText_course_info(String str) {
        text_course_info.setText(str);
    }
}
