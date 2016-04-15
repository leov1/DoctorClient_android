package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.ChoiceScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.http.follow.FollowPlanNet;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyScrollListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 医评量表选择
 */
public class ChoiceScaleActivity extends BaseTitleActivity implements View.OnClickListener {

    private MyScrollListView listView;
    private ChoiceScaleAdapter adapter;
    private Button btnOk;

    private ArrayList<Scale> list;
    private ArrayList<Scale> selectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_scale);
        initViewOnBaseTitle("医评量表选择");
        initViews();
        initListeners();
        selectPreceptDetail();
    }

    private void initListeners() {
        setBackListener();
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
                resultIntent.putExtra("list", selectList);
                setResult(2, resultIntent);
                finish();
                break;
        }
    }

    private void selectPreceptDetail() {
        FollowPlanNet.selectPreceptDetail("1", new FollowCallback(this){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (FollowApplyNet.myDev)
                    response = "[" +
                        "{" +
                        "        \"id\": \"0000\", " +
                        "        \"title\": \"医生评测1\", " +
                        "        \"digest\": \"本测验适用对象包括初中生至成人(16岁以上)...\", " +
                        "        \"self\": \"0\"" +
                        "    }," +
                        "{" +
                        "        \"id\": \"0000\", " +
                        "        \"title\": \"医生评测2\", " +
                        "        \"digest\": \"本测验适用对象包括初中生至成人(16岁以上)...\", " +
                        "        \"self\": \"0\"" +
                        "    }" +
                        "]";
                List<Scale> tmp = null;
                try {
                    tmp = Scale.parse(response);
                    list.clear();
                    list.addAll(tmp);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
