package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.case_report.FollowUpFormActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.adapter.FollowTaskAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.bean.follow.FollowTask;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 随访任务
 */
public class FollowTaskActivity extends BaseTitleActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{

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
        p.setName(task.getRealName());
        p.setId(task.getCustomerUuid());

        PatientTreatInfo pti = new PatientTreatInfo();
        pti.setId(task.getApplyUuid());

        Intent intent = new Intent(this, FollowUpFormActivity.class);
        intent.putExtra("treat_info", pti);
        intent.putExtra("patient", p);
        startActivity(intent);
    }

    public void getProcessedVisitList() {
        showDialog("加载中");
        FollowApplyNet.getProcessedVisitList(new FollowCallback(this){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                dismissDialog();
                if (FollowApplyNet.myDev)
                    result = "[" +
                            "    {" +
                            "        \"imgUrl\": \"http://101.201.150.49:7500/dev1/0/000/001/0000001978.fid\"," +
                            "        \"realName\": \"小马小马小马小马小马小马小马\"," +
                            "        \"applyUuid\": \"0ef34b3fabbd44b1b9e1d72c0350a552\"," +
                            "        \"customerUuid\": \"9ee56d1310b54baa97f5a8abbe85a0b1\"," +
                            "        \"createTime\": \"2016-03-19\"," +
                            "        \"illnessDescription\": \"illnessDescriptionillnessDescription\"," +
                            "        \"doctorUuid\": \"rvicestaff0000001961\"," +
                            "        \"sex\": \"1\"," +
                            "        \"age\": \"26\"" +
                            "    }" +
                            "]";

                dismissDialog();
                List<FollowTask> tmp = FollowTask.parseList(result);
                if (tmp.size() > 0) {
                    list.clear();
                    list.addAll(tmp);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FollowTaskActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                dismissDialog();
            }
        });
    }
}
