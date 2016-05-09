package com.hxqydyl.app.ys.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.register.CityBean;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.register.OfficeBean;
import com.hxqydyl.app.ys.bean.register.ProvinceInfo;
import com.hxqydyl.app.ys.bean.register.RegionBean;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.CityResponse;
import com.hxqydyl.app.ys.bean.response.DeptResponse;
import com.hxqydyl.app.ys.bean.response.HospitalResponse;
import com.hxqydyl.app.ys.bean.response.ImageResponse;
import com.hxqydyl.app.ys.bean.response.ProvinceResponse;
import com.hxqydyl.app.ys.bean.response.RegionResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.pickerview.lib.OptionsPopupWindow;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xus.http.httplib.model.GetParams;
import com.xus.http.httplib.model.PostPrams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import galleryfinal.wq.photo.widget.PickConfig;

/**
 * Created by wangxu on 2016/5/6.
 */
public class QuakuducationFragment extends BaseRequstFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, OptionsPopupWindow.OnOptionsSelectListener {
    OnSubmitSuccess onSubmitSuccess;
   public void  setOnSubmitSuccess(OnSubmitSuccess onSubmitSuccess){
this.onSubmitSuccess=onSubmitSuccess;
    }

    private EditText edt_name;//医生名字
    private EditText edt_phone_area_code;//区号
    private EditText edt_phone;//电话
    private CheckBox man, woman;//性别男女
    private RelativeLayout rl_city;//城市
    private RelativeLayout rl_hos;//医院
    private RelativeLayout rl_dept;//科室
    private RelativeLayout rl_ranks;//职称
    private Button btn_ok;//提交
    private ImageView iv_img;//图片
    private boolean isMan = true;
    private OptionsPopupWindow optionsPopupWindow;
    private Type type;
    private List<ProvinceInfo> provinces;//省列表
    private List<CityBean> citys;//市列表
    private List<RegionBean> regions;//县区列表
    private List<HospitalsBean> hospitals;//医院
    private List<OfficeBean> depts;//科室

    private TextView tv_city_value;//展示的城市
    private TextView tv_hos_value;//展示的医院
    private TextView tv_dept_value;//展示的科室
    private TextView edt_ranks_value;//展示的职称


    private String provinceName;
    private String provinceId;

    private String cityName;
    private String cityId;

