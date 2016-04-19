package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.patient.PatientInfoActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 随访申请
 */
public class FollowApplyDetailActivity extends BaseTitleActivity
        implements View.OnClickListener {

    private RelativeLayout rlPatient;
    private Button btnApply;
    private Button btnCancel;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvAge;
    private TextView tvQ;
    private TextView tvContent;

    private String applyUuid = null;
    private String avatar = null;

    private FollowApply fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_apply_detail);
        Intent intent = getIntent();
        applyUuid = intent.getStringExtra("applyUuid");
        avatar = intent.getStringExtra("avatar");

        initViews();
        initListeners();
        getApplyDetail();
    }

    private void initViews() {
        initViewOnBaseTitle("随访申请详情");
        rlPatient = (RelativeLayout) findViewById(R.id.rl_patient);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        ivAvatar = (ImageView) findViewById(R.id.head_img);
        tvName = (TextView) findViewById(R.id.head_name);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvQ = (TextView) findViewById(R.id.tvQ);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    private void initListeners() {
        setBackListener(this);
        rlPatient.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.rl_patient:
                Intent intent = new Intent(this, PatientInfoActivity.class);
                intent.putExtra("patientId", fa.getCustomerUuid());
                startActivity(intent);
                break;
            case R.id.btnApply:
                Intent okIntent = new Intent(this, FollowApplyOkActivity.class);
                okIntent.putExtra("applyUuid", applyUuid);
                okIntent.putExtra("customerUuid", fa.getCustomerUuid());
                startActivity(okIntent);
                break;
            case R.id.btnCancel:
                refuseVivistApply();
                break;
        }
    }

    private void getApplyDetail() {
        showDialog("正在加载");
        FollowApplyNet.getApplyDetail(applyUuid, new FollowCallback(this){
            @Override
            public void onResult(String result) {
                super.onResult(result);
                if (FollowApplyNet.myDev)
                    result = "[" +
                        "{" +
                        "\"ifStart\": null," +
                        "\"applyUuid\": \"2\"," +
                        "\"imgs\": null," +
                        "\"address\": \"北京市\"," +
                        "\"firstDiagnosis\": null," +
                        "\"certCode\": \"11231321213\"," +
                        "\"illnessDescription\": \"illnessDescription\"," +
                        "\"doctorUuid\": \"rvicestaff0000001553\"," +
                        "\"sex\": \"2\"," +
                        "\"mobile\": \"13671050634\"," +
                        "\"weight\": null," +
                        "\"industry\": \"123\"," +
                        "\"marryState\": \"1\"," +
                        "\"realName\": \"人发个\"," +
                        "\"diseaseTime\": null," +
                        "\"customerUuid\": \"2\"," +
                        "\"diagnose\": \"diagnosediagnosediagnose\"," +
                        "\"nickname\": \"\"," +
                        "\"email\": \"123123@123213.123123\"," +
                        "\"age\": \"1\"," +
                        "\"seizureTimes\": null," +
                        "\"height\": null," +
                        "\"nearlyDrugs\": null" +
                        "}" +
                        "]";

                dismissDialog();
                try{
                    List<FollowApply> tmp = FollowApply.parseList(result);
                    if (tmp.size() > 0) {
                        fa = tmp.get(0);
                        tvName.setText(fa.getRealName());
                        tvAge.setText(fa.getAge() + "岁");
                        tvQ.setText(fa.getIllnessDescription());
                        tvContent.setText(fa.getSymptoms());
                        if ("1".equals(fa.getSex())) {
                            tvSex.setText("男");
                        } else {
                            tvSex.setText("女");
                        }
                        ImageLoader.getInstance().displayImage(avatar, ivAvatar);
                    } else {
                        Toast.makeText(FollowApplyDetailActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }catch (Exception e){
                    onFail("","解析出错啦，刷新一下再试一次吧");
                }

            }

            @Override
            public void onFail(String status, String msg) {
                super.onFail(status, msg);
                UIHelper.ToastMessage(FollowApplyDetailActivity.this,msg);
                if ("0".equals(status)) {
                    finish();
                }
            }
        });
    }

    private void refuseVivistApply() {
        DialogUtils.showSignleEditTextDialog(this, new DialogUtils.SaveTextListener() {
            @Override
            public boolean save(String text) {
                FollowApplyNet.refuseVivistApply(fa.getApplyUuid(), text, new FollowCallback(FollowApplyDetailActivity.this){
                            @Override
                            public void onResult(String result) {
                                super.onResult(result);
                                UIHelper.ToastMessage(FollowApplyDetailActivity.this, "已拒绝");
                                finish();
                            }
                        });
                return true;
            }
        });
    }

}
