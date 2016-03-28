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
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.PlanMgrAdapter;
import com.hxqydyl.app.ys.bean.plan.Plan;
import com.hxqydyl.app.ys.ui.UIHelper;

import java.util.ArrayList;
import java.util.List;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * 随访方案管理页面
 */
public class PlanMgrActivity extends BaseTitleActivity implements View.OnClickListener{

    private SwipeMenuListView swipeMenuListView;
    private PlanMgrAdapter adapter;
    private RelativeLayout reLayoutAddPlan;
    private List<Plan> myPlanList;

    private ListView lvSuggestPlan;
    private PlanMgrAdapter suggestPlanAdapter;
    private List<Plan> suggestPlanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_mgr);

        initViews();
        initListeners();
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
                        UIHelper.ToastMessage(PlanMgrActivity.this, "删除");
                        break;
                }
                return false;
            }
        });

        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanMgrActivity.this, PlanEditActivity.class);
                startActivity(intent);
            }
        });

        lvSuggestPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanMgrActivity.this, PlanEditActivity.class);
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
        myPlanList.add(new Plan("我的方案111"));
        myPlanList.add(new Plan("我的方案222"));
        myPlanList.add(new Plan("我的方案333"));
        myPlanList.add(new Plan("我的方案444"));
        adapter = new PlanMgrAdapter(this, myPlanList);
        swipeMenuListView.setAdapter(adapter);

        lvSuggestPlan = (ListView) findViewById(R.id.lvSuggestPlan);
        suggestPlanList = new ArrayList<>();
        suggestPlanList.add(new Plan("建议方案111"));
        suggestPlanList.add(new Plan("建议方案222"));
        suggestPlanList.add(new Plan("建议方案333"));
        suggestPlanList.add(new Plan("建议方案444"));
        suggestPlanAdapter = new PlanMgrAdapter(this, suggestPlanList, false);
        lvSuggestPlan.setAdapter(suggestPlanAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
}
