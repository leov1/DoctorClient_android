package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.IllnessChangeAdapter;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/6.
 */
public class IllnessChangeDetalsActivity extends BaseTitleActivity implements View.OnClickListener {
    public static final String KEY_ILLNESS_CHANGE_RECORD = "illness_change_record";
    @InjectId(id = R.id.lvIllnessChange)
    private ListView lvIllnessChange;
    private IllnessChangeAdapter illnessChangeAdapter;
    private ArrayList<IllnessChange> changes = new ArrayList<IllnessChange>();

    private CaseReportNet caseReportNet;
    private IllnessChangeRecord illnessChangeRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        illnessChangeRecord = (IllnessChangeRecord) getIntent().getSerializableExtra(KEY_ILLNESS_CHANGE_RECORD);
        if (illnessChangeRecord == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_illness_change_details);

        initViewOnBaseTitle(getString(R.string.illness_change));
        setBackListener(this);

        InjectUtils.injectView(this);
//        initTestData();
        illnessChangeAdapter = new IllnessChangeAdapter(this, changes);
        lvIllnessChange.setAdapter(illnessChangeAdapter);

        caseReportNet = new CaseReportNet(this);
        caseReportNet.getIllnessChangeDetails(illnessChangeRecord.getId());
    }

    private void initTestData() {
        IllnessChange change = new IllnessChange();
        change.setType(IllnessChange.Type.ILL);
        change.setStatus(IllnessChange.Status.INVALID);
        change.setDescription("病情变化描述详情");
        changes.add(change);
        change = new IllnessChange();
        change.setType(IllnessChange.Type.SLEEP);
        change.setStatus(IllnessChange.Status.BETTER);
        change.setDescription("病情变化描述详情");
        changes.add(change);
        change = new IllnessChange();
        change.setType(IllnessChange.Type.FOOD);
        change.setStatus(IllnessChange.Status.BEST);
        change.setDescription("病情变化描述详情");
        changes.add(change);
        change = new IllnessChange();
        change.setType(IllnessChange.Type.OTHER);
        change.setDescription("病情变化描述详情");
        changes.add(change);
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        changes.clear();
        changes.addAll((ArrayList<IllnessChange>) result);
        illnessChangeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
