package com.hxqydyl.app.ys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseFragmentActivity;
import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.activity.follow.FollowMainActivity;
import com.hxqydyl.app.ys.activity.reading.ReadingActivity;
import com.hxqydyl.app.ys.activity.register.QualidicationActivity;
import com.hxqydyl.app.ys.adapter.LineGridViewAdapter;
import com.hxqydyl.app.ys.bean.StatusBean;
import com.hxqydyl.app.ys.bean.homepage.PageIconBean;
import com.hxqydyl.app.ys.bean.homepage.PageIconResult;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.bean.response.StatusResponse;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.homepage.GainDoctorInfoNet;
import com.hxqydyl.app.ys.http.homepage.PagerNet;
import com.hxqydyl.app.ys.ui.CircleImageView;
import com.hxqydyl.app.ys.ui.TextSliderView;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.library.PullToRefreshBase;
import com.hxqydyl.app.ys.ui.library.PullToRefreshScrollView;
import com.hxqydyl.app.ys.ui.linegridview.LineGridView;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.hxqydyl.app.ys.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import framework.listener.RegisterSucListener;

/**
 * 首页frg
 */
public class HomePageFrg extends BaseRequstFragment implements GainDoctorInfoNet.OnGainDoctorInfoListener, View.OnClickListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener
        , AdapterView.OnItemClickListener
        , PagerNet.OnPagerNetListener, RegisterSucListener {

    @InjectId(id = R.id.login_linear)
    private LinearLayout loginLiear;
    @InjectId(id = R.id.not_login_linear)
    private RelativeLayout noLoginLinear;
    @InjectId(id = R.id.login_btn)
    private TextView loginBtn;
    @InjectId(id = R.id.register_btn)
    private TextView registerBtn;

    @InjectId(id = R.id.head_img)
    private CircleImageView headImg;//医生头像
    @InjectId(id = R.id.head_name)
    private TextView headName;//医生名字
    @InjectId(id = R.id.sufferer_num)
    private TextView suffererNum;//患者数量
    @InjectId(id = R.id.follow_num)
    private TextView followNum;//随访数量
    @InjectId(id = R.id.income)
    private TextView income;//医生收入

    private View view;
    @InjectId(id = R.id.home_gridview)
    private LineGridView lineGridView;
    private LineGridViewAdapter lineGridViewAdapter;

    @InjectId(id = R.id.slider)
    private SliderLayout mDemoSlider;

    private String doctorUuid;
    private String vpInfoCache;
    private String doctorInfoCache;
    private ArrayList<PageIconBean> pageIconBeans = new ArrayList<>();
    private DoctorInfoNew doctorInfoNew;

    private GainDoctorInfoNet gainDoctorInfoNet;
    private PagerNet pagerNet;

    private Intent intent;

    @InjectId(id = R.id.list_view)
    private PullToRefreshScrollView pullToRefreshListView;
    private Handler mHandler = new Handler();

    public HomePageFrg() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_frg, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        InjectUtils.injectView(this, getView());
        super.onActivityCreated(savedInstanceState);
        readHeaderInfoFromCache();
        initViews();
        initListeners();
        parseHeaderJSON();
        getData();
    }

    private void parseHeaderJSON() {
        if (!TextUtils.isEmpty(vpInfoCache)) {
            pageIconBeans.clear();
            pageIconBeans = JsonUtils.JsonPageIconResult(vpInfoCache).getPageIconBeans();
            initSlider(pageIconBeans);
        }

        if (!TextUtils.isEmpty(doctorInfoCache)) {
            doctorInfoNew = JsonUtils.JsonDoctorInfoNew(StringUtils.cutoutBracketToString(doctorInfoCache)).getDoctorInfo();
            updateDoctorInfo(doctorInfoNew);
        } else {
            updateLinear(false);
        }
    }

    private void readHeaderInfoFromCache() {
        vpInfoCache = SharedPreferences.getInstance().getString(SharedPreferences.HOME_VP_CACHE, "");
        if (TextUtils.isEmpty(vpInfoCache)) {
            vpInfoCache = Utils.readAssetFileData(this.getContext(), "data/header_vp.txt");
        }
        doctorInfoCache = SharedPreferences.getInstance().getString(SharedPreferences.HOME_DOCTOR_INFO_CACHE, "");
    }

    //获取导航图
    private void getData() {
        pagerNet.getPager();
        if (LoginManager.isHasLogin()){
            getStatus();
        }
    }

    private void initListeners() {
        ((BaseFragmentActivity) getActivity()).addRegisterListener(this);
        gainDoctorInfoNet.setOnGainDoctorInfoListener(this);
        pagerNet.setPagerNetListener(this);
        lineGridView.setOnItemClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        headImg.setOnClickListener(this);
        backImg.setOnClickListener(this);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("--onRefresh-->");
                        loadDoctorInfo();
                    }
                }, 200);
            }
        });
    }


    private void initViews() {
        initViewOnBaseTitle("首页", view);
        backImg.setVisibility(View.VISIBLE);
        backImg.setImageResource(R.mipmap.erweima);

        gainDoctorInfoNet = new GainDoctorInfoNet();
        pagerNet = new PagerNet();

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        lineGridViewAdapter = new LineGridViewAdapter(this.getContext());
        lineGridView.setAdapter(lineGridViewAdapter);
        //防止gridview和scrollview抢焦点
        lineGridView.setFocusable(false);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //   mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomIndicator((PagerIndicator) view.findViewById(R.id.custom_indicator));
        mDemoSlider.addOnPageChangeListener(this);

    }

    private void initSlider(ArrayList<PageIconBean> pageIconBeans) {
        if (pageIconBeans == null || pageIconBeans.size() == 0) return;
        mDemoSlider.removeAllSliders();
        for (PageIconBean pageIconBean : pageIconBeans) {
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(pageIconBean.getNote())
                    .image(pageIconBean.getImageUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("url", pageIconBean.getUrl());
            mDemoSlider.addSlider(textSliderView);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && LoginManager.isHasLogin()) {
            startRefreshing();
        }
    }

    private void startRefreshing() {
        if (pullToRefreshListView != null) {
            pullToRefreshListView.setRefreshing();
        }
    }

    private void stopRefreshing() {
        if (pullToRefreshListView != null) {
            pullToRefreshListView.onRefreshComplete();
        }
    }

    /**
     * 请求网络
     */
    private void loadDoctorInfo() {
        if (LoginManager.isHasLogin()) {
            doctorUuid = LoginManager.getDoctorUuid();
            showDialog("获取医生信息中...");
            gainDoctorInfoNet.gainDoctorInfo(doctorUuid);
        } else {
            if (LoginManager.isQuit_home) {
                LoginManager.isQuit_home = false;
                readHeaderInfoFromCache();
                parseHeaderJSON();
            }
            stopRefreshing();
        }
    }

    /**
     * 更新数据显示
     *
     * @param doctorInfo
     */
    private void updateDoctorInfo(DoctorInfoNew doctorInfo) {
        updateLinear(true);
        System.out.println("doctorInfo--->" + doctorInfo);
        if (!TextUtils.isEmpty(doctorInfo.getDoctorIcon())) {
            ImageLoader.getInstance().displayImage(doctorInfo.getDoctorIcon(), headImg, Utils.initImageLoader(R.mipmap.portrait_man, true));
        }
        headName.setText(doctorInfo.getDoctorName());
        suffererNum.setText(doctorInfo.getCustomerNum() + "");
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
    public void onResume() {
        super.onResume();
        mDemoSlider.startAutoCycle();
    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void requestGainDoctorInfoSuccess(DoctorResultNew doctorResultNew, String str) {
        stopRefreshing();
        if (doctorResultNew == null) return;
        if (doctorResultNew.getQuery().getSuccess().equals("1")) {
            if (doctorResultNew.getDoctorInfo() != null) {
                updateDoctorInfo(doctorResultNew.getDoctorInfo());
                SharedPreferences.getInstance().putString(SharedPreferences.HOME_DOCTOR_INFO_CACHE, str);
            }
            getStatus();
        } else {
            UIHelper.ToastMessage(getActivity(), doctorResultNew.getQuery().getMessage());
        }

    }

    @Override
    public void requestGainDoctorInfoFail() {
        UIHelper.ToastMessage(getActivity(), "请求失败");
        stopRefreshing();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                UIHelper.showLoginForResult(getActivity());
                break;
            case R.id.register_btn:
                UIHelper.showRegister(getActivity());
                break;
            case R.id.head_img:
                QualidicationActivity.toQualidicationActivity(getActivity(), SharedPreferences.getInstance().getString(SharedPreferences.USER_INFO_COMPLETE, "0"));
                break;
            case R.id.back_img:
                CommentWebActivity.toCommentWeb(UrlConstants.getWholeApiUrl(UrlConstants.CURPAGE), null, getActivity(), true);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        startRefreshing();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0://阅读
                intent = new Intent(getActivity(), ReadingActivity.class);
                startActivity(intent);
                break;
            case 1://讲堂
                CommentWebActivity.toCommentWebForResult(UrlConstants.getWholeApiUrl(UrlConstants.GET_VIDEOS), getActivity(), UIHelper.LOGIN_REQUEST_CODE, true);
                break;
            case 2://随访
                if (!TextUtils.isEmpty(((BaseFragmentActivity)getActivity()).isIdenyInfo())){
                    UIHelper.ToastMessage(this.getActivity(),((BaseFragmentActivity)getActivity()).isIdenyInfo());
                    return;
                }

                intent = new Intent(getActivity(), FollowMainActivity.class);
                startActivity(intent);
                break;
            case 3://诊所
                if (!TextUtils.isEmpty(((BaseFragmentActivity)getActivity()).isIdenyInfo())){
                    UIHelper.ToastMessage(this.getActivity(),((BaseFragmentActivity)getActivity()).isIdenyInfo());
                    return;
                }
                CommentWebActivity.toCommentWebForResult(UrlConstants.getWholeApiUrl(UrlConstants.GET_CLINIC), getActivity(), UIHelper.LOGIN_REQUEST_CODE, true);
                break;
        }
    }

    @Override
    public void PagerNetSuccess(PageIconResult pageIconResult, String str) {
        if (pageIconResult == null) return;
        if (pageIconResult.getQuery().getSuccess().equals("1")) {
            pageIconBeans = pageIconResult.getPageIconBeans();
            initSlider(pageIconBeans);
            System.out.println("img--->" + pageIconBeans.toString());
        }
    }

    @Override
    public void PagerNetFail(int statueCode) {

    }

    @Override
    public void onRegisterSuc() {
        startRefreshing();
    }

    @Override
    public void onDestroy() {
        ((BaseFragmentActivity) getActivity()).removeRegisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this.getActivity(), slider.getBundle().get("url") + "", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(slider.getBundle().get("url").toString())) return;
        CommentWebActivity.toCommentWeb(slider.getBundle().get("url").toString(), null, this.getActivity(), false);
    }

    //获取医生的状态
    private void getStatus() {
        doctorUuid=LoginManager.getDoctorUuid();
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", doctorUuid)), StatusResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_SERVICE_STAFFINFO, "2.0"), "正在获取医生信息");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:

                StatusResponse statusResponse = (StatusResponse) bean;
                StatusBean sb = statusResponse.value;
                SharedPreferences.getInstance().putString(SharedPreferences.USER_INFO_COMPLETE, sb.getSate());
                break;
        }
    }
}
