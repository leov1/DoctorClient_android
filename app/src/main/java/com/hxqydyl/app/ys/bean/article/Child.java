package com.hxqydyl.app.ys.bean.article;

/**
 * Created by hxq on 2016/3/28.
 */
public class Child {
    private boolean isChecked;

    private String customerUuid;
    private String customerMessage;
    private String customerImg;
    private String sex;
    private String customerName;
    private String age;

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getCustomerImg() {
        return customerImg;
    }

    public void setCustomerImg(String customerImg) {
        this.customerImg = customerImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Child{" +
                "isChecked=" + isChecked +
                ", customerUuid='" + customerUuid + '\'' +
                ", customerMessage='" + customerMessage + '\'' +
                ", customerImg='" + customerImg + '\'' +
                ", sex='" + sex + '\'' +
                ", customerName='" + customerName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
