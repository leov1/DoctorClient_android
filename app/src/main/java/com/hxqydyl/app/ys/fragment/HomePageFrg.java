package com.hxqydyl.app.ys.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.clinic.ClinicActivity;
import com.hxqydyl.app.ys.activity.follow.FollowMainActivity;
import com.hxqydyl.app.ys.activity.reading.ReadingActivity;
import com.hxqydyl.app.ys.activity.video.VideoActivity;
import com.hxqydyl.app.ys.adapter.GalleryPagerAdapter;
import com.hxqydyl.app.ys.adapter.LineGridViewAdapter;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.http.homepage.GainDoctorInfoNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.linegridview.LineGridView;
import com.hxqydyl.app.ys.ui.loopviewpager.AutoLoopViewPager;
import com.hxqydyl.app.ys.ui.viewpagerindicator.CirclePageIndicator;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 首页frg
 */
public class HomePageFrg extends BaseFragment implements GainDoctorInfoNet.OnGainDoctorInfoListener,View.OnClickListener,AdapterView.OnItemClickListener{

    private LinearLayout loginLiear;
    private LinearLayout noLoginLinear;
    private TextView loginBtn;
    private TextView registerBtn;

    private ImageView headImg;//医生头像
    private TextView headName;//医生名字
    private TextView suffererNum;//患者数量
    private TextView followNum;//随访数量
    private TextView income;//医生收入

    private View view;
    private LineGridView lineGridView;
    private LineGridViewAdapter lineGridViewAdapter;

    private AutoLoopViewPager pager;
    private CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;

    private String doctorUuid;

    private GainDoctorInfoNet gainDoctorInfoNet;

    private View mHeader;
    private ListView listView;
    private Intent intent;

    public HomePageFrg() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_frg, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        initHeadView();
    }

    private void initListeners() {
        gainDoctorInfoNet.setOnGainDoctorInfoListener(this);
        lineGridView.setOnItemClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private void initHeadView(){
        listView.addHeaderView(mHeader);
        listView.setAdapter(null);
    }

    private void initViews() {
        initViewOnBaseTitle("首页",view);
        gainDoctorInfoNet = new GainDoctorInfoNet();
        mHeader = View.inflate(this.getActivity(),R.layout.home_header,null);

        listView = (ListView) view.findViewById(R.id.list_view);

        noLoginLinear = (LinearLayout) mHeader.findViewById(R.id.not_login_linear);
        loginLiear = (LinearLayout) mHeader.findViewById(R.id.login_linear);
        //已经登陆

        headImg = (ImageView) mHeader.findViewById(R.id.head_img);
        headName = (TextView) mHeader.findViewById(R.id.head_name);
        suffererNum = (TextView) mHeader.findViewById(R.id.sufferer_num);
        followNum = (TextView) mHeader.findViewById(R.id.follow_num);
        income = (TextView) mHeader.findViewById(R.id.income);

        loginBtn = (TextView) mHeader.findViewById(R.id.login_btn);
        registerBtn = (TextView) mHeader.findViewById(R.id.register_btn);

//        updateLinear(LoginManager.isHasLogin());

        lineGridView = (LineGridView) mHeader.findViewById(R.id.home_gridview);
        lineGridViewAdapter = new LineGridViewAdapter(this.getContext());
        lineGridView.setAdapter(lineGridViewAdapter);

        pager = (AutoLoopViewPager) mHeader.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) mHeader.findViewById(R.id.indicator);
        galleryAdapter = new GalleryPagerAdapter(this.getContext());
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);

    }

    @Override
    public void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (LoginManager.isHasLogin()) {
                doctorUuid = SharedPreferences.getInstance().getString("doctorUuid", "");
                loadDoctorInfo();
            }
        }
    }

    /**
     *请求网络
     */
    private void loadDoctorInfo() {
        gainDoctorInfoNet.gainDoctorInfo(doctorUuid);
    }

    /**
     * 更新数据显示
     * @param doctorInfo
     */
    private void updateDoctorInfo(DoctorInfoNew doctorInfo) {
        updateLinear(LoginManager.isHasLogin());
        ImageLoader.getInstance().displayImage(doctorInfo.getDoctorIcon(),headImg);
        headName.setText(doctorInfo.getDoctorName());
        suffererNum.setText(doctorInfo.getCustomerNum()+"");
        followNum.setText(doctorInfo.getVisitNum() + "");
        income.setText(doctorInfo.getIncome());
    }

    /**
     * 更新登陆条
     *
     * @param isLogin
     */
    private void updateLinear(Boolean isLogin) {
        if (isLogin) {
            noLoginLinear.setVisibility(View.GONE);
            loginLiear.setVisibility(View.VISIBLE);
        } else {
            noLoginLinear.setVisibility(View.VISIBLE);
            loginLiear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pager.stopAutoScroll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void saveHeaderInfoToCache(String str) {
        SharedPreferences.getInstance().putString("doctor_info", str);
    }

    private void getHeaderInfoFromCache() {
        SharedPreferences.getInstance().getString("doctor_info", "");
    }

    @Override
    public void requestGainDoctorInfoSuccess(DoctorInfoNew doctorInfoNew) {
        updateDoctorInfo(doctorInfoNew);
    }

    @Override
    public void requestGainDoctorInfoFail(int statueCode) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                UIHelper.showLogin(getActivity());
                break;
            case R.id.register_btn:
                UIHelper.showRegister(getActivity());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0://阅读
                intent = new Intent(getActivity(), ReadingActivity.class);
                startActivity(intent);
                break;
            case 1://讲堂
                intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
                break;
            case 2://随访
                intent = new Intent(getActivity(), FollowMainActivity.class);
                startActivity(intent);
                break;
            case 3://诊所
                intent = new Intent(getActivity(), ClinicActivity.class);
                startActivity(intent);
                break;
        }
    }
}
