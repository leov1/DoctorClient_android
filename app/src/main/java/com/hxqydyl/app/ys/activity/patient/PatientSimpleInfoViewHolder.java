package com.hxqydyl.app.ys.activity.patient;

import android.app.Activity;
import android.graphics.Bitmap;
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
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            ImageLoader.getInstance().displayImage(patient.getCustomerImg(), ivPatientPortrait, options);
            tvPatientName.setText(patient.getRealName());
            if ("女".equals(patient.getSex())) {
                ivSexFlag.setImageResource(R.mipmap.icon_woman_flag);
            } else {
                ivSexFlag.setImageResource(R.mipmap.icon_man_flag);
            }
            tvPatientAge.setText(patient.getAge());
            tvPatientFollowTime.setText(patient.getFollowTime());
            tvDescription.setText(patient.getDescription());
        }
    }


}
