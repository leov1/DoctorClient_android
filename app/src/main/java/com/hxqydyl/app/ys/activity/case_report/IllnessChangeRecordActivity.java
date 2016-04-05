package com.hxqydyl.app.ys.activity.case_report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.IllnessChangeRecordAdapter;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/4/6.
 */
public class IllnessChangeRecordActivity extends BaseTitleActivity implements View.OnClickListener {
    @InjectId(id = R.id.lvIllnessChangeRecord)
    private ListView lvIllnessChangeRecord;
    private IllnessChangeRecordAdapter illnessChangeAdapter;
    private ArrayList<IllnessChangeRecord> changeRecords = new ArrayList<IllnessChangeRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness_change_record);

        initViewOnBaseTitle(getString(R.string.illness_change));
        setBackListener(this);

        InjectUtils.injectView(this);

        initTestData();
        illnessChangeAdapter = new IllnessChangeRecordAdapter(this, changeRecords);
        lvIllnessChangeRecord.setAdapter(illnessChangeAdapter);
        lvIllnessChangeRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IllnessChangeRecord record = (IllnessChangeRecord) parent.getItemAtPosition(position);
                if(record!=null){
                    Intent intent = new Intent(IllnessChangeRecordActivity.this,IllnessChangeDetalsActivity.class);
                    intent.putExtra("illness_change_record",record);
                    startActivity(intent);
                }
            }
        });
    }

    private void initTestData() {
        IllnessChangeRecord record = new IllnessChangeRecord();
        record.setStatus("无效");
        record.setDescription("吃了药睡不着，数羊数到1000只也睡不着");
        record.setTime("2016-03-21");
        changeRecords.add(record);
        record = new IllnessChangeRecord();
        record.setStatus("好转");
        record.setDescription("数羊数羊数到1001只的时候，我好像快睡着了");
        record.setTime("2016-03-21");
        changeRecords.add(record);
        record = new IllnessChangeRecord();
        record.setStatus("痊愈");
        record.setDescription("第1005只，第1006只..... 第10001只，第10002只......");
        record.setTime("2016-03-21");
        changeRecords.add(record);
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }
}
