package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.ChoiceScaleAdapter;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自评量表选择页面
 */
public class ChoiceSelfActivity extends BaseTitleActivity implements View.OnClickListener {

    private MyScrollListView listView;
    private ChoiceScaleAdapter adapter;
    private Button btnOk;

    private List<String> list;
    private ArrayList<String> selectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_scale);
        initViewOnBaseTitle("自评量表选择");
        initViews();
        initListeners();
    }

    private void initListeners() {
        btnOk.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChoiceScaleAdapter.ViewHolder holder = (ChoiceScaleAdapter.ViewHolder) view.getTag();
                holder.checkBox.toggle();
                ChoiceScaleAdapter.getIsSelectMap().put(position, holder.checkBox.isChecked());
                if (holder.checkBox.isChecked()) {
                    selectList.add(list.get(position));
                } else {
                    selectList.remove(list.get(position));
                }
            }
        });
    }

    private void initViews() {
        listView = (MyScrollListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        selectList = new ArrayList<>();
        list.add("SDS（抑郁自评量表）1");
        list.add("SDS（抑郁自评量表）2");
        list.add("SDS（抑郁自评量表）3");
        list.add("SDS（抑郁自评量表）4");
        list.add("SDS（抑郁自评量表）5");
        adapter = new ChoiceScaleAdapter(this, list);
        listView.setAdapter(adapter);
        btnOk = (Button) findViewById(R.id.btnOk);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                if (selectList.size() == 0) {
                    return;
                }
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("list", selectList);
                setResult(1, resultIntent);
                finish();
                break;
        }
    }
}
