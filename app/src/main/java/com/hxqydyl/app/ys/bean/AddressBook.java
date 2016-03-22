package com.hxqydyl.app.ys.bean;

import java.util.Comparator;

/**
 * Created by wangchao36 on 16/3/21.
 * 通讯录信息
 */
public class AddressBook {

    private long contactId;
    private String name;
    private String phone;
    private String sortKey; //pinyin

    public AddressBook(long contactId, String name, String phone) {
        this.contactId = contactId;
        this.name = name;
        this.phone = phone;
    }

    public AddressBook(long contactId, String name, String phone, String sortKey) {
        this.contactId = contactId;
        this.name = name;
        this.phone = phone;
        this.sortKey = sortKey;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class AddressBookComparator implements Comparator<AddressBook> {

        @Override
        public int compare(AddressBook ab1, AddressBook ab2) {
            return ab1.getSortKey().compareToIgnoreCase(ab2.getSortKey());
        }
    }
}
