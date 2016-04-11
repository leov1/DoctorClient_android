package com.hxqydyl.app.ys.bean;

import android.util.Log;

import com.hxqydyl.app.ys.bean.follow.plan.Medicine;
import com.hxqydyl.app.ys.bean.follow.plan.MedicineDosage;
import com.hxqydyl.app.ys.utils.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao36 on 16/4/12.
 */
public class Advice implements Serializable{

    private String uuid;
    private String drugReaction;
    private String cureNote;
    private ArrayList<Medicine> medicineList;

    public static Advice parseDetailJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            Advice advice = new Advice();
            advice.setUuid(jsonObject.getString("uuid"));
            advice.setDrugReaction(jsonObject.getString("drugReaction"));
            advice.setCureNote(jsonObject.getString("cureNote"));
            advice.setMedicineList(Medicine.parse(jsonObject.optJSONArray("child")));
            return advice;
        } catch (Exception e) {
            Log.e("client", e.getMessage());
            return null;
        }
    }

    public String toJson(String customerUuid, String visitPreceptUuid) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serviceStaffUuid", LoginManager.getDoctorUuid());
        jsonObject.put("customerUuid", customerUuid);
        jsonObject.put("visitPreceptUuid", visitPreceptUuid);
        List<Medicine> medicineList = getMedicineList();
        JSONArray jsonArray = new JSONArray();
        for (Medicine m : medicineList) {
            JSONObject object = new JSONObject();
            object.put("food", m.getFood());
            object.put("medicineUuid", m.getUuid());
            StringBuffer sb = new StringBuffer();
            if (m.isTimeMorning()) {
                sb.append("早");
            }
            if (m.isTimeNoon()) {
                if (sb.length() > 0) sb.append(",");
                sb.append("中");
            }
            if (m.isTimeNight()) {
                if (sb.length() > 0) sb.append(",");
                sb.append("晚");
            }
            object.put("directions", sb.toString());
            StringBuffer sbFrequency = new StringBuffer();
            StringBuffer sbDosage = new StringBuffer();
            for (MedicineDosage md : m.getMdList()) {
                if (sbFrequency.length() > 0)  {
                    sbFrequency.append(",");
                    sbDosage.append(",");
                }
                sbFrequency.append(md.getDay());
                sbDosage.append(md.getSize()).append("|").append(md.getUnit());
            }

            object.put("frequency", sbFrequency);
            object.put("dosage", sbDosage);

            jsonArray.put(object);
        }
        jsonObject.put("child", jsonArray);
        return jsonObject.toString();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDrugReaction() {
        return drugReaction;
    }

    public void setDrugReaction(String drugReaction) {
        this.drugReaction = drugReaction;
    }

    public String getCureNote() {
        return cureNote;
    }

    public void setCureNote(String cureNote) {
        this.cureNote = cureNote;
    }

    public ArrayList<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(ArrayList<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

}
