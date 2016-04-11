package com.hxqydyl.app.ys.http.homepage;

import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

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
          void requestGainDoctorInfoSuccess(DoctorResultNew doctorResultNew,String str);
          void requestGainDoctorInfoFail(int statueCode);
     }

     public void gainDoctorInfo(String doctorUuid){
         System.out.println("response--->"+doctorUuid);
          OkHttpUtils
                  .get()
                  .url(UrlConstants.getWholeApiUrl(UrlConstants.GET_DOCTOR_INFO))
                  .addParams("doctorUuid", doctorUuid)
                  .addParams("callback", Constants.CALLBACK)
                  .build()
                  .execute(new StringCallback() {
                       @Override
                       public void onError(Call call, Exception e) {
                            onGainDoctorInfoListener.requestGainDoctorInfoFail(Constants.REQUEST_FAIL);
                       }

                       @Override
                       public void onResponse(String response) {
                           System.out.println("response--->" + response);
                           onGainDoctorInfoListener.requestGainDoctorInfoSuccess(JsonUtils.JsonDoctorInfoNew(StringUtils.cutoutBracketToString(response)),response);
                       }
                  });
     }
}
