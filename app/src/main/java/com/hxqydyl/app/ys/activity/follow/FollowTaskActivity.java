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
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 待处理随访任务
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

        FollowUpFormActivity.newIntent(this, pti, p);
    }

    public void getProcessedVisitList() {
        toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), FollowTaskListReponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_PROCESSED_VISITLIST, "2.0"), "正在获取随访任务");
    }

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
