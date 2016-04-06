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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.clinic.ClinicActivity;
import com.hxqydyl.app.ys.activity.follow.FollowMainActivity;
import com.hxqydyl.app.ys.activity.reading.ReadingActivity;
import com.hxqydyl.app.ys.activity.video.VideoActivity;
import com.hxqydyl.app.ys.adapter.GalleryPagerAdapter;
import com.hxqydyl.app.ys.adapter.LineGridViewAdapter;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.homepage.PageIconBean;
import com.hxqydyl.app.ys.bean.homepage.PageIconResult;
import com.hxqydyl.app.ys.bean.register.DoctorInfoNew;
import com.hxqydyl.app.ys.bean.register.DoctorResultNew;
import com.hxqydyl.app.ys.http.JsonUtils;
import com.hxqydyl.app.ys.http.homepage.GainDoctorInfoNet;
import com.hxqydyl.app.ys.http.homepage.PagerNet;
import com.hxqydyl.app.ys.http.login.QuitLoginNet;
import com.hxqydyl.app.ys.ui.CircleImageView;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.library.PullToRefreshBase;
import com.hxqydyl.app.ys.ui.library.PullToRefreshListView;
import com.hxqydyl.app.ys.ui.linegridview.LineGridView;
import com.hxqydyl.app.ys.ui.loopviewpager.AutoLoopViewPager;
import com.hxqydyl.app.ys.ui.viewpagerindicator.CirclePageIndicator;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.hxqydyl.app.ys.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * 首页frg
 */
