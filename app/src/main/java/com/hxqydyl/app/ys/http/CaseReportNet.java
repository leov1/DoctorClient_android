package com.hxqydyl.app.ys.http;

import android.text.TextUtils;

import com.hxqydyl.app.ys.bean.CaseReport;
import com.hxqydyl.app.ys.bean.FollowUpForm;
import com.hxqydyl.app.ys.bean.PatientTreatInfo;
import com.hxqydyl.app.ys.bean.Pic;
import com.hxqydyl.app.ys.bean.followupform.BadReactionRecord;
import com.hxqydyl.app.ys.bean.followupform.EatMedRecord;
import com.hxqydyl.app.ys.bean.followupform.FollowUpFormGroup;
import com.hxqydyl.app.ys.bean.followupform.IllnessChange;
import com.hxqydyl.app.ys.bean.followupform.IllnessChangeRecord;
import com.hxqydyl.app.ys.bean.followupform.MeasureFormRecord;
import com.hxqydyl.app.ys.bean.followupform.OtherCheckRecord;
import com.hxqydyl.app.ys.bean.followupform.WeightRecord;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by white_ash on 2016/4/4.
 */
public class CaseReportNet extends BaseNet {
    public CaseReportNet(NetRequestListener listener) {
        super(listener);
    }

    /**
     * 获取患者病情变化的历史记录
     *
     * @param doctorId  医生id
     * @param patientId 患者id
     */
    public void getIllnessChangeRecordHistory(final String doctorId, final String patientId) {
        final String shortUrl = UrlConstants.GET_ILLNESS_CHANGE_HISTORY_LIST;
        String version = "1.0";
        OkHttpUtils
                .post()
                .addHeader("Accept", "application/json")
                .addParams("serviceStaffUuid", doctorId)
                .addParams("customerUuid", patientId)
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .build()
                .execute(new Callback<ArrayList<IllnessChangeRecord>>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public ArrayList<IllnessChangeRecord> parseNetworkResponse(Response response) throws Exception {
                        ArrayList<IllnessChangeRecord> records = new ArrayList<IllnessChangeRecord>();
                        JSONArray array = new JSONArray(response.body().string());
                        if (array != null && array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                records.add(jsonToILlnessChangeRecord(array.getJSONObject(i)));
                            }
                        }
                        return records;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(ArrayList<IllnessChangeRecord> response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    private IllnessChangeRecord jsonToILlnessChangeRecord(JSONObject jsonObject) throws Exception {
        IllnessChangeRecord record = new IllnessChangeRecord();
        record.setId(jsonObject.optString("uuid"));
        record.setStatus(jsonObject.optString("previons"));
        record.setTime(jsonObject.optString("createTime"));
        record.setDescription(jsonObject.optString("newCondition"));
        return record;
    }

    /**
     * 获取病情变化记录详情
     * 由于病情变化记录详情的构成是不定长的变化list（包括睡眠变化，饮食变化等），所以这里的数据格式解析为了一个list。
     * 这个list代表的是一条病情变化记录的详情。
     *
     * @param illnessChangeId
     */
    public void getIllnessChangeDetails(final String illnessChangeId) {
        final String shortUrl = UrlConstants.GET_ILLNESS_CHANGE_DETAILS;
        String version = "1.0";
        OkHttpUtils
                .get()
                .addHeader("Accept", "application/json")
                .url(UrlConstants.getWholeApiUrl(shortUrl, version, illnessChangeId))
                .build()
                .execute(new Callback<ArrayList<IllnessChange>>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public ArrayList<IllnessChange> parseNetworkResponse(Response response) throws Exception {
                        JSONObject obj = new JSONObject(response.body().string());
                        return getChangesFromJson(obj);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(ArrayList<IllnessChange> response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    private ArrayList<IllnessChange> getChangesFromJson(JSONObject obj) throws Exception {
        ArrayList<IllnessChange> changes = new ArrayList<IllnessChange>();
        if (obj != null) {
            JSONObject json = null;
            IllnessChange change = null;
            if (obj.has("illnessRecord")) {
                json = obj.optJSONObject("illnessRecord");
                if (json != null) {
                    change = new IllnessChange();
                    change.setType(IllnessChange.Type.ILL);
                    change.setDescription(json.optString("newCondition"));
                    String state = json.optString("previons");
                    if ("无效".equals(state)) {
                        change.setStatus(IllnessChange.Status.INVALID);
                    } else if ("好转".equals(state)) {
                        change.setStatus(IllnessChange.Status.BETTER);
                    } else if ("痊愈".equals(state)) {
                        change.setStatus(IllnessChange.Status.BEST);
                    } else {
                        change.setStatus(IllnessChange.Status.INVALID);
                    }
                    changes.add(change);
                }
            }
            if (obj.has("sleep")) {
                json = obj.optJSONObject("sleep");
                if (json != null) {
                    change = jsonToIllnessChange(json);
                    change.setType(IllnessChange.Type.SLEEP);
                    changes.add(change);
                }
            }
            if (obj.has("eat")) {
                json = obj.optJSONObject("eat");
                if (json != null) {
                    change = jsonToIllnessChange(json);
                    change.setType(IllnessChange.Type.FOOD);
                    changes.add(change);
                }
            }
            if (obj.has("other")) {
                json = obj.optJSONObject("other");
                if (json != null) {
                    change = jsonToIllnessChange(json);
                    change.setType(IllnessChange.Type.OTHER);
                    changes.add(change);
                }
            }
        }
        return changes;
    }

    /**
     * 获取随访表单详情
     *
     * @param visitRecordUuid 随访表单id
     */
    public void getFollowUpFormDetails(final String visitRecordUuid) {
        final String shortUrl = UrlConstants.GET_FOLLOW_UP_FORM_DETAILS;
        String version = "1.0";
        OkHttpUtils
                .get()
                .addHeader("Accept", "application/json")
                .url(UrlConstants.getWholeApiUrl(shortUrl, version, visitRecordUuid))
                .build()
                .execute(new Callback<FollowUpForm>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public FollowUpForm parseNetworkResponse(Response response) throws Exception {
                        JSONObject obj = new JSONObject(response.body().string());
                        return jsonToFormData(obj);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(FollowUpForm response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    /**
     * json 对象转化为随访表单对象
     *
     * @param totalObj
     * @return
     * @throws Exception
     */
    private FollowUpForm jsonToFormData(JSONObject totalObj) throws Exception {
        FollowUpForm formData = new FollowUpForm();
        formData.setId(totalObj.optString("uuid"));
        JSONObject obj = totalObj.optJSONObject("visitRecord");
        FollowUpFormGroup oneGroupData = null;
        if (obj != null) {
            if (obj.has("illnessRecord") || obj.has("sleep") || obj.has("eat") || obj.has("other")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.ILLNESS_CHANGE);
                IllnessChange change = null;
                JSONObject json = null;
                if (obj.has("illnessRecord")) {
                    json = obj.optJSONObject("illnessRecord");
                    if (json != null) {
                        change = new IllnessChange();
                        change.setType(IllnessChange.Type.ILL);
                        change.setDescription(json.optString("newCondition"));
                        String state = json.optString("previons");
                        if ("无效".equals(state)) {
                            change.setStatus(IllnessChange.Status.INVALID);
                        } else if ("好转".equals(state)) {
                            change.setStatus(IllnessChange.Status.BETTER);
                        } else if ("痊愈".equals(state)) {
                            change.setStatus(IllnessChange.Status.BEST);
                        } else {
                            change.setStatus(IllnessChange.Status.INVALID);
                        }
                        oneGroupData.addRecord(change);
                    }
                }
                if (obj.has("sleep")) {
                    json = obj.optJSONObject("sleep");
                    if (json != null) {
                        change = jsonToIllnessChange(json);
                        change.setType(IllnessChange.Type.SLEEP);
                        oneGroupData.addRecord(change);
                    }
                }
                if (obj.has("eat")) {
                    json = obj.optJSONObject("eat");
                    if (json != null) {
                        change = jsonToIllnessChange(json);
                        change.setType(IllnessChange.Type.FOOD);
                        oneGroupData.addRecord(change);
                    }
                }
                if (obj.has("other")) {
                    json = obj.optJSONObject("other");
                    if (json != null) {
                        change = jsonToIllnessChange(json);
                        change.setType(IllnessChange.Type.OTHER);
                        oneGroupData.addRecord(change);
                    }
                }
                if (oneGroupData.getRecords().size() > 0) {
                    change = new IllnessChange();
                    change.setType(IllnessChange.Type.SEE_HISTORY_BUTTON);
                    oneGroupData.addRecord(change);
                }
                formData.addRecordGroup(oneGroupData);
            }
            if (obj.has("weight")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.WEIGHT_RECORD);
                JSONObject json = obj.optJSONObject("weight");
                if (json != null) {
                    WeightRecord weightRecord = new WeightRecord();
                    weightRecord.setWeight(json.optString("result"));
                    oneGroupData.addRecord(weightRecord);
                }
                formData.addRecordGroup(oneGroupData);
            }
            if (obj.has("checkResult")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.OTHER_CHECK_RECORD);
                JSONArray array = obj.optJSONArray("checkResult");
                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        OtherCheckRecord otherCheckRecord = jsonToOtherCheckRecord(json);
                        oneGroupData.addRecord(otherCheckRecord);
                    }
                }
                formData.addRecordGroup(oneGroupData);
            }
            if (obj.has("doctorAdvice") || obj.has("drugReaction")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.EAT_MED_RECORD);
                JSONArray array = obj.optJSONArray("doctorAdvice");
                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        EatMedRecord eatMedRecord = jsonToEatMedRecord(json);
                        oneGroupData.addRecord(eatMedRecord);
                    }
                }
                if (obj.has("drugReaction")) {
                    JSONObject json = obj.optJSONObject("drugReaction");
                    if (json != null) {
                        BadReactionRecord badReactionRecord = jsonToBadReactionRecord(json);
                        oneGroupData.addRecord(badReactionRecord);
                    }
                }
                formData.addRecordGroup(oneGroupData);
            }
        }
        obj = totalObj.optJSONObject("query");
        if (obj != null) {
            if (obj.has("selfList")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.MEASURE_SELF_RECORD);
                JSONArray array = obj.getJSONArray("selfList");
                if(array!=null&&array.length()>0){
                    for(int i=0;i<array.length();i++) {
                        MeasureFormRecord record = jsonToMeasureFormRecord(MeasureFormRecord.Type.SELF,array.getJSONObject(i));
                        oneGroupData.addRecord(record);
                    }
                }
                formData.addRecordGroup(oneGroupData);
            }
            if (obj.has("doctorList")) {
                oneGroupData = new FollowUpFormGroup();
                oneGroupData.setFormGroupType(FollowUpFormGroup.Type.DOC_MEASURE_RECORD);
                JSONArray array = obj.getJSONArray("doctorList");
                if(array!=null&&array.length()>0){
                    for(int i=0;i<array.length();i++) {
                        MeasureFormRecord record = jsonToMeasureFormRecord(MeasureFormRecord.Type.DOCTOR,array.getJSONObject(i));
                        oneGroupData.addRecord(record);
                    }
                }
                formData.addRecordGroup(oneGroupData);
            }
        }

        return formData;
    }

    private MeasureFormRecord jsonToMeasureFormRecord(int type,JSONObject jsonObject) throws Exception{
        MeasureFormRecord record = new MeasureFormRecord();
        record.setName(jsonObject.optString("subject"));
        record.setResult(jsonObject.optString("analys"));
        record.setRetDescription(jsonObject.optString("resultId"));
        record.setScore(jsonObject.optString("score"));
        record.setType(type);
        return record;
    }

    private BadReactionRecord jsonToBadReactionRecord(JSONObject json) throws Exception {
        BadReactionRecord record = new BadReactionRecord();
        record.setFirstTime(json.optString("occurrenceTime"));
        record.setDurationTime(json.optString("dosageTime"));
        record.setEffect(json.optString("impact"));
        record.setSymptomsDecription(json.optString("frequency"));
        return record;
    }

    private EatMedRecord jsonToEatMedRecord(JSONObject json) throws Exception {
        EatMedRecord record = new EatMedRecord();
        record.setMedName(json.optString("productName"));
        record.setStartTime(json.optString("medicalDateBegin"));
        record.setEndTime(json.optString("medicalDateEnd"));
        record.setEatMethod(json.optString("directions"));
        record.setRate(json.optString("frequency"));
        record.setSingleAmount(json.optString("dosage") + json.optString("unit"));
        return record;
    }

    private OtherCheckRecord jsonToOtherCheckRecord(JSONObject json) throws Exception {
        OtherCheckRecord record = new OtherCheckRecord();
        record.setId(json.optString("uuid"));
        record.setName(json.optString("name"));
        record.getResult().setText(json.optString("result"));
        JSONArray pics = json.optJSONArray("imgs");
        if (pics != null && pics.length() > 0) {
            for (int i = 0; i < pics.length(); i++) {
                String url = pics.optString(i);
                Pic pic = new Pic();
                pic.setThumbUrl(url);
                pic.setUrl(url);
                record.getResult().addPic(pic);
            }
        }
        return record;
    }

    private IllnessChange jsonToIllnessChange(JSONObject obj) throws Exception {
        IllnessChange change = new IllnessChange();
        change.setDescription(obj.optString("result"));
        String state = obj.optString("state");
        if ("无效".equals(state)) {
            change.setStatus(IllnessChange.Status.INVALID);
        } else if ("好转".equals(state)) {
            change.setStatus(IllnessChange.Status.BETTER);
        } else if ("痊愈".equals(state)) {
            change.setStatus(IllnessChange.Status.BEST);
        } else {
            change.setStatus(IllnessChange.Status.INVALID);
        }
        return change;
    }

    /**
     * 获取病历详情
     *
     * @param medicalRecordUuid 病历id
     */
    public void getCaseRecordDetails(final String medicalRecordUuid) {
        final String shortUrl = UrlConstants.GET_PATIENT_CASE_REPORT_DETAILS;
        String version = "1.0";
        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("medicalRecordUuid", medicalRecordUuid)
                .build()
                .execute(new Callback<CaseReport>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public CaseReport parseNetworkResponse(Response response) throws Exception {
                        JSONObject obj = new JSONObject(response.body().string());
                        return jsonToCaseReport(obj, medicalRecordUuid);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(CaseReport response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    /**
     * json对象转化为病历对象
     *
     * @param obj
     * @param medicalRecordUuid
     * @return
     */
    private CaseReport jsonToCaseReport(JSONObject obj, final String medicalRecordUuid) throws Exception {
        CaseReport caseReport = new CaseReport();
        caseReport.setId(medicalRecordUuid);
        caseReport.setType(obj.optString("caseCategoryType"));
        caseReport.setSeeDoctorTime(obj.optString("seeDoctorTime"));
        caseReport.setInHospitalTime(obj.optString("startTime"));
        caseReport.setOutHospitalTime(obj.optString("endTime"));
        caseReport.setDoctorName(obj.optString("realName"));
        caseReport.setHospitalName(obj.optString("hospitalUuid"));
        for (int i = 1; i <= 5; i++) {
            String image = obj.optString("image" + i);
            if (!TextUtils.isEmpty(image) && !image.equals(null)) {
                Pic pic = new Pic();
                pic.setThumbUrl(image);
                pic.setUrl(image);
                caseReport.addPic(pic);
            }
        }
        return caseReport;
    }

    /**
     * 获取患者的病历以及表单列表
     *
     * @param customerUuid 患者id
     * @param doctorUuid   医生id
     */
    public void getAllTreatInfoRecordOfPatient(final String customerUuid, final String doctorUuid) {
        final String shortUrl = UrlConstants.GET_PATIENT_TREAT_RECORD;
        String version = "1.0";
        OkHttpUtils
                .get()
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("customerUuid", customerUuid)
                .addParams("doctorUuid", doctorUuid)
                .build()
                .execute(new Callback<ArrayList<PatientTreatInfo>>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public ArrayList<PatientTreatInfo> parseNetworkResponse(Response response) throws Exception {
                        ArrayList<PatientTreatInfo> treatInfos = new ArrayList<PatientTreatInfo>();
                        JSONArray array = new JSONArray(response.body().string());
                        if (array != null && array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                treatInfos.add(jsonToTreatInfo(array.getJSONObject(i)));
                            }
                        }
                        return treatInfos;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(ArrayList<PatientTreatInfo> response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

    /**
     * json对象转化成治疗记录信息对象
     *
     * @param jsonObject
     * @return
     */
    private PatientTreatInfo jsonToTreatInfo(JSONObject jsonObject) throws Exception {
        PatientTreatInfo treatInfo = new PatientTreatInfo();
        treatInfo.setId(jsonObject.optString("uuid"));
        int type = jsonObject.optInt("type");
        String caseCategoryType = jsonObject.optString("caseCategoryType");
        if (type == 0) {
            if ("1".equals(caseCategoryType)) {
//                门诊
                treatInfo.setTreatType(PatientTreatInfo.TREAT_TYPE_MEN_ZHEN);
            } else if ("0".equals(caseCategoryType)) {
//                住院
                treatInfo.setTreatType(PatientTreatInfo.TREAT_TYPE_ZHU_YUAN);
            }
        } else if (type == 1) {
            treatInfo.setTreatType(PatientTreatInfo.TREAT_TYPE_BIAO_DAN);
            if ("1".equals(caseCategoryType)) {
                //已读
                treatInfo.setUnread(false);
            } else if ("0".equals(caseCategoryType)) {
                //未读
                treatInfo.setUnread(true);
            }
        }
        treatInfo.setTime(jsonObject.optString("dt"));
        return treatInfo;
    }

    /**
     * 添加病历
     *
     * @param customerUuid     患者id
     * @param doctorUuid       医生id
     * @param hospitalUuid     医院id  如没有，可以为null
     * @param caseCategoryType 病历类型0住院号 1门诊号 2床位号 3病案号 4其他 如没有，可以为null
     * @param seeDoctorTime    就诊时间，格式如：yyyy-MM-dd hh:mm:ss 如没有，可以为null
     * @param imageIds         照片id数组（需要先上传本地照片，获得照片id）如没有，可以为null
     */
    public void addCaseReportForPatient(final String customerUuid, final String doctorUuid,
                                        final String hospitalUuid, final String caseCategoryType,
                                        final String seeDoctorTime, final String startTime, final String endTime, final String[] imageIds) {
        final String shortUrl = UrlConstants.ADD_CASE_REPORT_FOR_PATIENT;
        String version = "1.0";
        PostFormBuilder formBuilder = OkHttpUtils
                .post()
                .addHeader("Accept", "application/json")
                .url(UrlConstants.getWholeApiUrl(shortUrl, version))
                .addParams("customerUuid", customerUuid)
                .addParams("doctorUuid", doctorUuid);
        if (hospitalUuid != null) {
            formBuilder.addParams("hospitalUuid", hospitalUuid);
        }
        if (caseCategoryType != null) {
            formBuilder.addParams("caseCategoryType", caseCategoryType);
        }
        if (seeDoctorTime != null) {
            formBuilder.addParams("seeDoctorTime", seeDoctorTime);
        }
        if (startTime != null) {
            formBuilder.addParams("startTime", startTime);
        }
        if (endTime != null) {
            formBuilder.addParams("endTime", endTime);
        }
        if (imageIds != null && imageIds.length > 0) {
            for (int i = 1; i < imageIds.length + 1; i++) {
                formBuilder.addParams("image" + i, imageIds[i - 1]);
            }
        }
        formBuilder
                .build()
                .execute(new Callback<Boolean>() {

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        callListener(ON_SEND, shortUrl, null);
                    }

                    @Override
                    public Boolean parseNetworkResponse(Response response) throws Exception {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        return jsonObject.optBoolean("value");
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        callListener(ON_ERROR, shortUrl, e);
                    }

                    @Override
                    public void onResponse(Boolean response) {
                        callListener(ON_RESPONSE, shortUrl, response);
                    }
                });
    }

}
