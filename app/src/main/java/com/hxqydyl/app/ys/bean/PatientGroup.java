package com.hxqydyl.app.ys.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/18.
 */
public class PatientGroup extends BaseBean implements Serializable {
    private String groupName;
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public PatientGroup() {
    }

    public PatientGroup(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
}
