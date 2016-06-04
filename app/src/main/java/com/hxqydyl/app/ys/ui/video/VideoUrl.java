package com.hxqydyl.app.ys.ui.video;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alice_company on 2016/5/31.
 */
public class VideoUrl implements Parcelable {

    private String mFormatUrl;//视频url

    public VideoUrl() {
    }

    public VideoUrl(Parcel sorce){
        mFormatUrl = sorce.readString();
    }

    public static final Creator<VideoUrl> CREATOR = new Creator<VideoUrl>() {
        @Override
        public VideoUrl createFromParcel(Parcel in) {
            return new VideoUrl(in);
        }

        @Override
        public VideoUrl[] newArray(int size) {
            return new VideoUrl[size];
        }
    };

    public String getmFormatUrl() {
        return mFormatUrl;
    }

    public void setmFormatUrl(String mFormatUrl) {
        this.mFormatUrl = mFormatUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeString(mFormatUrl);
    }
}
