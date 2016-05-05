package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.bean.request.BaseRequest;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.FollowUserApplyResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.xus.http.httplib.model.GetParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 随访申请
 */
public class FollowApplyActivity extends BaseRequstActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private FollowApplyAdapter adapter;
    private List<FollowApply> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply);

        initViews();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVisitApplyList();
    }

    private void initViews() {
        initViewOnBaseTitle("随访申请");
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new FollowApplyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initListeners() {
        setBackListener();
    }

    private void getVisitApplyList() {
        String url = "http://172.168.1.53/app/pub/doctor/2.0/getVisitApplyList";
        GetParams params = toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid()));
        toNomalNet(params, FollowUserApplyResponse.class, 1, url, "正在获取关联列表...");

//        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), FollowUserApplyResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_VISIT_APPLYLIST, "2.0"), "正在获取关联列表...");
//        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())),  1, UrlConstants.getWholeApiUrl(UrlConstants.GET_VISIT_APPLYLIST, "1.0"), "正在获取关联列表...");
    }


    @Override
    public void onSuccessToBean(Object bean, int flag) {
        FollowUserApplyResponse rs = (FollowUserApplyResponse) bean;
        list.clear();
        if (rs.value != null && rs.value.size() > 0) {
            list.addAll(rs.value);
        } else {
            Toast.makeText(FollowApplyActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, FollowApplyDetailActivity.class);
        intent.putExtra("applyUuid", list.get(position).getApplyUuid());
        intent.putExtra("avatar", list.get(position).getImgUrl());
        startActivity(intent);
    }
}
