package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.case_report.FollowUpFormActivity;
import com.hxqydyl.app.ys.adapter.FollowTaskAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.follow.FollowTask;
import com.hxqydyl.app.ys.bean.response.FollowTaskListReponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 随访任务
 */
public class FollowTaskActivity extends BaseRequstActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private FollowTaskAdapter adapter;
    private List<FollowTask> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_apply);
        initViews();
        initListeners();
        getProcessedVisitList();
    }

    private void initViews() {
        initViewOnBaseTitle("待处理随访任务");
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new FollowTaskAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void initListeners() {
        setBackListener();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FollowTask task = list.get(position);
        Patient p = new Patient();
        p.setRealName(task.getRealName());
        p.setId(task.getCustomerUuid());

        PatientTreatInfo pti = new PatientTreatInfo();
        pti.setId(task.getApplyUuid());

        Intent intent = new Intent(this, FollowUpFormActivity.class);
        intent.putExtra("treat_info", pti);
        intent.putExtra("patient", p);
        startActivity(intent);
    }

    public void getProcessedVisitList() {
//        String url="http://172.168.1.53/app/pub/doctor/2.0/getProcessedVisitList";
//        toNomalNetStringBack(toGetParams(toParamsBaen("doctorUuid",LoginManager.getDoctorUuid())),1, url,"正在获取随访任务");
//        .addParams("doctorUuid", LoginManager.getDoctorUuid())

//
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), FollowTaskListReponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PROCESSED_VISITLIST, "2.0"), "正在获取随访任务");
////        showDialog("加载中");
//        FollowApplyNet.getProcessedVisitList(new FollowCallback(this){
//            @Override
//            public void onFail(String status, String msg) {
//                super.onFail(status, msg);
//                UIHelper.ToastMessage(FollowTaskActivity.this,msg);
//            }
//
//            @Override
//            public void onResult(String result) {
//                super.onResult(result);
//                dismissDialog();
//                if (FollowApplyNet.myDev)
//                    result = "[" +
//                            "    {" +
//                            "        \"imgUrl\": \"http://101.201.150.49:7500/dev1/0/000/001/0000001978.fid\"," +
//                            "        \"realName\": \"小马小马小马小马小马小马小马\"," +
//                            "        \"applyUuid\": \"0ef34b3fabbd44b1b9e1d72c0350a552\"," +
//                            "        \"customerUuid\": \"9ee56d1310b54baa97f5a8abbe85a0b1\"," +
//                            "        \"createTime\": \"2016-03-19\"," +
//                            "        \"illnessDescription\": \"illnessDescriptionillnessDescription\"," +
//                            "        \"doctorUuid\": \"rvicestaff0000001961\"," +
//                            "        \"sex\": \"1\"," +
//                            "        \"age\": \"26\"" +
//                            "    }" +
//                            "]";
//
//                dismissDialog();
//                try{
//                    List<FollowTask> tmp = FollowTask.parseList(result);
//                    if (tmp.size() > 0) {
//                        list.clear();
//                        list.addAll(tmp);
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(FollowTaskActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    onFail("","解析出错啦，再刷新一次吧");
//                }

    }

//            @Override
//            public void onError(Call call, Exception e) {
//                super.onError(call, e);
//                dismissDialog();
//            }
//        });
//    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        FollowTaskListReponse ftlr = (FollowTaskListReponse) bean;
        list.clear();
        if (ftlr.value != null && ftlr.value.size() > 0) {
            list.addAll(ftlr.value);
            adapter.notifyDataSetChanged();
        } else {
            UIHelper.ToastMessage(this, "暂无随访任务");
        }
    }
}