public class HomePageFrg extends BaseFragment implements GainDoctorInfoNet.OnGainDoctorInfoListener, View.OnClickListener
        , AdapterView.OnItemClickListener, QuitLoginNet.OnQuitLoginListener
        , PagerNet.OnPagerNetListener {

    private LinearLayout loginLiear;
    private LinearLayout noLoginLinear;
    private TextView loginBtn;
    private TextView registerBtn;

    private CircleImageView headImg;//医生头像
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
    private String vpInfoCache;
    private String doctorInfoCache;
    private ArrayList<PageIconBean> pageIconBeans = new ArrayList<>();
    private DoctorInfoNew doctorInfoNew;

    private GainDoctorInfoNet gainDoctorInfoNet;
    private QuitLoginNet quitLoginNet;
    private PagerNet pagerNet;

    private View mHeader;
    private ListView listView;
    private Intent intent;

    private PullToRefreshListView pullToRefreshListView;
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
        super.onActivityCreated(savedInstanceState);
        readHeaderInfoFromCache();
        initViews();
        initHeadView();
        initListeners();
        parseHeaderJSON();
        getData();
    }

    private void parseHeaderJSON() {
        if (!TextUtils.isEmpty(vpInfoCache)) {
            pageIconBeans.clear();
            try {
                pageIconBeans = JsonUtils.JsonPageIconResult(vpInfoCache).getPageIconBeans();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            galleryAdapter = new GalleryPagerAdapter(this.getContext(), pageIconBeans);
            pager.setAdapter(galleryAdapter);
            indicator.setViewPager(pager);
            indicator.setPadding(5, 5, 10, 5);
        }

        if (!TextUtils.isEmpty(doctorInfoCache)) {
            doctorInfoNew = JsonUtils.JsonDoctorInfoNew(StringUtils.cutoutBracketToString(doctorInfoCache)).getDoctorInfo();
            updateDoctorInfo(doctorInfoNew);
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
    }

    private void initListeners() {
        gainDoctorInfoNet.setOnGainDoctorInfoListener(this);
        quitLoginNet.setListener(this);
        pagerNet.setPagerNetListener(this);
        lineGridView.setOnItemClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        headImg.setOnClickListener(this);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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

    private void initHeadView() {
        mHeader = View.inflate(this.getActivity(), R.layout.home_header, null);
        noLoginLinear = (LinearLayout) mHeader.findViewById(R.id.not_login_linear);
        loginLiear = (LinearLayout) mHeader.findViewById(R.id.login_linear);
        //已经登陆

        headImg = (CircleImageView) mHeader.findViewById(R.id.head_img);
        headName = (TextView) mHeader.findViewById(R.id.head_name);
        suffererNum = (TextView) mHeader.findViewById(R.id.sufferer_num);
        followNum = (TextView) mHeader.findViewById(R.id.follow_num);
        income = (TextView) mHeader.findViewById(R.id.income);

        loginBtn = (TextView) mHeader.findViewById(R.id.login_btn);
        registerBtn = (TextView) mHeader.findViewById(R.id.register_btn);

        lineGridView = (LineGridView) mHeader.findViewById(R.id.home_gridview);
        lineGridViewAdapter = new LineGridViewAdapter(this.getContext());
        lineGridView.setAdapter(lineGridViewAdapter);

        pager = (AutoLoopViewPager) mHeader.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) mHeader.findViewById(R.id.indicator);

        listView.addHeaderView(mHeader);
        listView.setAdapter(null);
    }

    private void initViews() {
        initViewOnBaseTitle("首页", view);

        gainDoctorInfoNet = new GainDoctorInfoNet();
        quitLoginNet = new QuitLoginNet();
        pagerNet = new PagerNet();

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list_view);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setHeaderDividersEnabled(false);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        startRefreshing();
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
        System.out.println("isLogin-->" + LoginManager.isHasLogin());
        System.out.println("doctoruuid-->" + LoginManager.getDoctorUuid());
        if (LoginManager.isHasLogin()) {
            doctorUuid = LoginManager.getDoctorUuid();
            gainDoctorInfoNet.gainDoctorInfo(doctorUuid);
        }else {
            stopRefreshing();
        }
    }

    /**
     * 更新数据显示
     *
     * @param doctorInfo
     */
    private void updateDoctorInfo(DoctorInfoNew doctorInfo) {
        updateLinear(LoginManager.isHasLogin());
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
    public void onPause() {
        super.onPause();
        pager.stopAutoScroll();
    }

    @Override
    public void requestGainDoctorInfoSuccess(DoctorResultNew doctorResultNew,String str) {
        stopRefreshing();
        if (doctorResultNew == null) return;
        if (doctorResultNew.getQuery().getSuccess().equals("1")) {
            updateDoctorInfo(doctorResultNew.getDoctorInfo());
            SharedPreferences.getInstance().putString(SharedPreferences.HOME_DOCTOR_INFO_CACHE, str);
        } else {
            UIHelper.ToastMessage(getActivity(), doctorResultNew.getQuery().getMessage());
        }

    }

    @Override
    public void requestGainDoctorInfoFail(int statueCode) {
        UIHelper.ToastMessage(getActivity(), "请求失败");
        stopRefreshing();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                UIHelper.showLogin(getActivity());
                break;
            case R.id.register_btn:
                UIHelper.showRegister(getActivity());
                break;
            case R.id.head_img:
                quitLogin();
                break;
        }
    }

    /**
     * 退出登陆
     */
    private void quitLogin() {
        quitLoginNet.quit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
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

    @Override
    public void requestQuitSuc(Query query) {

        if (query == null) return;
        System.out.println("response-quit-->" + query.toString());
        if (query.getSuccess().equals("1")) {
            LoginManager.quitLogin();
            updateLinear(false);
            UIHelper.ToastMessage(getActivity(), "退出登陆成功");
        } else {
            UIHelper.ToastMessage(getActivity(), query.getMessage());
        }
    }

    @Override
    public void requestQuitFail() {
        UIHelper.ToastMessage(getActivity(), "退出失败");
    }

    @Override
    public void PagerNetSuccess(PageIconResult pageIconResult,String str) {
        if (pageIconResult == null) return;
        if (pageIconResult.getQuery().getSuccess().equals("1")) {
            pageIconBeans = pageIconResult.getPageIconBeans();
            galleryAdapter.update(pageIconBeans);
            SharedPreferences.getInstance().putString(SharedPreferences.HOME_VP_CACHE, str);
            System.out.println("img--->" + pageIconBeans.toString());
        }
    }

    @Override
    public void PagerNetFail(int statueCode) {

    }
}
