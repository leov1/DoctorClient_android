package com.hxqydyl.app.ys.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by hxq on 2016/5/6.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_text,null);
        ImageView target = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
        LinearLayout description_layout = (LinearLayout) v.findViewById(com.daimajia.slider.library.R.id.description_layout) ;
        description_layout.setVisibility(View.INVISIBLE);
        bindEventAndShow(v, target);
        return v;
    }
}