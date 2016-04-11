package com.hxqydyl.app.ys.activity.case_report;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientDetailsActivity;
import com.hxqydyl.app.ys.adapter.CaseHistoryAdapter;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.http.CaseReportNet;
import com.hxqydyl.app.ys.http.UploadFileNet;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.utils.GetPicUtils;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by white_ash on 2016/3/23.
 */
public class AddCaseReportActivity extends BaseTitleActivity implements View.OnClickListener, CaseHistoryAdapter.DeleteListener {
    public static final int CASE_REPORT_TYPE_OUT_PATIENT = 1;
    public static final int CASE_REPORT_TYPE_IN_PATIENT = 2;
    private static final int MAX_PIC_NUM = 5;

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
    @InjectId(id = R.id.tvTreatmentTime)
    private TextView tvTreatmentTime;
    //    入出院时间
    @InjectId(id = R.id.beInHospitalTimeRow)
    private TableRow beInHospitalTimeRow;
    @InjectId(id = R.id.tvBeInHospitalTime)
    private TextView tvBeInHospitalTime;
    @InjectId(id = R.id.outHospitalTimeRow)
    private TableRow outHospitalTimeRow;
    @InjectId(id = R.id.tvOutHospitalTime)
    private TextView tvOutHospitalTime;
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

    private UploadFileNet uploadFileNet;
    private CaseReportNet caseReportNet;

    private int currentCaseType = CASE_REPORT_TYPE_OUT_PATIENT;

    private Patient patient;

    private Calendar outpatientCalendar;
    private Calendar inHospitalCalendar;
    private Calendar outHospitalCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        patient = (Patient) getIntent().getSerializableExtra(PatientDetailsActivity.KEY_PATIENT);

        setContentView(R.layout.activity_add_case_report);


        initViewOnBaseTitle(getString(R.string.add_case_report_title));
        setBackListener(this);

        InjectUtils.injectView(this);

        tvPatientName.setText(patient.getName());

        setCaseReportType(currentCaseType);
        tvOutPatient.setOnClickListener(this);
        tvInPatient.setOnClickListener(this);
        bSaveCaseReport.setOnClickListener(this);

        Pic addPic = new Pic(Pic.Source.DRAWABLE);
        addPic.setThumbUrl(R.mipmap.icon_add_case_history + "");
        picList.add(addPic);
        caseHistoryAdapter = new CaseHistoryAdapter(this, picList, this);
        gvPatientCaseHistory.setAdapter(caseHistoryAdapter);
        gvPatientCaseHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pic pic = (Pic) parent.getItemAtPosition(position);
                if (pic != null) {
                    if (isAddPicButton(pic)) {
                        showGetPhotoDialog();
                    } else {
                        // 跳转到查看大图
                    }
                }
            }
        });
        uploadFileNet = new UploadFileNet(this);
        caseReportNet = new CaseReportNet(this);
        tvTreatmentTime.setOnClickListener(onClickListener);
        tvBeInHospitalTime.setOnClickListener(onClickListener);
        tvOutHospitalTime.setOnClickListener(onClickListener);
    }

    private boolean isAddPicButton(Pic pic){
        if (("" + R.mipmap.icon_add_case_history).equals(pic.getThumbUrl())){
            return true;
        }
        return false;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvTreatmentTime:
                    showDatePickerDialog(outpatientCalendar, Calendar.getInstance(), null, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (outpatientCalendar == null) {
                                outpatientCalendar = Calendar.getInstance();
                            }
                            outpatientCalendar.set(year, monthOfYear, dayOfMonth);
                            formatAndDisplay(outpatientCalendar, tvTreatmentTime);
                        }
                    });
                    break;
                case R.id.tvBeInHospitalTime:
                    showDatePickerDialog(inHospitalCalendar, /*utHospitalCalendar*/Calendar.getInstance(), null, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (inHospitalCalendar == null) {
                                inHospitalCalendar = Calendar.getInstance();
                            }
                            inHospitalCalendar.set(year, monthOfYear, dayOfMonth);
                            formatAndDisplay(inHospitalCalendar, tvBeInHospitalTime);
                        }
                    });
                    break;
                case R.id.tvOutHospitalTime:
                    showDatePickerDialog(outHospitalCalendar, Calendar.getInstance(), inHospitalCalendar, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (outHospitalCalendar == null) {
                                outHospitalCalendar = Calendar.getInstance();
                            }
                            outHospitalCalendar.set(year, monthOfYear, dayOfMonth);
                            formatAndDisplay(outHospitalCalendar, tvOutHospitalTime);
                        }
                    });
                    break;
            }
        }
    };

    private void formatAndDisplay(Calendar calendar, TextView tv) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tv.setText(format.format(calendar.getTime()));
    }

    private void showDatePickerDialog(Calendar cur, Calendar max, Calendar min, final DatePickerDialog.OnDateSetListener onDateSetListener) {
        boolean flag = true;
        if (cur == null) {
            flag = false;
            cur = Calendar.getInstance();
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (onDateSetListener != null) {
                    onDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
                }
            }
        }, cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH));
