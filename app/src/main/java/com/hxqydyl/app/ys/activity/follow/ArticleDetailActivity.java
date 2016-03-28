package com.hxqydyl.app.ys.activity.follow;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hxqydyl.app.ys.activity.BaseWebActivity;

public class ArticleDetailActivity extends BaseWebActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initButtonView();
        loadUrl("http://www.cnblogs.com/gzggyy/archive/2013/05/27/3101721.html");
    }

    private void initButtonView() {
        btn_send_patient.setVisibility(View.VISIBLE);
    }

    /**
     * 跳转到选择患者页面
     * @param v
     */
    public void shareToPatient(View v){
        startActivity(new Intent(ArticleDetailActivity.this,PatientSelectActivity.class));
    }

}
