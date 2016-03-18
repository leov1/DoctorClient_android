package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.EvpiPhotoAdapter;
import com.hxqydyl.app.ys.bean.register.Bimp;
import com.hxqydyl.app.ys.bean.register.ImageItem;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyGridView;
import com.hxqydyl.app.ys.ui.uploadimage.UploadPhotoUtil;
import com.hxqydyl.app.ys.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
/**
 * 完善注册z照片信息
 */
public class EvpiPhotoActivity extends BaseTitleActivity implements View.OnClickListener {

    private MyGridView gridView;
    private EvpiPhotoAdapter adapter;

    private Boolean isCutImg = true;

    private Animation get_photo_layout_out_from_up, get_photo_layout_in_from_down;
    private RelativeLayout edit_photo_fullscreen_layout;
    private RelativeLayout edit_photo_outer_layout;
    private TextView take_picture;
    private TextView select_local_picture;
    private TextView cancel;

    File sdcardDir = Environment.getExternalStorageDirectory();
    private String photo_path = sdcardDir.getPath() + "/Gosu/cache/photoes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_evpi);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        edit_photo_fullscreen_layout = (RelativeLayout) findViewById(R.id.edit_photo_fullscreen_layout);
        edit_photo_outer_layout = (RelativeLayout) findViewById(R.id.edit_photo_outer_layout);
        take_picture = (TextView) findViewById(R.id.take_picture);
        select_local_picture = (TextView) findViewById(R.id.select_local_picture);
        cancel = (TextView) findViewById(R.id.cancel);

        gridView = (MyGridView) findViewById(R.id.gridview);
        adapter = new EvpiPhotoAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void initListeners() {
        setBackListener(this);
        take_picture.setOnClickListener(this);
        select_local_picture.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.tempSelectBitmap.size()) {
                    edit_photo_fullscreen_layout.setVisibility(View.VISIBLE);
                    get_photo_layout_in_from_down = AnimationUtils.loadAnimation(EvpiPhotoActivity.this,R.anim.search_layout_in_from_down);
                    edit_photo_outer_layout.startAnimation(get_photo_layout_in_from_down);
                } else {
                    UIHelper.ToastMessage(EvpiPhotoActivity.this, "--" + position);
                }
            }
        });
    }

    private String takePictureUrl;
    private int addTakePicCount = 1;
    private Intent intent;
    private int TAKE_PICTURE = 1, LOCAL_PICTURE = 2;
    private final int SHOW_TAKE_PICTURE = 9;
    private final int SHOW_LOCAL_PICTURE = 10;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.take_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                takePictureUrl = photo_path + "take_pic"
                        + addTakePicCount + ".png";
                File file = new File(takePictureUrl);
                if (file.exists()) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, TAKE_PICTURE);
                addTakePicCount++;
                break;
            case R.id.select_local_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, LOCAL_PICTURE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            handler.sendEmptyMessage(SHOW_TAKE_PICTURE);
            return;
        }

        if (resultCode == Activity.RESULT_OK) {
            this.intent = data;
            handler.sendEmptyMessage(SHOW_LOCAL_PICTURE);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TAKE_PICTURE:
                    Bitmap bitmap = UploadPhotoUtil.getInstance()
                            .trasformToZoomBitmapAndLessMemory(takePictureUrl);
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bitmap);
                    Bimp.tempSelectBitmap.add(takePhoto);
                    adapter.update();
                    break;

                case SHOW_LOCAL_PICTURE:
                    Uri uri = intent.getData();
                    String[] pojo = {MediaStore.Images.Media.DATA};

                    CursorLoader cursorLoader = new CursorLoader(EvpiPhotoActivity.this, uri, pojo, null,null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    cursor.moveToFirst();
                    String photo_local_file_path = cursor.getString(cursor.getColumnIndex(pojo[0]));
                    Bitmap bitmap2 = UploadPhotoUtil.getInstance()
                            .trasformToZoomBitmapAndLessMemory(photo_local_file_path);
                    ImageItem takePhoto1 = new ImageItem();
                    takePhoto1.setBitmap(bitmap2);
                    Bimp.tempSelectBitmap.add(takePhoto1);
                    adapter.update();

                    break;
            }
        }
    };

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
}
