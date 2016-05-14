package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.adapter.ChoiceHosAdapter;
import com.hxqydyl.app.ys.bean.register.HospitalsBean;
import com.hxqydyl.app.ys.bean.response.HospitalResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxu on 2016/5/13.
 */
public class SelectHosActivity extends BaseRequstActivity {
    private String provinceId;
    private String ragionCode;
    private String cityId;
    private MyScrollListView listView;
    private ChoiceHosAdapter adapter;
    private Button btnOk;
    private List<HospitalsBean> list = new ArrayList<>();

    private void getHos() {
        toNomalNet(toGetParams(toParamsBaen("regionUuid", ragionCode), toParamsBaen("cityUuid", cityId), toParamsBaen("provinceUuid", provinceId)), HospitalResponse.class, 4, UrlConstants.getWholeApiUrl(UrlConstants.GET_HOSPITAL, "2.0"), "正在获取医院列表");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hos);
        provinceId=getIntent().getExtras().getString("provinceId");
        ragionCode=getIntent().getExtras().getString("ragionCode");
        cityId=getIntent().getExtras().getString("cityId");
        initViewOnBaseTitle("自评量表选择");
        setBackListener();
        initViews();
        getHos();
    }

    private void initViews() {
        listView = (MyScrollListView) findViewById(R.id.list_view);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HospitalsBean hb = adapter.getIsSelect();
                if (hb == null) {
                    UIHelper.ToastMessage(SelectHosActivity.this, "请选择医院");
                    return;
                }else{
                    Intent intent=  new Intent();
                    intent .putExtra("hosId",hb.getId());
                    intent.putExtra("hosName",hb.getHospitalName());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        HospitalResponse hr = (HospitalResponse) bean;
        list = hr.value;
        if (list == null || list.size() <= 0) {
            UIHelper.ToastMessage(this, "暂无可选医院");
            return;
        }
        adapter = new ChoiceHosAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
