package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfo;
import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.bean.register.RegionBean;
import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.register.HospitalNet;
import com.hxqydyl.app.ys.http.register.ProvinceNet;
import com.hxqydyl.app.ys.http.register.RegionNet;
import com.hxqydyl.app.ys.ui.pickerview.lib.OptionsPopupWindow;
import com.hxqydyl.app.ys.utils.Constants;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * 完善单位信息页面
 */
public class EvpiAddressActivity extends BaseTitleActivity implements View.OnClickListener,OptionsPopupWindow.OnOptionsSelectListener,ProvinceNet.OnProvinceListener,HospitalNet.OnHospitalListener,RegionNet.OnRegionListener{

    private Button nextBtn;
    private ArrayList<String> optionsItems = new ArrayList<>();
    OptionsPopupWindow optionsPopupWindow;

    private Button btnCity;
    private Button btnHosital;
    private Button btnOffice;
    private Button btnPhone;
    private Button btnRanks;

    private ProvinceNet provinceNet;
    private HospitalNet hospitalNet;
    private RegionNet regionNet;

    private ArrayList<String> provinces;
    private ArrayList<String> hospitals;
    private ArrayList<String> regions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_address);

        initViews();
        initListeners();
    }

    private void initViews(){
        initViewOnBaseTitle("完善信息");

        provinceNet = new ProvinceNet();
        hospitalNet = new HospitalNet();
        regionNet = new RegionNet();

        provinceNet.setListener(this);
        hospitalNet.setListener(this);
        regionNet.setListener(this);
        provinceNet.obtainProvince();

        optionsPopupWindow = new OptionsPopupWindow(this);

        nextBtn = (Button) findViewById(R.id.next_btn);

        btnCity = (Button) findViewById(R.id.btn_city);
        btnHosital = (Button) findViewById(R.id.btn_hosital);
        btnOffice = (Button) findViewById(R.id.btn_office);
        btnPhone = (Button) findViewById(R.id.btn_phone);
        btnRanks = (Button) findViewById(R.id.btn_ranks);

    }

    private void initListeners() {
        setBackListener(this);

        nextBtn.setOnClickListener(this);
        btnCity.setOnClickListener(this);
        btnHosital.setOnClickListener(this);
        btnOffice.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnRanks.setOnClickListener(this);

        optionsPopupWindow.setOnoptionsSelectListener(this);
        optionsPopupWindow.setFocusable(true);
        optionsPopupWindow.setBackgroundDrawable(new BitmapDrawable());
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
            case R.id.btn_city:
                optionsPopupWindow.setPicker(provinces);
                optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_hosital:
                optionsPopupWindow.setPicker(hospitals);
                optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_office:
                optionsPopupWindow.setPicker(regions);
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

    @Override
    public void requestProvinceSuc(ProvinceInfoResult provinceInfoResult) {
        List<ProvinceInfo> provinceInfos = provinceInfoResult.getProvinceInfos();
        provinces = new ArrayList<>();
        for (int i = 0;i<provinceInfos.size();i++){
            provinces.add(provinceInfos.get(i).getProvinceName());
        }

    }

    @Override
    public void RequestProvinceFail() {

    }

    @Override
    public void requestHospitalSuc(HospitalResultBean hospitalResultBean) {
        List<HospitalsBean> hospitalsBeans = hospitalResultBean.getList();
       hospitals = new ArrayList<>();
        for (int i = 0;i<hospitalsBeans.size();i++){
            hospitals.add(hospitalsBeans.get(i).getHospitalName());
        }

    }

    @Override
    public void requestHospitalFail() {

    }

    @Override
    public void requestRegionSuc(RegionResultBean regionResultBean) {
        List<RegionBean> regionBeans = regionResultBean.getRegionBeans();
        regions = new ArrayList<>();
        for (int i =0;i<regionBeans.size();i++){
            regions.add(regionBeans.get(i).getRegionName());
        }
    }

    @Override
    public void requestRegionFail() {

    }
}
