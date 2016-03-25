package com.hxqydyl.app.ys.http.homepage;

import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.ResultCallback;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

/**
 * Created by hxq on 2016/3/3.
 * 主页获取医生信息
 */
public class GainDoctorInfoNet {

     private OnGainDoctorInfoListener onGainDoctorInfoListener;

     public void setOnGainDoctorInfoListener(OnGainDoctorInfoListener ml){
          this.onGainDoctorInfoListener = ml;
     }

     public interface OnGainDoctorInfoListener{
          void requestGainDoctorInfoSuccess(DoctorInfoNew doctorInfoNew);
          void requestGainDoctorInfoFail(int statueCode);
     }

     public void gainDoctorInfo(String doctorUuid){
          OkHttpClientManager.getAsyn(Constants.GET_DOCTOR_INFO + "?doctorUuid=" + doctorUuid + "&callback="+Constants.CALLBACK, new ResultCallback<String>() {
               @Override
               public void onError(Request request, Exception e) {
                    System.out.println("response--->" + request);
                    onGainDoctorInfoListener.requestGainDoctorInfoFail(Constants.REQUEST_FAIL);
               }

               @Override
               public void onResponse(String response) {
                    System.out.println("response--->" + response);
                    onGainDoctorInfoListener.requestGainDoctorInfoSuccess(JsonUtils.JsonDoctorInfoNew(response));
               }
          });
     }
}
