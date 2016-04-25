package com.hxqydyl.app.ys.activity.patient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by white_ash on 2016/3/24.
 */
public class PatientSimpleInfoViewHolder {
    private DisplayImageOptions options;
    public View wholeView;
    public ImageView ivPatientPortrait;
    public TextView tvPatientName;
    public ImageView ivSexFlag;
    public TextView tvPatientAge;
    public TextView tvPatientFollowTime;
    public TextView tvDescription;

    public PatientSimpleInfoViewHolder(Activity activity) {
        wholeView = activity.findViewById(R.id.llPatientSimpleInfo);
        InjectUtils.injectView(this, wholeView);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(activity, 50)))
                .showImageForEmptyUri(R.mipmap.portrait_man)
                .showImageOnFail(R.mipmap.portrait_man)
                .build();
        ivPatientPortrait = (ImageView) activity.findViewById(R.id.ivPatientPortrait);
        tvPatientName = (TextView) activity.findViewById(R.id.tvPatientName);
        ivSexFlag = (ImageView) activity.findViewById(R.id.ivSexFlag);
        tvPatientAge = (TextView) activity.findViewById(R.id.tvPatientAge);
        tvPatientFollowTime = (TextView) activity.findViewById(R.id.tvPatientFollowTime);
        tvDescription = (TextView) activity.findViewById(R.id.tvDescription);


    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            ImageLoader.getInstance().displayImage(patient.getCustomerImg(), ivPatientPortrait, options);
            tvPatientName.setText(patient.getCustomerName());
            if (TextUtils.isEmpty(patient.getSex())) {
                ivSexFlag.setVisibility(View.GONE);
            } else {
                if ("女".equals(patient.getSex())) {
                    ivSexFlag.setImageResource(R.mipmap.icon_woman_flag);
                } else {
                    ivSexFlag.setImageResource(R.mipmap.icon_man_flag);
                }
                ivSexFlag.setVisibility(View.VISIBLE);
            }
            tvPatientAge.setText(TextUtils.isEmpty(patient.getAge()) ? "" : patient.getAge());
            tvPatientFollowTime.setText(TextUtils.isEmpty(patient.getFollowTime()) ? "" : patient.getFollowTime());
            tvDescription.setText(TextUtils.isEmpty(patient.getCustomerMessage()) ? "" : patient.getCustomerMessage());
        }
    }


}