//        if(min!=null){
//            dialog.getDatePicker().setMinDate(min.getTimeInMillis());
//        }
        if (max != null) {
            dialog.getDatePicker().setMaxDate(max.getTimeInMillis());
        }
        dialog.show();
    }

    private GetPicUtils getPicUtils;

    private void showGetPhotoDialog() {
        if (Utils.isExitsSdcard()) {
            getPicUtils = new GetPicUtils(this);
            getPicUtils.getPic();
        } else {
            Toast.makeText(this, getString(R.string.sdcard_not_exist), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPicUtils.onActivityResult(requestCode, resultCode, data);
        Pic pic = new Pic(Pic.Source.SD);
        pic.setCanDel(true);
        pic.setUrl(getPicUtils.getPicPath());
        pic.setThumbUrl(getPicUtils.getPicThumbPath());
        int length = picList.size();
        if (length < MAX_PIC_NUM) {
            picList.add(length - 1, pic);
        } else {
            picList.remove(MAX_PIC_NUM - 1);
            picList.add(pic);
        }
        caseHistoryAdapter.notifyDataSetChanged();
    }

    private void setCaseReportType(int caseType) {
        currentCaseType = caseType;
        switch (currentCaseType) {
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
        switch (v.getId()) {
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
                int length = picList.size();
                if (picList.get(length - 1).getSource() == Pic.Source.DRAWABLE) {
                    length = length - 1;
                }
                if (length > 0) {
                    File[] files = new File[length];
                    for (int i = 0; i < length; i++) {
                        files[i] = new File(picList.get(i).getUrl());
                    }
                    uploadFileNet.uploadPic(files);
                } else {
                    caseReportNet.addCaseReportForPatient(patient.getId(), LoginManager.getDoctorUuid(), // 患者id，医生id
                            null, "1", // 医院id，病历类型
                            tvTreatmentTime.getText().toString(), null, null,  //就诊时间，入院时间，出院时间
                            null); // 照片id
                }
                break;
        }
    }

    @Override
    public void onResponse(String url, Object result) {
        super.onResponse(url, result);
        if (url.endsWith(UrlConstants.UPLOAD_PIC)) {
            ArrayList<Pic> pics = (ArrayList<Pic>) result;
            String[] picsId = new String[pics.size()];
            for (int i = 0; i < pics.size(); i++) {
                picsId[i] = pics.get(i).getId();
            }
            if (currentCaseType == CASE_REPORT_TYPE_IN_PATIENT) {
                //住院
                caseReportNet.addCaseReportForPatient(patient.getId(), LoginManager.getDoctorUuid(),
                        etTreatmentHospital.getText().toString(), "0",// 医院id，病历类型
                        null, tvBeInHospitalTime.getText().toString(), tvOutHospitalTime.getText().toString(), //就诊时间，入院时间，出院时间
                        picsId);// 照片id
            } else if (currentCaseType == CASE_REPORT_TYPE_OUT_PATIENT) {
//                门诊
                caseReportNet.addCaseReportForPatient(patient.getId(), LoginManager.getDoctorUuid(), // 患者id，医生id
                        etTreatmentHospital.getText().toString(), "1", // 医院id，病历类型
                        tvTreatmentTime.getText().toString(), null, null,  //就诊时间，入院时间，出院时间
                        picsId); // 照片id
            }
        } else if (url.endsWith(UrlConstants.ADD_CASE_REPORT_FOR_PATIENT)) {
            Boolean ret = (Boolean) result;
            if (ret) {
                Toast.makeText(this, "添加病历成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "添加病历失败，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDeleteClick(int position) {
        picList.remove(position);
        int length = picList.size();
        if(!isAddPicButton(picList.get(length-1))){
            Pic addPic = new Pic(Pic.Source.DRAWABLE);
            addPic.setThumbUrl(R.mipmap.icon_add_case_history + "");
            picList.add(addPic);
        }
        caseHistoryAdapter.notifyDataSetChanged();
    }
}
