package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.AddressParamBean;
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
import com.hxqydyl.app.ys.http.register.CityNet;
import com.hxqydyl.app.ys.http.register.HospitalNet;
import com.hxqydyl.app.ys.http.register.OfficeNet;
import com.hxqydyl.app.ys.http.register.ProvinceNet;
import com.hxqydyl.app.ys.http.register.RegionNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.pickerview.lib.OptionsPopupWindow;

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

    private Intent intent;

    private Button btnCity;
    private Button btnHosital;
    private Button btnOffice;
    private EditText editPhone;
    private EditText editOther;
    private EditText editBrief;
    private Button btnRanks;

    private ProvinceNet provinceNet;//获取省
    private CityNet cityNet;//获取市
    private RegionNet regionNet;//获取区县
    private HospitalNet hospitalNet;//获取医院
    private OfficeNet officeNet;//获取科室

    private ArrayList<String> provinces = new ArrayList<>();
    private ArrayList<String> cities = new ArrayList<>();
    private ArrayList<String> regions = new ArrayList<>();
    private ArrayList<String> hospitals = new ArrayList<>();
    private ArrayList<String> offices = new ArrayList<>();
    private ArrayList<String> rankList = new ArrayList<>();
    private ArrayList<String> codes = new ArrayList<>();

    private String provinceName;
    private String cityName;
    private String regionName;
    private String hospitalName;
    private String officeName;
    private String rankName;

    private String provinceCode;
    private String cityCode;
    private String ragionCode;
    private String hospitalCode;
    private String officeCode;
    private String rankCode;

    private String[] ranks = new String[]{"主任医师","副主任医师","主治医师","住院医师","助理医师","实习医师"};

    private Type type;
    private enum Type{
        province ,city ,region,hospital,office,rank
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_address);

        initViews();
        initListeners();
    }

    private void initViews(){
        initViewOnBaseTitle("完善信息");

        for (int i = 0;i<ranks.length;i++){
            rankList.add(ranks[i]);
        }

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

        optionsPopupWindow = new OptionsPopupWindow(this);

        nextBtn = (Button) findViewById(R.id.next_btn);

        btnCity = (Button) findViewById(R.id.btn_city);
        btnHosital = (Button) findViewById(R.id.btn_hosital);
        btnOffice = (Button) findViewById(R.id.btn_office);
        btnRanks = (Button) findViewById(R.id.btn_ranks);
        editPhone = (EditText) findViewById(R.id.edit_mobile);
        editOther = (EditText) findViewById(R.id.edit_other);
        editBrief = (EditText) findViewById(R.id.brief_edit);

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
                if (TextUtils.isEmpty(rankName)){
                    UIHelper.ToastMessage(EvpiAddressActivity.this,"请先完善信息");
                    return;
                }
                intent = new Intent(this,GoodChoiceActivity.class);
                AddressParamBean addressParamBean = new AddressParamBean();
                addressParamBean.setProvinceCode(provinceCode);
                addressParamBean.setAreaCode(ragionCode);
                addressParamBean.setCityCode(cityCode);
                addressParamBean.setDepartments(officeCode);
                addressParamBean.setInfirmaryCode(hospitalCode);
                addressParamBean.setOtherhospital(editOther.getText().toString());
                addressParamBean.setProfessional(rankName);
                addressParamBean.setSynopsis(editBrief.getText().toString());
                addressParamBean.setTelephone(editPhone.getText().toString());
                intent.putExtra("bean",addressParamBean);
                startActivity(intent);
                break;
            case R.id.btn_city: //县市区
                loadData(Type.province,null);
                break;
            case R.id.btn_hosital://执业医院
                if (TextUtils.isEmpty(provinceCode)){
                    UIHelper.ToastMessage(EvpiAddressActivity.this,"请先选择地区");
                    return;
                }
                loadData(Type.hospital,null);
                break;
            case R.id.btn_office://科室
                if (TextUtils.isEmpty(hospitalCode)){
                    UIHelper.ToastMessage(EvpiAddressActivity.this,"请先选择医院");
                    return;
                }
                loadData(Type.office,null);
                break;
            case R.id.btn_ranks://职称
                if (TextUtils.isEmpty(officeName)){
                    UIHelper.ToastMessage(EvpiAddressActivity.this,"请先选择科室");
                    return;
                }
                loadData(Type.rank,null);
                break;
        }
    }

    private void loadData(Type type,String code){
        this.type = type;
        switch (type){
            case province:
                provinceNet.obtainProvince();
                break;
            case city:
                cityNet.obtainCity(code);
                break;
            case region:
                regionNet.obtainRegions(code);
                break;
            case hospital:
                hospitalNet.obtainHospitals(provinceCode, cityCode, ragionCode);
                break;
            case office:
                officeNet.obtainOffice();
                break;
            case rank:
                collectPopupWindow(rankList);
                break;
        }
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
          System.out.println("options1--->"+options1+"--options2--"+option2+"--options3--"+options3);
         if (type == Type.province){
             provinceCode = codes.get(options1);
             provinceName = provinces.get(options1);
             loadData(Type.city,provinceCode);
         }else if(type == Type.city){
             cityCode = codes.get(options1);
             cityName = cities.get(options1);
             loadData(Type.region,codes.get(options1));
         }else if (type == Type.region){
             btnCity.setText(provinceName+"/"+cityName+"/"+regions.get(options1));
             ragionCode = codes.get(options1);
         }else if (type == Type.hospital){

             if (options1 == (hospitals.size()-1)){
                 editOther.setVisibility(View.VISIBLE);
             }else{
                 editOther.setVisibility(View.GONE);
             }

             btnHosital.setText(hospitals.get(options1));
             hospitalCode = codes.get(options1);
         }else if (type == Type.office){
             officeName =offices.get(options1);
             btnOffice.setText(officeName);
             officeCode = codes.get(options1);
         }else if(type == Type.rank){
             rankName = rankList.get(options1);
             btnRanks.setText(rankName);
         }
    }

    /**
     * 显示轮滑
     * @param list
     */
    private void collectPopupWindow(ArrayList<String> list){
        if (list == null) return;
        optionsPopupWindow.setPicker(list);
        optionsPopupWindow.showAtLocation(btnHosital, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void requestProvinceSuc(ProvinceInfoResult provinceInfoResult) {
        List<ProvinceInfo> provinceInfos = provinceInfoResult.getProvinceInfos();
        provinces = new ArrayList<>();
        codes.clear();
        for (int i = 0;i<provinceInfos.size();i++){
            provinces.add(provinceInfos.get(i).getProvinceName());
            codes.add(provinceInfos.get(i).getCode());
        }
        collectPopupWindow(provinces);
    }

    @Override
    public void RequestProvinceFail() {

    }

    @Override
    public void requestCitySuc(CityResultBean cityResultBean) {
        List<CityBean> cityBeans = cityResultBean.getCityBeans();
        cities = new ArrayList<>();
        codes.clear();
        for (int i = 0;i<cityBeans.size();i++){
            cities.add(cityBeans.get(i).getCityName());
            codes.add(cityBeans.get(i).getCode());
        }
        collectPopupWindow(cities);
    }

    @Override
    public void RequestCityFail() {

    }

    @Override
    public void requestRegionSuc(RegionResultBean regionResultBean) {
        List<RegionBean> regionBeans = regionResultBean.getRegionBeans();
        regions = new ArrayList<>();
        codes.clear();
        for (int i =0;i<regionBeans.size();i++){
            regions.add(regionBeans.get(i).getRegionName());
            codes.add(regionBeans.get(i).getCode());
        }
        collectPopupWindow(regions);
    }

    @Override
    public void requestRegionFail() {

    }

    @Override
    public void requestHospitalSuc(HospitalResultBean hospitalResultBean) {
        List<HospitalsBean> hospitalsBeans = hospitalResultBean.getList();
        hospitals = new ArrayList<>();
        codes.clear();
        for (int i = 0;i<hospitalsBeans.size();i++){
            hospitals.add(hospitalsBeans.get(i).getHospitalName());
            codes.add(hospitalsBeans.get(i).getId());
        }
        collectPopupWindow(hospitals);
    }

    @Override
    public void requestHospitalFail() {

    }


    @Override
    public void requestOfficeSuc(OfficeResultBean officeResultBean) {
        List<OfficeBean> officeBeans = officeResultBean.getOfficeBeans();
        offices = new ArrayList<>();
        codes.clear();
        for (int i = 0;i<officeBeans.size();i++){
            offices.add(officeBeans.get(i).getDepartmentName());
            codes.add(officeBeans.get(i).getId());
        }
        collectPopupWindow(offices);
    }

    @Override
    public void requestOfficeFail() {

    }
}
