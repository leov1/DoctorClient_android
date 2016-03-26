package com.hxqydyl.app.ys.activity.case_report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.CaseHistoryAdapter;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.utils.GetPicUtils;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.Utils;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/23.
 */
public class AddCaseReportActivity extends BaseTitleActivity implements View.OnClickListener {
    public static final int CASE_REPORT_TYPE_OUT_PATIENT = 1;
    public static final int CASE_REPORT_TYPE_IN_PATIENT = 2;

//    患者姓名
    @InjectId(id = R.id.tvPatientName)
    private TextView tvPatientName;
//    门诊或者住院类型选择
    @InjectId(id = R.id.tvOutPatient)
    private TextView tvOutPatient;
    @InjectId(id = R.id.tvInPatient)
    private TextView tvInPatient;
//    门诊就诊时间
    @InjectId(id = R.id.treatmentTimeRow)
    private TableRow treatmentTimeRow;
    @InjectId(id = R.id.etTreatmentTime)
    private EditText etTreatmentTime;
//    入出院时间
    @InjectId(id = R.id.beInHospitalTimeRow)
    private TableRow beInHospitalTimeRow;
    @InjectId(id = R.id.etBeInHospitalTime)
    private EditText etBeInHospitalTime;
    @InjectId(id = R.id.outHospitalTimeRow)
    private TableRow outHospitalTimeRow;
    @InjectId(id = R.id.etOutHospitalTime)
    private EditText etOutHospitalTime;
    @InjectId(id = R.id.outHospitalTimeDividerRow)
    private TableRow outHospitalTimeDividerRow;
//    医院
    @InjectId(id = R.id.etTreatmentHospital)
    private EditText etTreatmentHospital;
//    医生
    @InjectId(id = R.id.etDoctor)
    private EditText etDoctor;
//    病历照片
    @InjectId(id = R.id.gvPatientCaseHistory)
    private GridView gvPatientCaseHistory;
    private CaseHistoryAdapter caseHistoryAdapter;
    private ArrayList<Pic> picList = new ArrayList<Pic>();
//    保存
    @InjectId(id = R.id.bSaveCaseReport)
    private Button bSaveCaseReport;

    private int currentCaseType = CASE_REPORT_TYPE_OUT_PATIENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case_report);

        initViewOnBaseTitle(getString(R.string.add_case_report_title));
        setBackListener(this);

        InjectUtils.injectView(this);
        setCaseReportType(currentCaseType);
        tvOutPatient.setOnClickListener(this);
        tvInPatient.setOnClickListener(this);

        Pic addPic = new Pic(Pic.Source.DRAWABLE);
        addPic.setThumbUrl(R.mipmap.icon_add_case_history + "");
        picList.add(addPic);
        caseHistoryAdapter = new CaseHistoryAdapter(this,picList);
        gvPatientCaseHistory.setAdapter(caseHistoryAdapter);
        gvPatientCaseHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pic pic = (Pic) parent.getItemAtPosition(position);
                if(pic != null){
                    if (("" + R.mipmap.icon_add_case_history).equals(pic.getThumbUrl())) {
                        showGetPhotoDialog();
                    }else{
                        // 跳转到查看大图
                    }
                }
            }
        });
    }

    private GetPicUtils getPicUtils;

    private void showGetPhotoDialog() {
        if (Utils.isExitsSdcard()) {
            getPicUtils = new GetPicUtils(this);
            getPicUtils.getPic();
        } else {
            Toast.makeText(this,getString(R.string.sdcard_not_exist),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPicUtils.onActivityResult(requestCode,resultCode,data);
        Pic pic = new Pic(Pic.Source.SD);
        pic.setUrl(getPicUtils.getPicPath());
        pic.setThumbUrl(getPicUtils.getPicThumbPath());
        if(picList.size() < 5){
            picList.add(picList.size(),pic);
        }else{
            picList.remove(4);
            picList.add(pic);
        }
        caseHistoryAdapter.notifyDataSetChanged();
    }

    private void setCaseReportType(int caseType) {
        currentCaseType = caseType;
        switch(currentCaseType){
            case CASE_REPORT_TYPE_OUT_PATIENT:
                tvOutPatient.setBackgroundResource(R.drawable.shape_treatment_type_bg_selected);
                tvOutPatient.setTextColor(Color.WHITE);
                tvInPatient.setBackgroundResource(R.drawable.shape_treatment_type_bg_normal);
                tvInPatient.setTextColor(getResources().getColor(R.color.color_4b4b4b));
                treatmentTimeRow.setVisibility(View.VISIBLE);
                beInHospitalTimeRow.setVisibility(View.GONE);
                outHospitalTimeRow.setVisibility(View.GONE);
                outHospitalTimeDividerRow.setVisibility(View.GONE);
                break;
            case CASE_REPORT_TYPE_IN_PATIENT:
                tvOutPatient.setBackgroundResource(R.drawable.shape_treatment_type_bg_normal);
                tvOutPatient.setTextColor(getResources().getColor(R.color.color_4b4b4b));
                tvInPatient.setBackgroundResource(R.drawable.shape_treatment_type_bg_selected);
                tvInPatient.setTextColor(Color.WHITE);
                treatmentTimeRow.setVisibility(View.GONE);
                beInHospitalTimeRow.setVisibility(View.VISIBLE);
                outHospitalTimeRow.setVisibility(View.VISIBLE);
                outHospitalTimeDividerRow.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.tvOutPatient:
                setCaseReportType(CASE_REPORT_TYPE_OUT_PATIENT);
                break;
            case R.id.tvInPatient:
                setCaseReportType(CASE_REPORT_TYPE_IN_PATIENT);
                break;
            case R.id.bSaveCaseReport:
                break;
        }
    }
}
