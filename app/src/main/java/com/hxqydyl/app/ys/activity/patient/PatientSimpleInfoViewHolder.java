package com.hxqydyl.app.ys.activity.patient;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

/**
 * Created by white_ash on 2016/3/24.
 */
public class PatientSimpleInfoViewHolder {
    public View wholeView;
    @InjectId(id = R.id.ivPatientPortrait)
    public ImageView ivPatientPortrait;
    @InjectId(id = R.id.tvPatientName)
    public TextView tvPatientName;
    @InjectId(id = R.id.ivSexFlag)
    public ImageView ivSexFlag;
    @InjectId(id = R.id.tvPatientAge)
    public TextView tvPatientAge;
    @InjectId(id = R.id.tvPatientFollowTime)
    public TextView tvPatientFollowTime;
    @InjectId(id = R.id.tvDescription)
    public TextView tvDescription;

    public PatientSimpleInfoViewHolder(Activity activity){
        wholeView = activity.findViewById(R.id.llPatientSimpleInfo);
        InjectUtils.injectView(this,wholeView);
    }

}
