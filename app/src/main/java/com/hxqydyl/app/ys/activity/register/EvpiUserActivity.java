package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.bean.register.HeadIconResult;
import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.register.HeadIconNet;
import com.hxqydyl.app.ys.http.register.RegisterSecNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.uploadimage.UploadPhotoUtil;
import com.hxqydyl.app.ys.utils.CommonUtils;
import com.hxqydyl.app.ys.utils.Constants;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 完善姓名邮箱信息页面
 */
public class EvpiUserActivity extends BaseTitleActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,HeadIconNet.OnHeadIconNetListener,RegisterSecNet.OnRegisterSecListener{

    private Button nextBtn;
    private ImageView image_upload;
    private EditText text_nick;
    private EditText text_email;
    private TextView take_picture;
    private TextView select_local_picture;
    private RadioGroup genderGroup;
    private RadioButton femaleRadioButton,maleRadioButton;

    private RelativeLayout edit_photo_fullscreen_layout,edit_photo_outer_layout,uploading_photo_progress;
    private Animation get_photo_layout_out_from_up, get_photo_layout_in_from_down;
    private Intent intent;
    private final int NONE = 0, TAKE_PICTURE = 1, LOCAL_PICTURE = 2;
    private final int SHOW_UPDATE_PHOTO = 3;
    private final int UPLOAD_TAKE_PICTURE = 4;
    private final int UPLOAD_LOCAL_PICTURE =5;
    private final int SAVE_PHOTO_IMAGE = 6;
    File sdcardDir = Environment.getExternalStorageDirectory();
    private String photo_path = sdcardDir.getPath() + "/Gosu/cache/photoes/";
    private String photo_take_file_path = photo_path + "temp.png";
    private String fileString;//base64位图片

    private HeadIconNet headIconNet;
    private RegisterSecNet registerSecNet;

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
                    ImageLoader.getInstance().displayImage((String) msg.obj, image_upload);
                    uploading_photo_progress.setVisibility(View.GONE);
                    UIHelper.ToastMessage(EvpiUserActivity.this, "上传成功");
                    break;
                case UPLOAD_LOCAL_PICTURE:
                    Log.d("gaolei", " case UPLOAD_LOCAL_PICTURE:------------------");
                    Uri uri = intent.getData();

                    try {
                        String[] pojo = {MediaStore.Images.Media.DATA};
                        // The method managedQuery(Uri, String[], String, String[],
                        // String) from the type Activity is deprecated
                        // android用CursorLoader代替

                        CursorLoader cursorLoader = new CursorLoader(EvpiUserActivity.this, uri, pojo, null,null, null);
                        Cursor cursor = cursorLoader.loadInBackground();
                        cursor.moveToFirst();
                        String photo_local_file_path = cursor.getString(cursor.getColumnIndex(pojo[0]));
                        uploadUserPhotoNew(photo_local_file_path);

                        Log.d("gaolei", "uri.getPath()------------------" + uri.getPath());

                    } catch (Exception e) {
                    }
                    break;
                case UPLOAD_TAKE_PICTURE:
                    Log.d("gaolei", " case UPLOAD_TAKE_PICTURE:------------------");
                    Log.d("gaolei", "photo_take_file_path------------------" + photo_take_file_path);
                    uploadUserPhotoNew(photo_take_file_path);
                    break;
                case SAVE_PHOTO_IMAGE:
                    Log.d("gaolei", " case SAVE_PHOTO_IMAGE:------------------");
                    Map<String, String> map = (Map<String, String>) msg.obj;
                    map.put("callback","hxq");
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

        doctorUUid = LoginManager.getDoctorUuid();

    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        nextBtn = (Button) findViewById(R.id.next_btn);
        image_upload = (ImageView) findViewById(R.id.image_upload);
        text_nick = (EditText) findViewById(R.id.text_nick);
        text_email = (EditText) findViewById(R.id.text_email);

