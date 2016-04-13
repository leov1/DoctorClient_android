package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.http.sendMsg.SendMsgToPaNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.LoginManager;

/**
 * 群发消息页面
 */
public class MassActivity extends BaseTitleActivity implements View.OnClickListener,SendMsgToPaNet.OnSendMsgToPaListener {

    private EditText edit_msg;
    private Button btn_choice_patient;
    private Button btn_send;
    private TextView tv_name;

    private String strsUuid;
    private String strName;

    private Intent intent;
    private SendMsgToPaNet sendMsgToPaNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass);

        initViews();
        initListener();
    }

    private void initListener() {
        setBackListener();
        btn_send.setOnClickListener(this);
        btn_choice_patient.setOnClickListener(this);
    }

    private void initViews() {
        sendMsgToPaNet = new SendMsgToPaNet();
        sendMsgToPaNet.setListener(this);

        edit_msg = (EditText) findViewById(R.id.edit_msg);
        btn_choice_patient = (Button) findViewById(R.id.btn_choice_patient);
        btn_send  = (Button) findViewById(R.id.btn_send);
        tv_name = (TextView) findViewById(R.id.tv_name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == RESULT_OK){
           strsUuid = data.getStringExtra("customerUuids");
           strName = data.getStringExtra("customerNames");
           tv_name.setText(strName);
       }
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_choice_patient:
              intent = new Intent(MassActivity.this,PatientSelectActivity.class);
              startActivityForResult(intent, 0);
              break;
          case R.id.btn_send:
              if (TextUtils.isEmpty(edit_msg.getText().toString())){
                  UIHelper.ToastMessage(MassActivity.this,"请输入发送内容");
                  return;
              }

              if (TextUtils.isEmpty(strName)){
                  UIHelper.ToastMessage(MassActivity.this,"请选择患者");
                  return;
              }
              System.out.println("id---"+strsUuid);
              System.out.println("doc---"+LoginManager.getDoctorUuid());
              showDialog("发送中...");
              sendMsgToPaNet.sendMsgToPa(strsUuid, LoginManager.getDoctorUuid(),edit_msg.getText().toString());
              break;
      }
    }

    @Override
    public void sendMsgSuc(Query query) {
        dismissDialog();
        if (query == null)return;
        if (query.getSuccess().equals("1")){
            UIHelper.ToastMessage(MassActivity.this, query.getMessage());
            finish();
            return;
        }
        UIHelper.ToastMessage(MassActivity.this,query.getMessage());
    }

    @Override
    public void sendMsgFail() {
        dismissDialog();
        UIHelper.ToastMessage(MassActivity.this,"请求失败");
    }
}