    private String ragionCode;
    private String hosId;
    private String deptId;
    private String rank;
    private String imagePath;//图片地址
    private String[] ranks = new String[]{"主任医师", "副主任医师", "主治医师", "住院医师", "助理医师", "实习医师"};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_city: //县市区
                getProvince();
                break;
            case R.id.rl_hos://执业医院
                if (TextUtils.isEmpty(ragionCode)) {
                    UIHelper.ToastMessage(getActivity(), "请先选择城市");
                    return;
                }
                getHos();
                break;
            case R.id.rl_dept://科室
                if (TextUtils.isEmpty(hosId)) {
                    UIHelper.ToastMessage(getActivity(), "请先选择医院");
                    return;
                }
                getDept();
                break;
            case R.id.rl_ranks://职称
                getranks();
                break;
            case R.id.btn_ok://职称
                if (TextUtils.isEmpty(imagePath)) {
                    UIHelper.ToastMessage(getActivity(), "请上传您的证件照");
                    return;
                }
                upImg();
                break;
            case R.id.iv_img://图片
                access(PickConfig.MODE_SINGLE_PICK, 1);
                break;
        }
    }


    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        switch (type) {
            case province:
                getCity(provinces.get(options1).getCode());
                provinceName = provinces.get(options1).getProvinceName();
                provinceId = provinces.get(options1).getCode();
                break;
            case city:
                getRegion(citys.get(options1).getCode());
                cityName = citys.get(options1).getCityName();
                cityId = citys.get(options1).getCode();
                break;
            case region:
                tv_city_value.setText(provinceName + "/" + cityName + "/" + regions.get(options1).getRegionName());
                ragionCode = regions.get(options1).getCode();
                break;
            case hospital:
                tv_hos_value.setText(hospitals.get(options1).getHospitalName());
                hosId = hospitals.get(options1).getId();
                break;
            case dept:
                tv_dept_value.setText(depts.get(options1).getDepartmentName());
                deptId = depts.get(options1).getId();
                break;
            case rank:
                edt_ranks_value.setText(ranks[options1]);
                rank = ranks[options1];
                break;
        }
    }

    private enum Type {
        province, city, region, hospital, dept, rank
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qualidication, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        edt_phone_area_code = (EditText) view.findViewById(R.id.edt_phone_area_code);
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);
        man = (CheckBox) view.findViewById(R.id.man);
        woman = (CheckBox) view.findViewById(R.id.woman);
        rl_city = (RelativeLayout) view.findViewById(R.id.rl_city);
        rl_hos = (RelativeLayout) view.findViewById(R.id.rl_hos);
        rl_dept = (RelativeLayout) view.findViewById(R.id.rl_dept);
        rl_ranks = (RelativeLayout) view.findViewById(R.id.rl_ranks);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        tv_city_value = (TextView) view.findViewById(R.id.tv_city_value);
        tv_hos_value = (TextView) view.findViewById(R.id.tv_hos_value);
        tv_dept_value = (TextView) view.findViewById(R.id.tv_dept_value);
        edt_ranks_value = (TextView) view.findViewById(R.id.edt_ranks_value);
    }

    private void initListener() {
        optionsPopupWindow = new OptionsPopupWindow(getActivity());
        rl_city.setOnClickListener(this);
        rl_hos.setOnClickListener(this);
        rl_dept.setOnClickListener(this);
        rl_ranks.setOnClickListener(this);
        iv_img.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        optionsPopupWindow.setOnoptionsSelectListener(this);
        optionsPopupWindow.setFocusable(true);
        optionsPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        man.setOnCheckedChangeListener(this);
        woman.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.man:
                isMan = isChecked;
                break;
            default:
                isMan = !isChecked;
                break;
        }
        man.setChecked(isMan);
        woman.setChecked(!isMan);
    }

    /**
     * 显示轮滑
     *
     * @param list
     */
    private void showPopupWindow(ArrayList<String> list) {
        if (list == null) return;
        optionsPopupWindow.setPicker(list);
        optionsPopupWindow.showAtLocation(btn_ok, Gravity.BOTTOM, 0, 0);
    }

    private void getProvince() {
        type = Type.province;
        toNomalNet(new GetParams(), ProvinceResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PROVINCE, "2.0"), "正在获取省列表");
    }

    private void getCity(String code) {
        type = Type.city;
        toNomalNet(toGetParams(toParamsBaen("provinceUuid", code)), CityResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.GET_CITY, "2.0"), "正在获取市列表");
    }

    private void getRegion(String code) {
        type = Type.region;
        toNomalNet(toGetParams(toParamsBaen("cityUuid", code)), RegionResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.GET_REGION, "2.0"), "正在获取区县列表");
    }

    private void getHos() {
        type = Type.hospital;
        toNomalNet(toGetParams(toParamsBaen("regionUuid", ragionCode)), HospitalResponse.class, 4, UrlConstants.getWholeApiUrl(UrlConstants.GET_HOSPITAL, "2.0"), "正在获取医院列表");

    }

    private void getDept() {
        type = Type.dept;

        toNomalNet(new GetParams(), DeptResponse.class, 5, UrlConstants.getWholeApiUrl(UrlConstants.GET_DEPARTMENT, "2.0"), "正在获取科室列表");

    }

    private void getranks() {
        type = Type.rank;
        showPopupWindow(toPopListForRanks(ranks));
    }

    private void upImg() {
        PostPrams postPrams = toPostFileParams(toParamsBaen("thumbnail", "true"));
        File file = new File(imagePath);
        postPrams.addFile(file.getName(), file);
        toNomalNet(postPrams, ImageResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.UPLOAD_IMGS, "1.0"), "正在上传图片");
    }

    private void subMitInfo(String imageId) {
        String name = edt_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            UIHelper.ToastMessage(getActivity(), "请填写您的真实姓名");
            return;
        }
        String area_code = edt_phone_area_code.getText().toString();
        if (TextUtils.isEmpty(area_code)) {
            UIHelper.ToastMessage(getActivity(), "请填写您的电话区号");
            return;
        }
        String phone = edt_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            UIHelper.ToastMessage(getActivity(), "请填写您的电话号码");
            return;
        }
        if (TextUtils.isEmpty(ragionCode)) {
            UIHelper.ToastMessage(getActivity(), "请选择您所在城市");
            return;
        }
        if (TextUtils.isEmpty(hosId)) {
            UIHelper.ToastMessage(getActivity(), "请选择您的医院");
            return;
        }
        if (TextUtils.isEmpty(deptId)) {
            UIHelper.ToastMessage(getActivity(), "请选择您的科室");
            return;
        }

        PostPrams postPrams = toPostParams(toParamsBaen("uuid", LoginManager.getDoctorUuid()),
                toParamsBaen("realName", name),
                toParamsBaen("sex", isMan ? "1" : "2"),
                toParamsBaen("province", provinceId),
                toParamsBaen("city", cityId),
                toParamsBaen("region", ragionCode),
                toParamsBaen("hospital", hosId),
                toParamsBaen("department", deptId),
                toParamsBaen("departmentLine", area_code + "-" + phone),
                toParamsBaen("sate", "3"),
                toParamsBaen("professional", rank),
                toParamsBaen("certImage", imageId)
        );
