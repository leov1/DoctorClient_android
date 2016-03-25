package com.hxqydyl.app.ys.activity.patient;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.AddressBookSelectAdapter;
import com.hxqydyl.app.ys.bean.AddressBook;
import com.hxqydyl.app.ys.ui.AssortView;
import com.hxqydyl.app.ys.utils.PinyinUtils;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by wangchao36 on 16/3/21.
 * 通过通讯录选择患者
 */
public class AddressBookSelectActivity extends BaseTitleActivity
    implements View.OnClickListener {

    private int WHAT_INIT_DATA = 1;

    private ExpandableListView exList;
    private AssortView assortView;

    private List<AddressBook> addressBookList;
    private AddressBookSelectAdapter adapter;
    private ImageView ivSave;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_INIT_DATA) {
                initData();
            }
            return false;
        }
    });

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY,
            ContactsContract.Contacts.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressbook_select);
        initViewOnBaseTitle("添加患者");
        setBackListener();

        ivSave = (ImageView) findViewById(R.id.right_img);
        ivSave.setVisibility(View.VISIBLE);
        ivSave.setImageDrawable(getResources().getDrawable(R.drawable.btn_add_white));
        ivSave.setOnClickListener(this);
        exList = (ExpandableListView) this.findViewById(R.id.exList);
        assortView = (AssortView) this.findViewById(R.id.assortView);
        addressBookList = new ArrayList<>();

        handler.sendEmptyMessage(WHAT_INIT_DATA);
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

        }
    }

    private void initData() {
        try {
            ContentResolver resolver = this.getContentResolver();
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONES_PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(0);
                    if (TextUtils.isEmpty(phoneNumber)) {
                        continue;
                    }
                    String contactName = phoneCursor.getString(1);
                    String sortkey = contactName;
                    if (StringUtils.isNotEmpty(contactName)) {
                        sortkey = PinyinUtils.toPinYin(contactName.charAt(0));
                        if (sortkey == null) {
                            sortkey = contactName;
                        }
                    }
                    long contactId = phoneCursor.getLong(4);
                    addressBookList.add(new AddressBook(contactId, contactName, phoneNumber, sortkey));
                }
                adapter = new AddressBookSelectAdapter(this, addressBookList);
                exList.setAdapter(adapter);
                //展开所有
                for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
                    exList.expandGroup(i);
                }
                exList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        AddressBook ab = adapter.getChild(groupPosition, childPosition);
                        adapter.setSelectAB(ab);

                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "通讯录权限获取失败", Toast.LENGTH_SHORT);
        }

        //字母按键回调
        assortView.setOnTouchAssortListener(new AssortView.OnTouchAssortListener() {
            public void onTouchAssortListener(String str) {
                int index = adapter.getAssort().getHashList().indexOfKey(str);
                if (index != -1) {
                    exList.setSelectedGroup(index);
                }
            }
            @Override
            public void onTouchAssortUP() {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_img:
                AddressBook ab = adapter.getSelectAB();
                if (ab == null) {
                    Toast.makeText(this, "请选择", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("ab", ab);
                setResult(0, intent);
                finish();
                break;
        }
    }
}
