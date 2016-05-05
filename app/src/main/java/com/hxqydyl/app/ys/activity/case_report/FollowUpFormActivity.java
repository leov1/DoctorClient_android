package com.hxqydyl.app.ys.activity.case_report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.patient.PatientAdviceInfoActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.activity.patient.PatientSimpleInfoViewHolder;
import com.hxqydyl.app.ys.adapter.FollowUpFormAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.bean.follow.CheckResult;
import com.hxqydyl.app.ys.bean.follow.VisitRecord;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;
import com.hxqydyl.app.ys.bean.followupform.OtherCheckRecord;
import com.hxqydyl.app.ys.bean.followupform.WeightRecord;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.FollowUpFormResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.xus.http.httplib.model.GetParams;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/23.
 */
public class FollowUpFormActivity extends BaseRequstActivity implements View.OnClickListener, FollowUpFormAdapter.SeeHistoryButtonListener, ExpandableListView.OnGroupExpandListener {
    private ExpandableListView lvForm;
    private Button bDoctorAdvice;
    private PatientSimpleInfoViewHolder simpleInfoViewHolder;
    private ArrayList<FollowUpFormGroup> formList = new ArrayList<FollowUpFormGroup>();
    private FollowUpFormAdapter followUpFormAdapter;
    private int curExpandGroup = -1;
    private PatientTreatInfo treatInfo;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_form);
        init();
        initView();
        initListener();
        getFollowUpFormDetails();
    }

    private void init() {
        treatInfo = (PatientTreatInfo) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_TREAT_INFO);
        patient = (Patient) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_PATIENT);
        if (treatInfo == null || patient == null) {
            finish();
            return;
        }
    }

    private void initView() {
        lvForm = (ExpandableListView) findViewById(R.id.lvForm);
        bDoctorAdvice = (Button) findViewById(R.id.bDoctorAdvice);
        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        initViewOnBaseTitle(String.format(getString(R.string.follow_up_form_title), patient.getCustomerName()));
        followUpFormAdapter = new FollowUpFormAdapter(this, formList, this);
        lvForm.setAdapter(followUpFormAdapter);
        lvForm.setGroupIndicator(null);
    }

    private void initListener() {
        lvForm.setOnGroupExpandListener(this);
        bDoctorAdvice.setOnClickListener(this);
        setBackListener(this);
    }

    private void getFollowUpFormDetails() {
        toNomalNet(new GetParams(), 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_FOLLOW_UP_FORM_DETAILS, "1.0", treatInfo.getId()), "正在获取获取随访表单详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.bDoctorAdvice:
                Intent intent = new Intent(this, PatientAdviceInfoActivity.class);
                intent.putExtra("customerUuid", patient.getCustomerUuid());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onButtonClick() {
        Intent intent = new Intent(this, IllnessChangeRecordActivity.class);
        intent.putExtra(PatientDetailsActivity.KEY_PATIENT, patient);
        startActivity(intent);
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        BaseResponse<VisitRecord> FollowUpFormResponse = (  BaseResponse<VisitRecord>) bean;
        ArrayList<FollowUpFormGroup> list = toformList(FollowUpFormResponse);
        if (list != null && list.size() > 0) {
            formList.clear();
            formList.addAll(list);
            followUpFormAdapter.notifyDataSetChanged();
        }else{
            UIHelper.ToastMessage(this,"该表单暂无数据");
        }
    }

    //将数据转换成界面处理的list
    public ArrayList<FollowUpFormGroup> toformList(BaseResponse<VisitRecord> followUpFormResponse) {

        ArrayList<FollowUpFormGroup> formList = new ArrayList<>();
        if (followUpFormResponse.value == null) {
            return formList;
        }
        FollowUpFormGroup bqbh = new FollowUpFormGroup();
        bqbh.setFormGroupType(FollowUpFormGroup.Type.ILLNESS_CHANGE);
        FollowUpFormGroup tzjl = new FollowUpFormGroup();
        tzjl.setFormGroupType(FollowUpFormGroup.Type.WEIGHT_RECORD);
        FollowUpFormGroup jcjg = new FollowUpFormGroup();
        jcjg.setFormGroupType(FollowUpFormGroup.Type.OTHER_CHECK_RECORD);
        FollowUpFormGroup fyjl = new FollowUpFormGroup();
        fyjl.setFormGroupType(FollowUpFormGroup.Type.EAT_MED_RECORD);
        FollowUpFormGroup zplb = new FollowUpFormGroup();
        zplb.setFormGroupType(FollowUpFormGroup.Type.MEASURE_SELF_RECORD);
        FollowUpFormGroup yplb = new FollowUpFormGroup();
        yplb.setFormGroupType(FollowUpFormGroup.Type.DOC_MEASURE_RECORD);
        //病情变化中的病情变化
        if (followUpFormResponse.value.getIllnessRecord() != null) {
            VisitRecord.IllnessRecord record = followUpFormResponse.value.getIllnessRecord();
            IllnessChange change = new IllnessChange();
            change.setType(IllnessChange.Type.ILL);
            change.setDescription(record.getNewCondition());
            getStatus(change, record.getNewCondition());
            bqbh.addRecord(change);
        }
        //进食情况
        if (followUpFormResponse.value.getEat() != null) {
            VisitRecord.SleepOrEat eat = followUpFormResponse.value.getEat();
            IllnessChange change = new IllnessChange();

            getStatus(change, eat.getState());
            change.setDescription(eat.getResult());
            change.setType(IllnessChange.Type.FOOD);
            bqbh.addRecord(change);
        }
        //其他情况
        if (followUpFormResponse.value.getOther() != null) {
            VisitRecord.Other other = followUpFormResponse.value.getOther();
            IllnessChange change = new IllnessChange();
            change.setDescription(other.getResult());
            change.setType(IllnessChange.Type.OTHER);
            bqbh.addRecord(change);
        }
        if (bqbh.getRecords().size() > 0)
            formList.add(bqbh);
        //体重记录
        if (followUpFormResponse.value.getWeight() != null) {
            VisitRecord.Weight weight = followUpFormResponse.value.getWeight();
            WeightRecord change = new WeightRecord();
            change.setWeight(weight.getResult());
            tzjl.addRecord(change);
        }
        if (tzjl.getRecords().size() > 0)
            formList.add(tzjl);
        //检查结果
        if (followUpFormResponse.value.getCheckResult() != null && followUpFormResponse.value.getCheckResult().size() > 0) {
            for (CheckResult result : followUpFormResponse.value.getCheckResult()) {
                OtherCheckRecord record = new OtherCheckRecord();
                record.setId(result.getUuid());
                record.setName(result.getName());
                record.getResult().setText(result.getResult());
                if (result.getImgs() != null && result.getImgs().size() > 0) {
                    for (String s : result.getImgs()) {
                        Pic pic = new Pic();
                        pic.setThumbUrl(s);
                        pic.setUrl(s);
                        record.getResult().addPic(pic);
                    }
                }
                jcjg.addRecord(record);
            }
        }
        if (jcjg.getRecords().size() > 0)
            formList.add(jcjg);
        //服药记录
        if (followUpFormResponse.value.getDoctorAdvice() != null && followUpFormResponse.value.getDoctorAdvice().size() > 0) {
            for (EatMedRecord record : followUpFormResponse.value.getDoctorAdvice()) {
                fyjl.addRecord(record);
            }
        }
        //不良反应
        if (followUpFormResponse.value.getDrugReaction() != null) {
            fyjl.addRecord(followUpFormResponse.value.getDrugReaction());
        }
        if (fyjl.getRecords().size() > 0)
            formList.add(fyjl);
        //自评量表
        if (followUpFormResponse.query.selfList != null && followUpFormResponse.query.selfList.size() > 0) {
            for (MeasureFormRecord record : followUpFormResponse.query.selfList) {
                zplb.addRecord(record);
            }
        }
        if (zplb.getRecords().size() > 0)
            formList.add(zplb);
        //医评量表
        if (followUpFormResponse.query.doctorList != null && followUpFormResponse.query.doctorList.size() > 0) {
            for (MeasureFormRecord record : followUpFormResponse.query.doctorList) {
                yplb.addRecord(record);
            }
        }
        if (yplb.getRecords().size() > 0)
            formList.add(yplb);
        return formList;
    }

    //数据处理
    public void getStatus(IllnessChange change, String s) {
        if ("无效".equals(s)) {
            change.setStatus(IllnessChange.Status.INVALID);
        } else if ("好转".equals(s)) {
            change.setStatus(IllnessChange.Status.BETTER);
        } else if ("痊愈".equals(s)) {
            change.setStatus(IllnessChange.Status.BEST);
        } else {
            change.setStatus(IllnessChange.Status.INVALID);
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        if (curExpandGroup >= 0 && curExpandGroup != groupPosition) {
            lvForm.collapseGroup(curExpandGroup);
        }
        curExpandGroup = groupPosition;
    }
}