        take_picture = (TextView) findViewById(R.id.take_picture);
        select_local_picture = (TextView) findViewById(R.id.select_local_picture);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        maleRadioButton = (RadioButton) findViewById(R.id.maleButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleButton);

        edit_photo_fullscreen_layout = (RelativeLayout) findViewById(R.id.edit_photo_fullscreen_layout);
        edit_photo_outer_layout = (RelativeLayout) findViewById(R.id.edit_photo_outer_layout);
        uploading_photo_progress = (RelativeLayout) findViewById(R.id.uploading_photo_progress);
    }

    private void initListeners() {
        setBackListener(this);
        nextBtn.setOnClickListener(this);
        take_picture.setOnClickListener(this);
        select_local_picture.setOnClickListener(this);
        genderGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
         if (checkedId == maleRadioButton.getId()){
             sex = 1;
         }else if(checkedId == femaleRadioButton.getId()){
             sex = 2;
         }
    }

    /**
     * 下一步
     */
    private void loadNext(){
       nick = text_nick.getText().toString();
       email = text_email.getText().toString();

        if (TextUtils.isEmpty(fileString)){
            UIHelper.ToastMessage(EvpiUserActivity.this,"请上传头像");
            return;
        }

       if(TextUtils.isEmpty(nick)) {
           UIHelper.ToastMessage(EvpiUserActivity.this,"姓名不能为空");
           return;
       }
        if (TextUtils.isEmpty(email)){
           UIHelper.ToastMessage(EvpiUserActivity.this,"邮箱不能为空");
        }

        registerSecNet.registerSec(doctorUUid, email, sex + "", fileString, nick);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_btn:

             //   loadNext();
                intent = new Intent(this,EvpiAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.take_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                File file = new File(photo_take_file_path);
                if (file.exists()) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, TAKE_PICTURE);
                break;
            case R.id.select_local_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, LOCAL_PICTURE);
                break;

            case R.id.back_img:
                finish();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //为什么不在这处理图片呢？因为处理图片比较耗时，如果在这里处理图片，从图库或者拍照Activity时会不流畅，很卡卡卡，试试就知道了
        if (resultCode == NONE)
            return;
        if (requestCode == TAKE_PICTURE) {
            handler.sendEmptyMessage(UPLOAD_TAKE_PICTURE);
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            this.intent = intent;
            handler.sendEmptyMessage(UPLOAD_LOCAL_PICTURE);
        }
    }

    public void uploadUserPhotoNew(final String filePath) {
        uploading_photo_progress.setVisibility(View.VISIBLE);
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

    public void showEditPhotoLayout(View view) {
        edit_photo_fullscreen_layout.setVisibility(View.VISIBLE);
        get_photo_layout_in_from_down = AnimationUtils.loadAnimation(
                this, R.anim.search_layout_in_from_down);
        edit_photo_outer_layout.startAnimation(get_photo_layout_in_from_down);
    }

    public void hideEditPhotoLayout(View view) {
        get_photo_layout_out_from_up = AnimationUtils.loadAnimation(
                this, R.anim.search_layout_out_from_up);
        edit_photo_outer_layout.startAnimation(get_photo_layout_out_from_up);
        get_photo_layout_out_from_up
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // TODO Auto-generated method stub
                        edit_photo_fullscreen_layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    @Override
    public void requestHeadIconNetSuc(HeadIconResult headIconResult) {
        String photoUrl = headIconResult.getSmallUrl();
        Message msg = handler.obtainMessage();
        msg.obj = photoUrl;
        msg.what = SHOW_UPDATE_PHOTO;
        handler.sendMessage(msg);
    }

    @Override
    public void requestHeadIconNetFail() {
        uploading_photo_progress.setVisibility(View.GONE);
        UIHelper.ToastMessage(EvpiUserActivity.this, "上传失败");
    }

    @Override
    public void requestRegisterSecSuc() {
        intent = new Intent(this,EvpiAddressActivity.class);
        startActivity(intent);
    }

    @Override
    public void requestRegisterSecFail() {

    }

}
