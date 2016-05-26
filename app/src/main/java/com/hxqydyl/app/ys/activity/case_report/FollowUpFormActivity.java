package com.hxqydyl.app.ys.activity.case_report;

import android.content.Context;
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
import com.hxqydyl.app.ys.bean.follow.VisitRecordList;
import com.hxqydyl.app.ys.bean.follow.VisitRecordResponse;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;
import com.hxqydyl.app.ys.bean.followupform.OtherCheckRecord;
import com.hxqydyl.app.ys.bean.followupform.WeightRecord;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.xus.http.httplib.model.GetParams;

import java.util.ArrayList;

/**
 * xxx的随访表单
 * Created by white_ash on 2016/3/23.
 */
public class FollowUpFormActivity extends BaseRequstActivity implements View.OnClickListener, FollowUpFormAdapter.SeeHistoryButtonListener, ExpandableListView.OnGroupExpandListener {
    @InjectId(id = R.id.lvForm)
    private ExpandableListView lvForm;
    @InjectId(id = R.id.bModifyAdvice)
    private Button bModifyAdvice;
    @InjectId(id = R.id.bKeepAdvice)
    private Button bKeepAdvice;
    private PatientSimpleInfoViewHolder simpleInfoViewHolder;
    private ArrayList<FollowUpFormGroup> formList = new ArrayList<FollowUpFormGroup>();
    private FollowUpFormAdapter followUpFormAdapter;
    private int curExpandGroup = -1;
    private PatientTreatInfo treatInfo;
    private Patient patient;

    public static void newIntent(Context mContext,PatientTreatInfo pti, Patient p){
        Intent intent = new Intent(mContext, FollowUpFormActivity.class);
        intent.putExtra(PatientDetailsActivity.KEY_TREAT_INFO, pti);
        intent.putExtra(PatientDetailsActivity.KEY_PATIENT, p);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_form);
        InjectUtils.injectView(this);
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
        }
    }

    private void initView() {
        simpleInfoViewHolder = new PatientSimpleInfoViewHolder(this);
        initViewOnBaseTitle(String.format(getString(R.string.follow_up_form_title), patient.getRealName()));
        followUpFormAdapter = new FollowUpFormAdapter(this, formList, this);
        lvForm.setAdapter(followUpFormAdapter);
        lvForm.setGroupIndicator(null);
    }

    private void initListener() {
        lvForm.setOnGroupExpandListener(this);
        bModifyAdvice.setOnClickListener(this);
        bKeepAdvice.setOnClickListener(this);
        setBackListener(this);
    }

    private void getFollowUpFormDetails() {
        toNomalNet(new GetParams(), VisitRecordResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_FOLLOW_UP_FORM_DETAILS, "2.0", treatInfo.getId()), "正在获取获取随访表单详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
//            case R.id.bDoctorAdvice:
//                Intent intent = new Intent(this, PatientAdviceInfoActivity.class);
//                intent.putExtra("customerUuid", patient.getCustomerUuid());
//                startActivity(intent);
//                break;
            case R.id.bKeepAdvice:

                break;
            case R.id.bModifyAdvice:

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
        VisitRecordList visitRecordList = (VisitRecordList) bean;
        ArrayList<FollowUpFormGroup> list = toformList(visitRecordList);
        if (list != null && list.size() > 0) {
            formList.clear();
            formList.addAll(list);
            followUpFormAdapter.notifyDataSetChanged();
        } else {
            UIHelper.ToastMessage(this, "该表单暂无数据");
        }
    }

    //将数据转换成界面处理的list
    public ArrayList<FollowUpFormGroup> toformList(VisitRecordList followUpFormResponse) {

        ArrayList<FollowUpFormGroup> formList = new ArrayList<>();
        if (followUpFormResponse == null) {
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
        if (followUpFormResponse.visitRecord.getIllnessRecord() != null) {
            VisitRecord.IllnessRecord record = followUpFormResponse.visitRecord.getIllnessRecord();
            IllnessChange change = new IllnessChange();
            change.setType(IllnessChange.Type.ILL);
            change.setDescription(record.getNewCondition());
            getStatus(change, record.getNewCondition());
            bqbh.addRecord(change);
        }
        //进食情况
        if (followUpFormResponse.visitRecord.getEat() != null) {
            VisitRecord.SleepOrEat eat = followUpFormResponse.visitRecord.getEat();
            IllnessChange change = new IllnessChange();

            getStatus(change, eat.getState());
            change.setDescription(eat.getResult());
            change.setType(IllnessChange.Type.FOOD);
            bqbh.addRecord(change);
        }
        //其他情况
        if (followUpFormResponse.visitRecord.getOther() != null) {
            VisitRecord.Other other = followUpFormResponse.visitRecord.getOther();
            IllnessChange change = new IllnessChange();
            change.setDescription(other.getResult());
            change.setType(IllnessChange.Type.OTHER);
            bqbh.addRecord(change);
        }
        if (bqbh.getRecords().size() > 0)
            formList.add(bqbh);
        //体重记录
        if (followUpFormResponse.visitRecord.getWeight() != null) {
            VisitRecord.Weight weight = followUpFormResponse.visitRecord.getWeight();
            WeightRecord change = new WeightRecord();
            change.setWeight(weight.getResult());
            tzjl.addRecord(change);
        }
        if (tzjl.getRecords().size() > 0)
            formList.add(tzjl);
        //检查结果
        if (followUpFormResponse.visitRecord.getCheckResult() != null && followUpFormResponse.visitRecord.getCheckResult().size() > 0) {
            for (CheckResult result : followUpFormResponse.visitRecord.getCheckResult()) {
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
        if (followUpFormResponse.visitRecord.getDoctorAdvice() != null && followUpFormResponse.visitRecord.getDoctorAdvice().size() > 0) {
            for (EatMedRecord record : followUpFormResponse.visitRecord.getDoctorAdvice()) {
                fyjl.addRecord(record);
            }
        }
        //不良反应
        if (followUpFormResponse.visitRecord.getDrugReaction() != null) {
            fyjl.addRecord(followUpFormResponse.visitRecord.getDrugReaction());
        }
        if (fyjl.getRecords().size() > 0)
            formList.add(fyjl);
        //自评量表
        if (followUpFormResponse.selfList != null && followUpFormResponse.selfList.size() > 0) {
            for (MeasureFormRecord record : followUpFormResponse.selfList) {
                zplb.addRecord(record);
            }
        }
        if (zplb.getRecords().size() > 0)
            formList.add(zplb);
        //医评量表
        if (followUpFormResponse.doctorList != null && followUpFormResponse.doctorList.size() > 0) {
            for (MeasureFormRecord record : followUpFormResponse.doctorList) {
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
