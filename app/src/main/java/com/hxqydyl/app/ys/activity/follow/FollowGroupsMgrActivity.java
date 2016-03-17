package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.adapter.FollowGroupsMgrAdapter;
import com.hxqydyl.app.ys.ui.UIHelper;

import ui.swipemenulistview.SwipeMenu;
import ui.swipemenulistview.SwipeMenuCreator;
import ui.swipemenulistview.SwipeMenuItem;
import ui.swipemenulistview.SwipeMenuListView;

/**
 * 随访分组页面
 */
public class FollowGroupsMgrActivity extends Activity {

    private SwipeMenuListView swipeMenuListView;
    private FollowGroupsMgrAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_groups_mgr);

        initViews();
        initListeners();
    }

    private void initViews() {
        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.swipe_menu_lv);
        adapter = new FollowGroupsMgrAdapter(this);
        swipeMenuListView.setAdapter(adapter);
    }

    private void initListeners() {

        swipeMenuListView.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        });

       swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
               switch (index){
                   case 0:
                       UIHelper.ToastMessage(FollowGroupsMgrActivity.this,"删除");
                       break;
               }
               return false;
           }
       });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
