package com.hxqydyl.app.ys.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/18.
 */
public class PatientGroup extends BaseBean implements Serializable {
    private String groupName;
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private ArrayList<Patient> customers = new ArrayList<Patient>();

    public PatientGroup() {
    }

    public PatientGroup(String groupName) {
        this.groupName = groupName;
    }

    //该分组下是否含有患者
    public boolean isHasPatient(){
        return customers != null && customers.size() != 0;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<Patient> getCustomers() {
        return customers;
    }

    public void addPatient(Patient patient) {
        customers.add(patient);
    }

    public void removePatient(Patient patient) {
        customers.remove(patient);
    }
}
