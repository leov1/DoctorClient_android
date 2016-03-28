package com.hxqydyl.app.ys.activity.case_report;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientSimpleInfoViewHolder;
import com.hxqydyl.app.ys.adapter.FollowUpFormAdapter;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.bean.followupform.BadReactionRecord;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormOneRecord;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;
import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;
import com.hxqydyl.app.ys.bean.followupform.OtherCheckRecord;
import com.hxqydyl.app.ys.bean.followupform.WeightRecord;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/23.
 */
public class FollowUpFormActivity extends BaseTitleActivity implements View.OnClickListener{
    private PatientSimpleInfoViewHolder simpleInfoViewHolder;
    @InjectId(id = R.id.lvForm)
    private ExpandableListView lvForm;
    private ArrayList<FollowUpFormGroup> formList = new ArrayList<FollowUpFormGroup>();

    @InjectId(id = R.id.bDoctorAdvice)
    private Button bDoctorAdvice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_form);

        initViewOnBaseTitle(String.format(getString(R.string.follow_up_form_title),"林志玲"));
        setBackListener(this);

        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        InjectUtils.injectView(this);
        initTestData();
        FollowUpFormAdapter adapter = new FollowUpFormAdapter(this,formList);
//        lvForm.setAdapter(adapter);

        bDoctorAdvice.setOnClickListener(this);
    }

    private void initTestData() {
        for(int i=0;i<6;i++){
            FollowUpFormGroup group = new FollowUpFormGroup(i+1);
            FollowUpFormOneRecord record = null;
            switch(group.getFormGroupType()){
                case FollowUpFormGroup.Type.ILLNESS_CHANGE:
                    record = new IllnessChange();
                    ((IllnessChange)record).setType(IllnessChange.Type.ILL);
                    ((IllnessChange)record).setStatus(IllnessChange.Status.INVALID);
                    ((IllnessChange)record).setDescription("病情变化描述详情");
                    group.addRecord(record);
                    record = new IllnessChange();
                    ((IllnessChange)record).setType(IllnessChange.Type.SLEEP);
                    ((IllnessChange)record).setStatus(IllnessChange.Status.BETTER);
                    ((IllnessChange)record).setDescription("病情变化描述详情");
                    group.addRecord(record);
                    record = new IllnessChange();
                    ((IllnessChange)record).setType(IllnessChange.Type.FOOD);
                    ((IllnessChange)record).setStatus(IllnessChange.Status.BEST);
                    ((IllnessChange)record).setDescription("病情变化描述详情");
                    group.addRecord(record);
                    record = new IllnessChange();
                    ((IllnessChange)record).setType(IllnessChange.Type.OTHER);
                    ((IllnessChange)record).setDescription("病情变化描述详情");
                    group.addRecord(record);
                    break;
                case FollowUpFormGroup.Type.WEIGHT_RECORD:
                    record = new WeightRecord();
                    ((WeightRecord)record).setWeight(60);
                    group.addRecord(record);
                    break;
                case FollowUpFormGroup.Type.OTHER_CHECK_RECORD:
                    record = new OtherCheckRecord();
                    ((OtherCheckRecord)record).setName("肾功能检查");
                    ((OtherCheckRecord)record).getResult().setText("正常");
                    Pic pic = new Pic(Pic.Source.WEB);
                    pic.setThumbUrl("http://b.hiphotos.baidu.com/image/h%3D200/sign=fd93c2465cb5c9ea7df304e3e539b622/9c16fdfaaf51f3de7d874d2993eef01f3a297942.jpg");
                    ((OtherCheckRecord)record).getResult().addPic(pic);
                    group.addRecord(record);
                    record = new OtherCheckRecord();
                    ((OtherCheckRecord)record).setName("肝功能检查");
                    pic = new Pic(Pic.Source.WEB);
                    pic.setThumbUrl("http://b.hiphotos.baidu.com/image/h%3D200/sign=fd93c2465cb5c9ea7df304e3e539b622/9c16fdfaaf51f3de7d874d2993eef01f3a297942.jpg");
                    ((OtherCheckRecord)record).getResult().addPic(pic);
                    pic = new Pic(Pic.Source.WEB);
                    pic.setThumbUrl("http://b.hiphotos.baidu.com/image/h%3D200/sign=fd93c2465cb5c9ea7df304e3e539b622/9c16fdfaaf51f3de7d874d2993eef01f3a297942.jpg");
                    ((OtherCheckRecord)record).getResult().addPic(pic);
                    group.addRecord(record);
                    break;
                case FollowUpFormGroup.Type.EAT_MED_RECORD:
                    record = new EatMedRecord();
                    ((EatMedRecord)record).setMedName("阿司匹林");
                    ((EatMedRecord)record).setStartTime("2016-03-20");
                    ((EatMedRecord)record).setEndTime("2013-03-21");
                    ((EatMedRecord)record).setSmpleAmount("0.5mg");
                    ((EatMedRecord)record).setRate("1天3次");
                    ((EatMedRecord)record).setEatMethod("口服");
                    group.addRecord(record);
                    record = new EatMedRecord();
                    ((EatMedRecord)record).setMedName("阿司匹林速溶片");
                    ((EatMedRecord)record).setStartTime("2016-03-20");
                    ((EatMedRecord)record).setEndTime("2013-03-21");
                    ((EatMedRecord)record).setSmpleAmount("0.3mg");
                    ((EatMedRecord)record).setRate("1天2次");
                    ((EatMedRecord)record).setEatMethod("口服");
                    group.addRecord(record);
                    record = new BadReactionRecord();
                    ((BadReactionRecord)record).setFirstTime("2016-03-20");
                    ((BadReactionRecord)record).setDurationTime("2小时");
                    ((BadReactionRecord)record).setSymptomsDecription("肚子痛");
                    ((BadReactionRecord)record).setEffect("不想吃饭");
                    group.addRecord(record);
                    break;
                case FollowUpFormGroup.Type.MEASURE_SELF_RECORD:
                    record = new MeasureFormRecord();
                    ((MeasureFormRecord)record).setName("SDS(抑郁自评量表)");
                    ((MeasureFormRecord)record).setScore("3分");
                    ((MeasureFormRecord)record).setResult("正常");
                    ((MeasureFormRecord)record).setRetDescription("自评描述自评描述");
                    group.addRecord(record);
                    record = new MeasureFormRecord();
                    ((MeasureFormRecord)record).setName("LSR(生活满意度自评量表)");
                    ((MeasureFormRecord)record).setScore("3分");
                    ((MeasureFormRecord)record).setResult("正常");
                    ((MeasureFormRecord)record).setRetDescription("自评描述自评描述");
                    group.addRecord(record);
                    break;
                case FollowUpFormGroup.Type.DOC_MEASURE_RECORD:
                    record = new MeasureFormRecord();
                    ((MeasureFormRecord)record).setName("SDS(抑郁医评量表)");
                    ((MeasureFormRecord)record).setScore("3分");
                    ((MeasureFormRecord)record).setResult("正常");
                    ((MeasureFormRecord)record).setRetDescription("医评描述自评描述");
                    group.addRecord(record);
                    record = new MeasureFormRecord();
                    ((MeasureFormRecord)record).setName("LSR(生活满意度医评量表)");
                    ((MeasureFormRecord)record).setScore("3分");
                    ((MeasureFormRecord)record).setResult("正常");
                    ((MeasureFormRecord)record).setRetDescription("医评描述自评描述");
                    group.addRecord(record);
                    break;
            }
            formList.add(group);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.bDoctorAdvice:
                break;
        }
    }
}
