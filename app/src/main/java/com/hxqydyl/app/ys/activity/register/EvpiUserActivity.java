package com.hxqydyl.app.ys.activity.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.bean.register.RegisterFirst;
import com.hxqydyl.app.ys.http.register.HeadIconNet;
import com.hxqydyl.app.ys.http.register.RegisterSecNet;
import com.hxqydyl.app.ys.ui.CircleImageView;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.uploadimage.UploadPhotoUtil;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import framework.listener.RegisterSucListener;
import galleryfinal.wq.photo.widget.PickConfig;
import galleryfinal.yalantis.ucrop.UCrop;

/**
 * 完善姓名邮箱信息页面
 */
public class EvpiUserActivity extends BaseTitleActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, HeadIconNet.OnHeadIconNetListener
        , RegisterSecNet.OnRegisterSecListener, RegisterSucListener {

    private static final int CODE_FOR_WRITE_PERMISSION = 1119;
    private Button nextBtn;
    private CircleImageView image_upload;
    private TextView text_head;
    private EditText text_nick;
    private EditText text_email;
    private RadioGroup genderGroup;
    private RadioButton femaleRadioButton, maleRadioButton;

    private Intent intent;
    private final int SHOW_UPDATE_PHOTO = 3;
    private final int UPLOAD_LOCAL_PICTURE = 5;
    private final int SAVE_PHOTO_IMAGE = 6;
    private String fileString;//base64位图片

    private HeadIconNet headIconNet;
    private RegisterSecNet registerSecNet;

    private String smallImage;
    private String doctorUUid;
    private String mobile;
    private String nick;
    private String email;
    private int sex = 1;//默认为男士

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SHOW_UPDATE_PHOTO:
                    if (!TextUtils.isEmpty(msg.obj.toString())) {
                        text_head.setVisibility(View.GONE);
                        ImageLoader.getInstance().displayImage((String) msg.obj, image_upload, Utils.initImageLoader(R.mipmap.portrait_man, true));
                    }
                    dismissDialog();
                    UIHelper.ToastMessage(EvpiUserActivity.this, "上传成功");
                    break;
                case UPLOAD_LOCAL_PICTURE:
                    Log.d("gaolei", " case UPLOAD_LOCAL_PICTURE:------------------");
                    ArrayList<String> paths = intent.getStringArrayListExtra("data");
                    uploadUserPhotoNew(paths.get(0));
                    Log.d("gaolei", "uri.getPath()------------------" + paths.get(0));
                    break;
                case SAVE_PHOTO_IMAGE:
                    Log.d("gaolei", " case SAVE_PHOTO_IMAGE:------------------");
                    Map<String, String> map = (Map<String, String>) msg.obj;
                    headIconNet.uploadHeadImg(map);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evpi_user);

        getData();
        initViews();
        initListeners();
    }

    private void getData() {
        headIconNet = new HeadIconNet();
        headIconNet.setListener(this);
        registerSecNet = new RegisterSecNet();
        registerSecNet.setListener(this);

        doctorUUid = LoginManager.getRegisterUuid();

    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        addRegisterListener(this);
        nextBtn = (Button) findViewById(R.id.next_btn);
        image_upload = (CircleImageView) findViewById(R.id.image_upload);
        text_head = (TextView) findViewById(R.id.text_head);
        text_nick = (EditText) findViewById(R.id.text_nick);
        text_email = (EditText) findViewById(R.id.text_email);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        maleRadioButton = (RadioButton) findViewById(R.id.maleButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleButton);

    }

    private void initListeners() {
        setBackListener(this);
        nextBtn.setOnClickListener(this);
        image_upload.setOnClickListener(this);
        genderGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == maleRadioButton.getId()) {
            sex = 1;
        } else if (checkedId == femaleRadioButton.getId()) {
            sex = 2;
        }
    }

    /**
     * 下一步
     */
    private void loadNext() {
        nick = text_nick.getText().toString();
        email = text_email.getText().toString();

      /*  if (TextUtils.isEmpty(smallImage)) {
            UIHelper.ToastMessage(EvpiUserActivity.this, "请上传头像");
            return;
        }*/

        if (TextUtils.isEmpty(nick)) {
            UIHelper.ToastMessage(EvpiUserActivity.this, "姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            UIHelper.ToastMessage(EvpiUserActivity.this, "邮箱不能为空");
        }

        registerSecNet.registerSec(doctorUUid, email, sex + "", smallImage, nick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:

                loadNext();
                break;
            case R.id.image_upload:
                access();
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //为什么不在这处理图片呢？因为处理图片比较耗时，如果在这里处理图片，从图库或者拍照Activity时会不流畅，很卡卡卡，试试就知道了
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            this.intent = intent;
            handler.sendEmptyMessage(UPLOAD_LOCAL_PICTURE);
        }
    }

    public void uploadUserPhotoNew(final String filePath) {
        showDialog("正在上传...");
        //为什么另开一个线程呢?因为要把图片字节流转化为字符串上传，比较耗时，阻塞UI线程，会使应用卡卡卡，所以要另开一线程
        new Thread() {
            public void run() {
                fileString = UploadPhotoUtil.getInstance().getUploadBitmapZoomString(
                        filePath);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("icon", fileString);
                Message msg = handler.obtainMessage();
                msg.obj = map;
                msg.what = SAVE_PHOTO_IMAGE;
                handler.sendMessage(msg);

            }
        }.start();
    }

    private void access() {

        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(EvpiUserActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CODE_FOR_WRITE_PERMISSION);

                return;
            }
        }
        showEditPhotoLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                showEditPhotoLayout();
            } else {
                //用户不同意，自行处理即可
                // finish();
            }
        }
    }

    public void showEditPhotoLayout() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        new PickConfig.Builder(EvpiUserActivity.this)
                .isneedcrop(false)
                .actionBarcolor(Color.parseColor("#1F80B8"))
                .statusBarcolor(Color.parseColor("#FFFFFF"))
                .isneedcamera(true)
                .isSqureCrop(false)
                .setUropOptions(options)
                .maxPickSize(Integer.parseInt("5"))
                .spanCount(Integer.parseInt("3"))
                .pickMode(PickConfig.MODE_SINGLE_PICK).build();
    }

    @Override
    public void requestHeadIconNetSuc(HeadIconResult headIconResult) {
        if (headIconResult == null) {
            UIHelper.ToastMessage(EvpiUserActivity.this, "请求出错");
            return;
        }
        if (headIconResult.getQuery().getSuccess().equals("1")) {
            String photoUrl = TextUtils.isEmpty(headIconResult.getSmallUrl()) ? headIconResult.getImageUrl() : headIconResult.getSmallUrl();
            smallImage = TextUtils.isEmpty(headIconResult.getIcon()) ? headIconResult.getSmallImage() : headIconResult.getIcon();
            System.out.println("smallImage--->" + headIconResult.toString());
            Message msg = handler.obtainMessage();
            msg.obj = photoUrl;
            msg.what = SHOW_UPDATE_PHOTO;
            handler.sendMessage(msg);
        } else {
            UIHelper.ToastMessage(EvpiUserActivity.this, headIconResult.getQuery().getMessage());
        }
    }

    @Override
    public void requestHeadIconNetFail() {
        dismissDialog();
        UIHelper.ToastMessage(EvpiUserActivity.this, "上传失败");
    }

    @Override
    public void requestRegisterSecSuc(RegisterFirst registerFirst) {
        if (registerFirst == null) {
            UIHelper.ToastMessage(EvpiUserActivity.this, "服务器请求出错!");
            return;
        }
        if (registerFirst.getQuery().getSuccess().equals("1")) {
            startActivity(new Intent(this, EvpiAddressActivity.class));
        } else {
            UIHelper.ToastMessage(EvpiUserActivity.this, registerFirst.getQuery().getMessage());
        }

    }

    @Override
    public void requestRegisterSecFail() {
        UIHelper.ToastMessage(EvpiUserActivity.this, "服务器请求出错!");
    }

    @Override
    public void onRegisterSuc() {
        finish();
    }

    @Override
    protected void onDestroy() {
        removeRegisterListener(this);
        super.onDestroy();
    }
}
