package com.hxqydyl.app.ys.activity.follow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.BigPicActivity;
import com.hxqydyl.app.ys.activity.patient.PatientInfoActivity;
import com.hxqydyl.app.ys.adapter.FollowApplyAdapter;
import com.hxqydyl.app.ys.adapter.PicAdapter;
import com.hxqydyl.app.ys.bean.follow.FollowApply;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.bean.response.FollowUserApplyResponse;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.http.follow.FollowApplyNet;
import com.hxqydyl.app.ys.http.follow.FollowCallback;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xus.http.httplib.model.PostPrams;

import java.util.List;

/**
 * 随访申请
 */
public class FollowApplyDetailActivity extends BaseRequstActivity
        implements View.OnClickListener,AdapterView.OnItemClickListener{

    private RelativeLayout rlPatient;
    private Button btnApply;
    private Button btnCancel;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvAge;
    private TextView tvQ;
    private TextView tvContent;
    private DisplayImageOptions options;
    private String applyUuid = null;
    private PicAdapter adapter;
    private String avatar = null;
    private GridView gv_pics;
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
        gv_pics = (GridView) findViewById(R.id.gv_pics);
        rlPatient = (RelativeLayout) findViewById(R.id.rl_patient);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        ivAvatar = (ImageView) findViewById(R.id.head_img);
        tvName = (TextView) findViewById(R.id.head_name);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvQ = (TextView) findViewById(R.id.tvQ);
        tvContent = (TextView) findViewById(R.id.tvContent);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(this, 50)))
                .showImageForEmptyUri(R.mipmap.portrait_man)
                .showImageOnFail(R.mipmap.portrait_man)
                .build();
    }

    private void initListeners() {
        setBackListener(this);
        rlPatient.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        toNomalNet(toGetParams(toParamsBaen("applyUuid", applyUuid), toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), FollowUserApplyResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_APPLY_DETAIL, "1.0"), "获取随访申请详情中...");
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        switch (flag) {
            case 1:
                FollowUserApplyResponse rs = (FollowUserApplyResponse) bean;
                if (rs.getRelist() != null && rs.getRelist().size() > 0) {
                    fa = rs.getRelist().get(0);
                    tvName.setText(fa.getRealName());
                    tvAge.setText(TextUtils.isEmpty(fa.getAge()) ? "" : fa.getAge() + "岁");
                    tvQ.setText(TextUtils.isEmpty(fa.getIllnessDescription()) ? "" : "问题" + fa.getAge());
                    tvContent.setText(fa.getSymptoms());
                    if (StringUtils.isEmpty(fa.getSex())) {
                        tvSex.setVisibility(View.GONE);
                    } else {
                        tvSex.setVisibility(View.VISIBLE);
                        if ("1".equals(fa.getSex())) {
                            tvSex.setText("男");
                        } else {
                            tvSex.setText("女");
                        }
                    }
                    ImageLoader.getInstance().displayImage(avatar, ivAvatar, options);
                    if (fa.getImgs() != null && fa.getImgs().size() > 0)
                        adapter = new PicAdapter(this, fa.getImgs());
                    gv_pics.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(gv_pics);
                    gv_pics.setOnItemClickListener(this);
                }
                break;
            case 2:
                BaseResponse baseResponse = (BaseResponse) bean;
                if (baseResponse.message.contains("成功")) {
                    UIHelper.ToastMessage(FollowApplyDetailActivity.this, "已拒绝");
                    finish();
                }
                break;
        }
    }

    private void refuseVivistApply() {
        DialogUtils.showSignleEditTextDialog(this, new DialogUtils.SaveTextListener() {
            @Override
            public boolean save(String text) {
                toNomalNet(toPostParams(toParamsBaen("applyUuid", fa.getApplyUuid()), toParamsBaen("refuseReason", text), toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), BaseResponse.class, 2, UrlConstants.getWholeApiUrl(UrlConstants.REFUSE_VIVIST_APPLY, "1.0"), "正在拒绝...");
                return true;
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((GridView.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BigPicActivity.toBigPic(fa.getImgs(),position,this);
    }
}