//        String url = "http://172.168.1.53/app/pub/doctor/2.0/insertServiceStaffMessage";
//        toNomalNet(postPrams, BaseResponse.class, 7, url, "正在提交信息");

    toNomalNet(postPrams, BaseResponse.class, 7, UrlConstants.getWholeApiUrl(UrlConstants.INSERT_SERVICE_STAFF_MESSAGE,"2.0"), "正在提交信息");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            ArrayList<String> paths = data.getStringArrayListExtra("data");
            for (String path : paths) {
                this.imagePath = path;
            }
            ImageLoader.getInstance().displayImage("file:/" + imagePath, iv_img);
        }
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1://省
                ProvinceResponse pr = (ProvinceResponse) bean;
                provinces = pr.value;
                showPopupWindow(toPopListForProvince(provinces));
                break;
            case 2://市
                CityResponse cr = (CityResponse) bean;
                citys = cr.value;
                showPopupWindow(toPopListForCity(citys));
                break;
            case 3://区县
                RegionResponse rr = (RegionResponse) bean;
                regions = rr.value;
                showPopupWindow(toPopListForRegion(regions));
                break;
            case 4://医院
                HospitalResponse hr = (HospitalResponse) bean;
                hospitals = hr.value;
                showPopupWindow(toPopListForHos(hospitals));
                break;
            case 5://科室
                DeptResponse dr = (DeptResponse) bean;
                depts = dr.value;
                showPopupWindow(toPopListForDept(depts));
                break;
            case 6://图片
                ImageResponse ir = (ImageResponse) bean;
                subMitInfo(ir.value.get(0).getId());
                break;
            case 7://提交
                SharedPreferences.getInstance().putString(SharedPreferences.USER_INFO_COMPLETE,"3");
                onSubmitSuccess.onSucess();
                break;
        }
    }

    private ArrayList<String> toPopListForProvince(List<ProvinceInfo> lists) {
        ArrayList<String> list = new ArrayList<>();
        for (ProvinceInfo info : lists) {
            list.add(info.getProvinceName());
        }
        return list;
    }

    private ArrayList<String> toPopListForCity(List<CityBean> lists) {
        ArrayList<String> list = new ArrayList<>();
        for (CityBean bean : lists) {
            list.add(bean.getCityName());
        }
        return list;
    }

    private ArrayList<String> toPopListForRegion(List<RegionBean> lists) {
        ArrayList<String> list = new ArrayList<>();
        for (RegionBean bean : lists) {
            list.add(bean.getRegionName());
        }
        return list;
    }

    private ArrayList<String> toPopListForHos(List<HospitalsBean> lists) {
        ArrayList<String> list = new ArrayList<>();
        for (HospitalsBean bean : lists) {
            list.add(bean.getHospitalName());
        }
        return list;
    }

    private ArrayList<String> toPopListForDept(List<OfficeBean> lists) {
        ArrayList<String> list = new ArrayList<>();
        for (OfficeBean bean : lists) {
            list.add(bean.getDepartmentName());
        }
        return list;
    }

    private ArrayList<String> toPopListForRanks(String[] lists) {
        ArrayList<String> list = new ArrayList<>();
        for (String bean : lists) {
            list.add(bean);
        }
        return list;
    }
    public interface OnSubmitSuccess{
        void onSucess();
    }
}
