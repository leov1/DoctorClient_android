package com.hxq.hxq.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxq.hxq.R;
import com.hxq.hxq.adapter.GalleryPagerAdapter;
import com.hxq.hxq.adapter.LineGridViewAdapter;
import com.hxq.hxq.bean.DoctorInfo;
import com.hxq.hxq.bean.DoctorInfoNew;
import com.hxq.hxq.bean.DoctorResult;
import com.hxq.hxq.bean.DoctorResultNew;
import com.hxq.hxq.http.OkHttpClientManager;
import com.hxq.hxq.ui.linegridview.LineGridView;
import com.hxq.hxq.ui.loopviewpager.AutoLoopViewPager;
import com.hxq.hxq.ui.viewpagerindicator.CirclePageIndicator;
import com.hxq.hxq.utils.Constants;
import com.hxq.hxq.utils.SharedPreferences;
import com.hxq.hxq.utils.StringUtils;
import com.squareup.okhttp.Request;

import org.w3c.dom.Text;

/**
 * 首页frg
 */
public class HomePageFrg extends Fragment {

    private LinearLayout loginLiear;
    private LinearLayout noLoginLinear;
    private TextView loginBtn;
    private TextView registerBtn;

    private ImageView headImg;//医生头像
    private TextView headName;//医生名字
    private TextView suffererNum;//患者数量
    private TextView followNum;//随访数量

    private View view;
    private LineGridView lineGridView;
    private LineGridViewAdapter lineGridViewAdapter;

    private AutoLoopViewPager pager;
    private CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;

    private boolean isLogin;
    private String doctorUuid;

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
        isLogin = SharedPreferences.getInstance().getBoolean(SharedPreferences.KEY_LOGIN_TYPE, false);
        initViews();
    }

    private void initViews() {
        noLoginLinear = (LinearLayout) view.findViewById(R.id.not_login_linear);
        loginLiear = (LinearLayout) view.findViewById(R.id.login_linear);
        //已经登陆
        if (isLogin) {
            noLoginLinear.setVisibility(View.GONE);
            loginLiear.setVisibility(View.VISIBLE);
            headImg = (ImageView) view.findViewById(R.id.head_img);
            headName = (TextView) view.findViewById(R.id.head_name);
            suffererNum = (TextView) view.findViewById(R.id.sufferer_num);
            followNum = (TextView) view.findViewById(R.id.follow_num);
        }else{
            noLoginLinear.setVisibility(View.VISIBLE);
            loginLiear.setVisibility(View.GONE);
            loginBtn = (TextView) view.findViewById(R.id.login_btn);
            registerBtn = (TextView) view.findViewById(R.id.register_btn);
        }

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
        isLogin = SharedPreferences.getInstance().getBoolean(SharedPreferences.KEY_LOGIN_TYPE, false);
        if (isLogin) {
            doctorUuid = SharedPreferences.getInstance().getString("doctorUuid", "");
            loadDoctorInfo();
        }

    }

    private void loadDoctorInfo() {
        OkHttpClientManager.getAsyn(Constants.GET_DOCTOR_INFO + "?doctorUuid=" + doctorUuid + "&callback=xch", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
               System.out.println("response--->"+response);
               DoctorResultNew doctorResult = new Gson().fromJson(StringUtils.cutoutBracketToString(response), DoctorResultNew.class);
               updateDoctorInfo(doctorResult.getDoctorInfo());
            }
        });
    }

    private void updateDoctorInfo(DoctorInfoNew doctorInfo){
        OkHttpClientManager.getDisplayImageDelegate().displayImage(headImg,doctorInfo.getDoctorIcon());
        headName.setText(doctorInfo.getDoctorName());
        suffererNum.setText(doctorInfo.getCustomerNum());
        followNum.setText(doctorInfo.getVisitNum());
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
}
