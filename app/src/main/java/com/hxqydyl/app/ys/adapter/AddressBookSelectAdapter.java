package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.AddressBook;
import com.hxqydyl.app.ys.utils.HashList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wangchao36 on 16/3/21.
 * 选择通讯录适配器
 */
public class AddressBookSelectAdapter extends BaseExpandableListAdapter {

    private List<AddressBook> AddressBookList;
    private Context context;
    private LayoutInflater inflater;
    private AssortPinyinList assort = new AssortPinyinList();
    private LanguageComparatorEN comparatorEN = new LanguageComparatorEN();
    private AddressBook.AddressBookComparator addressBookComparator = new AddressBook.AddressBookComparator();

    private String selectPhone = null;

    public AddressBookSelectAdapter(Context context, List<AddressBook> AddressBookList) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.AddressBookList = AddressBookList;
        sort();

    }

    private void sort() {
        // 分类
        for (AddressBook AddressBook : AddressBookList) {
            assort.getHashList().add(AddressBook);
        }
        assort.getHashList().sortKeyComparator(comparatorEN);
        for(int i=0,length=assort.getHashList().size();i<length;i++) {
            Collections.sort((assort.getHashList().getValueListIndex(i)), addressBookComparator);
        }

    }

    public AddressBook getChild(int group, int child) {
        return assort.getHashList().getValueIndex(group, child);
    }

    public long getChildId(int group, int child) {
        return child;
    }

    public View getChildView(int group, int child, boolean arg2,
                             View contentView, ViewGroup arg4) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.item_addressbook, null);
        }
        AddressBook addressBook = assort.getHashList().getValueIndex(group, child);
        TextView textView = (TextView) contentView.findViewById(R.id.name);
        RadioButton rb = (RadioButton) contentView.findViewById(R.id.rbPhone);
        textView.setText(addressBook.getName());
        if (selectPhone != null && selectPhone.equals(addressBook.getPhone())) {
            rb.setChecked(true);
        } else {
            rb.setChecked(false);
        }

        return contentView;
    }

    public int getChildrenCount(int group) {
        return assort.getHashList().getValueListIndex(group).size();
    }

    public Object getGroup(int group) {
        return assort.getHashList().getValueListIndex(group);
    }

    public int getGroupCount() {
        return assort.getHashList().size();
    }

    public long getGroupId(int group) {
        return group;
    }

    public View getGroupView(int group, boolean arg1, View contentView,
                             ViewGroup arg3) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.item_addressbook_group, null);
            contentView.setClickable(true);
        }
        AddressBook AddressBook = assort.getHashList().getValueIndex(group, 0);
        TextView textView = (TextView) contentView.findViewById(R.id.name);
        textView.setText(assort.getFirstChar(AddressBook.getSortKey()));
        return contentView;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    public AssortPinyinList getAssort() {
        return assort;
    }

    public class AssortPinyinList {
        private HashList<String, AddressBook> hashList = new HashList<>(new HashList.KeySort<String, AddressBook>(){
            public String getKey(AddressBook value) {
                return getFirstChar(value.getSortKey());
            }});

        //获得字符串的首字母 首字符 转汉语拼音
        public  String getFirstChar(String value) {
            char firstChar = value.charAt(0);   // 首字符
            String first = null;    // 首字母分类
            if ((firstChar >= 97 && firstChar <= 122)) {    // 将小写字母改成大写
                firstChar -= 32;
            }
            if (firstChar >= 65 && firstChar <= 90) {
                first = String.valueOf((char) firstChar);
            } else {
                first = "#";    // 认为首字符为数字或者特殊字符
            }
            if (first == null) {
                first = "?";
            }
            return first;
        }
        public HashList<String, AddressBook> getHashList() {
            return hashList;
        }
    }

    public class LanguageComparatorEN implements Comparator<String> {

        public int compare(String ostr1, String ostr2) {
            return ostr1.compareToIgnoreCase(ostr2);
        }

    }
}
