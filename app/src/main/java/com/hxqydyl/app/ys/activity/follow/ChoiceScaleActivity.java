package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.ChoiceScaleAdapter;
import com.hxqydyl.app.ys.bean.follow.plan.Scale;
import com.hxqydyl.app.ys.bean.response.TestListResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 医评量表选择
 */
public class ChoiceScaleActivity extends BaseRequstActivity implements View.OnClickListener {

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
        toNomalNet(toGetParams(toParamsBaen("type", "1")), TestListResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.SELECT_PRECEPT_DETAIL, "1.0"), "正在获取测试列表");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        TestListResponse tlr = (TestListResponse) bean;
        list.clear();

        if (tlr.getRelist() == null || tlr.getRelist().size() == 0) {
            UIHelper.ToastMessage(this, "暂无数据..");
            return;
        }
        list.addAll(tlr.getRelist());
        adapter.notifyDataSetChanged();
    }
}
