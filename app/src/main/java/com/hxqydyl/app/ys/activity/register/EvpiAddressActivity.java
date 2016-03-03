package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.ui.pickerview.lib.OptionsPopupWindow;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import java.util.ArrayList;

/**
 * 完善单位信息页面
 */
public class EvpiAddressActivity extends BaseTitleActivity implements View.OnClickListener,OptionsPopupWindow.OnOptionsSelectListener{

    private Button nextBtn;
    private ArrayList<String> optionsItems = new ArrayList<>();
    OptionsPopupWindow optionsPopupWindow;

    private Button btnHosital;
    private Button btnOffice;
    private Button btnPhone;
    private Button btnRanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_address);

        getData();
        initViews();
        initListeners();
    }

    private void getData() {
        for (int i = 0;i<=6;i++){
            optionsItems.add("北京"+i);
        }

        //获取医院
        OkHttpClientManager.getAsyn(Constants.GET_HOSPITAL + "?callback=xch", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    private void initViews(){
        initViewOnBaseTitle("完善信息");
        optionsPopupWindow = new OptionsPopupWindow(this);

        nextBtn = (Button) findViewById(R.id.next_btn);

        btnHosital = (Button) findViewById(R.id.btn_hosital);
        btnOffice = (Button) findViewById(R.id.btn_office);
        btnPhone = (Button) findViewById(R.id.btn_phone);
        btnRanks = (Button) findViewById(R.id.btn_ranks);

    }

    private void initListeners() {
        setBackListener(this);

        nextBtn.setOnClickListener(this);
        btnHosital.setOnClickListener(this);
        btnOffice.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnRanks.setOnClickListener(this);

        optionsPopupWindow.setOnoptionsSelectListener(this);
        optionsPopupWindow.setPicker(optionsItems);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.next_btn:
                Intent nextIntent = new Intent(this,GoodChoiceActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.btn_hosital:
                optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_office:
                optionsPopupWindow.showAtLocation(btnOffice, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_phone:
                optionsPopupWindow.showAtLocation(btnPhone, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_ranks:
                optionsPopupWindow.showAtLocation(btnRanks, Gravity.BOTTOM,0,0);
                break;
        }
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {

    }
}
