package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.EvpiPhotoAdapter;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.IconBean;
import com.hxqydyl.app.ys.http.register.SaveUserIconNet;
import com.hxqydyl.app.ys.http.register.UploadIconsNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyGridView;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.ArrayList;

import framework.listener.RegisterSucListener;
import framework.listener.RegisterSucMag;
import galleryfinal.wq.photo.widget.PickConfig;
import galleryfinal.yalantis.ucrop.UCrop;

/**
 * 完善注册z照片信息
 */
public class EvpiPhotoActivity extends BaseTitleActivity implements View.OnClickListener,
        UploadIconsNet.OnUploadIconsListener, SaveUserIconNet.OnSaveUserIconListener {

    public ArrayList<String> mPhotoList = new ArrayList<>();//选择的图片临时列表

    private MyGridView gridView;
    private EvpiPhotoAdapter adapter;

    private RelativeLayout edit_photo_fullscreen_layout, edit_photo_outer_layout;
    private TextView take_picture;
    private TextView select_local_picture;
    private Button registerBtn;

    private UploadIconsNet uploadIconsNet;
    private SaveUserIconNet saveUserIconNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_evpi);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        uploadIconsNet = new UploadIconsNet();
        uploadIconsNet.setListener(this);
        saveUserIconNet = new SaveUserIconNet();
        saveUserIconNet.setListener(this);

        edit_photo_fullscreen_layout = (RelativeLayout) findViewById(R.id.edit_photo_fullscreen_layout);
        edit_photo_outer_layout = (RelativeLayout) findViewById(R.id.edit_photo_outer_layout);
        take_picture = (TextView) findViewById(R.id.take_picture);
        select_local_picture = (TextView) findViewById(R.id.select_local_picture);
        registerBtn = (Button) findViewById(R.id.register_btn);

        gridView = (MyGridView) findViewById(R.id.gridview);
        adapter = new EvpiPhotoAdapter(this, mPhotoList);
        gridView.setAdapter(adapter);

    }

    private void initListeners() {
        setBackListener(this);
        take_picture.setOnClickListener(this);
        select_local_picture.setOnClickListener(this);
        registerBtn.setOnClickListener(this);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPhotoList.size()) {
                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(90);
                    new  PickConfig.Builder(EvpiPhotoActivity.this)
                            .isneedcrop(false)
                            .actionBarcolor(Color.parseColor("#1F80B8"))
                            .statusBarcolor(Color.parseColor("#FFFFFF"))
                            .isneedcamera(true)
                            .isSqureCrop(false)
                            .setUropOptions(options)
                            .maxPickSize(Integer.parseInt("5"))
                            .spanCount(Integer.parseInt("3"))
                            .pickMode(PickConfig.MODE_MULTIP_PICK).build();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            //在data中返回 选择的图片列表
            ArrayList<String>paths=data.getStringArrayListExtra("data");
            mPhotoList.clear();
            mPhotoList.addAll(paths);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.register_btn:
                uploadIcoms();
                break;
        }
    }

    /**
     * 上传图片，然后在注册
     */
    private void uploadIcoms() {
        if (mPhotoList.size() == 0) {
            UIHelper.ToastMessage(EvpiPhotoActivity.this, "请上传图片");
            return;
        }
        showDialog("请稍等...");
        uploadIconsNet.saveIcons(mPhotoList);
        System.out.println("list --->" + mPhotoList.toString());
    }

    @Override
    public void requestUploadIconsSuc(ArrayList<IconBean> iconBeans) {
        if (iconBeans == null) {
            dismissDialog();
            UIHelper.ToastMessage(EvpiPhotoActivity.this,"上传图片失败");
            return;
        }

        saveUserIconNet.saveUserIcon(LoginManager.getRegisterUuid(), StringUtils.listToString(mPhotoList, ','));
        //       UIHelper.ToastMessage(EvpiPhotoActivity.this,query.getMessage());
    }

    @Override
    public void requestUploadIconsFail() {
        dismissDialog();
    }

    @Override
    public void requestSaveIconSuc(Query query) {
        dismissDialog();
        if (query == null) {
            UIHelper.ToastMessage(EvpiPhotoActivity.this, "请求出错");
            return;
        }
        if (query.getSuccess().equals("1")) {
            UIHelper.ToastMessage(EvpiPhotoActivity.this, "注册成功");
            LoginManager.setDoctorUuid(LoginManager.getRegisterUuid());
            removeBeforViews();
            finish();
        } else {
            UIHelper.ToastMessage(EvpiPhotoActivity.this, query.getMessage());
        }

    }

    /**
     * 观察者移除之前页面
     */
    private void removeBeforViews() {
        ArrayList<RegisterSucListener> registerSucListeners = RegisterSucMag.getInstance().downloadListeners;
        if (registerSucListeners == null || registerSucListeners.size() == 0) return;
        for (int i = 0; i < registerSucListeners.size(); i++) {
            registerSucListeners.get(i).onRegisterSuc();
        }
    }

    @Override
    public void requestSaveIconFail() {
        dismissDialog();
    }

}
