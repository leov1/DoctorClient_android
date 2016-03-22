package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;

/**
 * Created by wangchao36 on 16/3/21.
 * 添加患者
 */
public class PatientAddActivity extends BaseTitleActivity implements View.OnClickListener {

    private ImageView ibAddressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);
        initViewOnBaseTitle(getResources().getString(R.string.patient_add_title));
        setBackListener();

        ibAddressBook = (ImageView) this.findViewById(R.id.ibAddressBook);
        ibAddressBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddressBook:
                Intent intent = new Intent(this, AddressBookSelectActivity.class);
                this.startActivityForResult(intent, 1);
                break;
        }
    }
}
