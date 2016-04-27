package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PlanMgrAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.Plan;
import com.hxqydyl.app.ys.bean.follow.plan.PlanBaseInfo;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.FollowUserApplyResponse;
import com.hxqydyl.app.ys.bean.response.PlanInfoListResponse;
import com.hxqydyl.app.ys.bean.response.PlanListResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * 随访方案管理页面
 */
public class PlanMgrActivity extends BaseRequstActivity implements View.OnClickListener {

    private SwipeMenuListView swipeMenuListView;
    private PlanMgrAdapter adapter;
    private RelativeLayout reLayoutAddPlan;
    private List<Plan> myPlanList;

    private ListView lvSuggestPlan;
    private PlanMgrAdapter suggestPlanAdapter;
    private List<Plan> suggestPlanList;
    private int delectPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_mgr);

        initViews();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyVisitPreceptList();
    }

    private void initListeners() {
        setBackListener(this);

        swipeMenuListView.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(31, 128, 183)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        });

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delPreceptDetail(position);
                        break;
                }
                return false;
            }
        });

        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanMgrActivity.this, PlanInfoActivity.class);
                intent.putExtra("visitUuid", myPlanList.get(position).getVisitUuid());
                intent.putExtra("from", "my");
                intent.putExtra("preceptName", myPlanList.get(position).getPreceptName());
                startActivity(intent);
            }
        });

        lvSuggestPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanMgrActivity.this, PlanInfoActivity.class);
                intent.putExtra("visitUuid", suggestPlanList.get(position).getVisitUuid());
                intent.putExtra("from", "suggest");
                intent.putExtra("preceptName", suggestPlanList.get(position).getPreceptName());
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        initViewOnBaseTitle("随访方案管理");
        reLayoutAddPlan = (RelativeLayout) findViewById(R.id.rl_add_plan);
        reLayoutAddPlan.setOnClickListener(this);
        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipe_menu_lv);
        myPlanList = new ArrayList<>();
        adapter = new PlanMgrAdapter(this, myPlanList);
        swipeMenuListView.setAdapter(adapter);

        lvSuggestPlan = (ListView) findViewById(R.id.lvSuggestPlan);
        suggestPlanList = new ArrayList<>();
        suggestPlanAdapter = new PlanMgrAdapter(this, suggestPlanList, false);
        lvSuggestPlan.setAdapter(suggestPlanAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.rl_add_plan:
                Intent intent = new Intent(this, PlanEditActivity.class);
                startActivity(intent);
                break;

        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    //获取我的方案
    private void getMyVisitPreceptList() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PlanInfoListResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_MYVISIT_PRECEPTLIST, "1.0"), "正在获取方案列表");
    }

    //获取推荐的随访方案
    private void getRecommendVisitpreceptByDoctorid() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), PlanInfoListResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.GET_RECOMMEND_VISITPRECEPT_BY_DOCTORID, "1.0"), "正在推荐列表");
    }

    //删除方案
    private void delPreceptDetail(final int position) {
        Plan plan = myPlanList.get(position);
        delectPos = position;
        toNomalNet(toPostParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()), toParamsBaen("visitUuid", plan.getVisitUuid())), BaseResponse.class, 3, UrlConstants.getWholeApiUrl(UrlConstants.DEL_PRECEPT_DETAIL, "1.0"), "正在删除方案");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                PlanInfoListResponse pilr = (PlanInfoListResponse) bean;
                myPlanList.clear();
                if (pilr.getRelist() != null && pilr.getRelist().size() > 0) {
                    myPlanList.addAll(pilr.getRelist());
                }
                adapter.notifyDataSetChanged();
                getRecommendVisitpreceptByDoctorid();
                break;

            case 2:
                PlanInfoListResponse pilr2 = (PlanInfoListResponse) bean;
                suggestPlanList.clear();
                if (pilr2.getRelist() != null && pilr2.getRelist().size() > 0) {
                    suggestPlanList.addAll(pilr2.getRelist());
                }
                suggestPlanAdapter.notifyDataSetChanged();
                break;
            case 3:
                myPlanList.remove(delectPos);
                adapter.notifyDataSetChanged();
                UIHelper.ToastMessage(PlanMgrActivity.this, "删除成功");
                break;
        }
    }
}
