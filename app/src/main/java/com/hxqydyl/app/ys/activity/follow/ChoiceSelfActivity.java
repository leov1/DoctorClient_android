package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.os.Bundle;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.adapter.ChoiceScaleAdapter;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyScrollListView;

/**
 * 自评量表选择页面
 */
public class ChoiceSelfActivity extends Activity {

    private MyScrollListView listView;
    private ChoiceScaleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_scale);

        initViews();
        initListeners();
    }

    private void initListeners() {

    }

    private void initViews() {
        listView = (MyScrollListView) findViewById(R.id.list_view);
        adapter = new ChoiceScaleAdapter(this);
        listView.setAdapter(adapter);
    }
}
