package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.CityBean;
import com.hxqydyl.app.ys.bean.register.CityResultBean;
import com.hxqydyl.app.ys.bean.register.HospitalResultBean;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.register.OfficeBean;
import com.hxqydyl.app.ys.bean.register.OfficeResultBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfo;
import com.hxqydyl.app.ys.bean.register.ProvinceInfoResult;
import com.hxqydyl.app.ys.bean.register.RegionBean;
import com.hxqydyl.app.ys.bean.register.RegionResultBean;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.register.CityNet;
import com.hxqydyl.app.ys.http.register.HospitalNet;
import com.hxqydyl.app.ys.http.register.OfficeNet;
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
public class EvpiAddressActivity extends BaseTitleActivity implements View.OnClickListener,OptionsPopupWindow.OnOptionsSelectListener,
        ProvinceNet.OnProvinceListener,HospitalNet.OnHospitalListener,RegionNet.OnRegionListener,CityNet.OnCityListener,
        OfficeNet.OnOfficeListener{

    private Button nextBtn;
    private ArrayList<String> optionsItems = new ArrayList<>();
    OptionsPopupWindow optionsPopupWindow;

    private Button btnCity;
    private Button btnHosital;
    private Button btnOffice;
    private EditText editPhone;
    private Button btnRanks;

    private ProvinceNet provinceNet;//获取省
    private CityNet cityNet;//获取市
    private RegionNet regionNet;//获取区县
    private HospitalNet hospitalNet;//获取医院
    private OfficeNet officeNet;//获取科室

    private ArrayList<String> provinces;
    private ArrayList<String> cities;
    private ArrayList<String> hospitals;
    private ArrayList<String> regions;
    private ArrayList<String> offices;

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
        cityNet = new CityNet();
        hospitalNet = new HospitalNet();
        regionNet = new RegionNet();
        officeNet = new OfficeNet();

        provinceNet.setListener(this);
        cityNet.setListener(this);
        hospitalNet.setListener(this);
        regionNet.setListener(this);
        officeNet.setListener(this);
        provinceNet.obtainProvince();


        optionsPopupWindow = new OptionsPopupWindow(this);

        nextBtn = (Button) findViewById(R.id.next_btn);

        btnCity = (Button) findViewById(R.id.btn_city);
        btnHosital = (Button) findViewById(R.id.btn_hosital);
        btnOffice = (Button) findViewById(R.id.btn_office);
        editPhone = (EditText) findViewById(R.id.edit_mobile);
        btnRanks = (Button) findViewById(R.id.btn_ranks);

    }

    private void initListeners() {
        setBackListener(this);

        nextBtn.setOnClickListener(this);
        btnCity.setOnClickListener(this);
        btnHosital.setOnClickListener(this);
        btnOffice.setOnClickListener(this);
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
            case R.id.next_btn: //下一步
                Intent nextIntent = new Intent(this,GoodChoiceActivity.class);
                startActivity(nextIntent);
                break;
            case R.id.btn_city: //县市区
                optionsPopupWindow.setPicker(provinces);
                optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_hosital://执业医院
                optionsPopupWindow.setPicker(hospitals);
                optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_office://科室
                optionsPopupWindow.setPicker(regions);
                optionsPopupWindow.showAtLocation(btnOffice, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_ranks://职称
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
    public void requestCitySuc(CityResultBean cityResultBean) {
        List<CityBean> cityBeans = cityResultBean.getCityBeans();
        cities = new ArrayList<>();
        for (int i = 0;i<cityBeans.size();i++){
            cities.add(cityBeans.get(i).getCityName());
        }
    }

    @Override
    public void RequestCityFail() {

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
    public void requestOfficeSuc(OfficeResultBean officeResultBean) {
        List<OfficeBean> officeBeans = officeResultBean.getOfficeBeans();
        offices = new ArrayList<>();
        for (int i = 0;i<officeBeans.size();i++){
            hospitals.add(officeBeans.get(i).getDepartmentName());
        }
    }

    @Override
    public void requestOfficeFail() {

    }
}
