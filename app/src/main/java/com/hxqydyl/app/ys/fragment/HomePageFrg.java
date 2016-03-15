package com.hxqydyl.app.ys.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.follow.ArticleActivity;
import com.hxqydyl.app.ys.activity.follow.ChoiceScaleActivity;
import com.hxqydyl.app.ys.activity.follow.FollowGroupsMgrActivity;
import com.hxqydyl.app.ys.activity.follow.FollowMainActivity;
import com.hxqydyl.app.ys.activity.follow.MassActivity;
import com.hxqydyl.app.ys.activity.follow.PatientInfoActivity;
import com.hxqydyl.app.ys.activity.reading.ReadingActivity;
import com.hxqydyl.app.ys.activity.video.VideoActivity;
import com.hxqydyl.app.ys.adapter.GalleryPagerAdapter;
import com.hxqydyl.app.ys.adapter.LineGridViewAdapter;
import com.hxqydyl.app.ys.bean.DoctorInfo;
import com.hxqydyl.app.ys.bean.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.DoctorResult;
import com.hxqydyl.app.ys.bean.DoctorResultNew;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.homepage.GainDoctorInfoNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.linegridview.LineGridView;
import com.hxqydyl.app.ys.ui.loopviewpager.AutoLoopViewPager;
import com.hxqydyl.app.ys.ui.viewpagerindicator.CirclePageIndicator;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.squareup.okhttp.Request;

import org.w3c.dom.Text;

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

    public HomePageFrg() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page_frg, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
    }

    private void initListeners() {
        gainDoctorInfoNet.setOnGainDoctorInfoListener(this);
        lineGridView.setOnItemClickListener(this);
    }

    private void initViews() {
        gainDoctorInfoNet = new GainDoctorInfoNet();

        noLoginLinear = (LinearLayout) view.findViewById(R.id.not_login_linear);
        loginLiear = (LinearLayout) view.findViewById(R.id.login_linear);
        //已经登陆

        headImg = (ImageView) view.findViewById(R.id.head_img);
        headName = (TextView) view.findViewById(R.id.head_name);
        suffererNum = (TextView) view.findViewById(R.id.sufferer_num);
        followNum = (TextView) view.findViewById(R.id.follow_num);
        income = (TextView) view.findViewById(R.id.income);

        loginBtn = (TextView) view.findViewById(R.id.login_btn);
        registerBtn = (TextView) view.findViewById(R.id.register_btn);

        updateLinear(LoginManager.isHasLogin());

        lineGridView = (LineGridView) view.findViewById(R.id.home_gridview);
        lineGridViewAdapter = new LineGridViewAdapter(this.getContext());
        lineGridView.setAdapter(lineGridViewAdapter);

        pager = (AutoLoopViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
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
        OkHttpClientManager.getDisplayImageDelegate().displayImage(headImg, doctorInfo.getDoctorIcon());
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
                Intent readIntent = new Intent(getActivity(), ReadingActivity.class);
                startActivity(readIntent);
                break;
            case 1://讲堂
                Intent videoIntent = new Intent(getActivity(), VideoActivity.class);
                startActivity(videoIntent);
                break;
            case 2://随访
                Intent followIntent = new Intent(getActivity(), FollowMainActivity.class);
                startActivity(followIntent);
                break;
            case 3://诊所

                break;
        }
    }
}
