package com.hxqydyl.app.ys.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.AddressBook;

/**
 * Created by wangchao36 on 16/3/21.
 * 添加患者
 */
public class PatientAddActivity extends BaseTitleActivity implements View.OnClickListener {

    private ImageView ibAddressBook;
    private EditText etPhone;
    private EditText etRealName;
    private EditText etDiagnosis;
    private TextView tvGroupName;

    private String[] groupItem = {"默认分组", "抑郁", "精神分裂", "痴呆"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add);
        initViewOnBaseTitle(getResources().getString(R.string.patient_add_title));
        setBackListener();

        ibAddressBook = (ImageView) findViewById(R.id.ibAddressBook);
        ibAddressBook.setOnClickListener(this);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etRealName = (EditText) findViewById(R.id.etRealName);
        etDiagnosis = (EditText) findViewById(R.id.etDiagnosis);
        tvGroupName = (TextView) findViewById(R.id.tvGroupName);
        tvGroupName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibAddressBook:
                Intent intent = new Intent(this, AddressBookSelectActivity.class);
                this.startActivityForResult(intent, 0);
                break;
            case R.id.tvGroupName:
                groupDialog((TextView) v, groupItem);
                break;
        }
    }

    private void groupDialog(final TextView tv, final String[] items) {
        final NormalListDialog dialog = new NormalListDialog(this, items);
        dialog.title("请选择分组")
                .titleBgColor(getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(items[position]);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            AddressBook ab = (AddressBook) data.getSerializableExtra("ab");
            etPhone.setText(ab.getPhone());
            etRealName.setText(ab.getName());
        }
    }
}
