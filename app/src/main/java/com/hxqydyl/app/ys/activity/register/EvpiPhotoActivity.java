package com.hxqydyl.app.ys.activity.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.EvpiPhotoAdapter;
import com.hxqydyl.app.ys.bean.register.Bimp;
import com.hxqydyl.app.ys.bean.register.ImageItem;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyGridView;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.ui.uploadimage.ImgManage;
import com.hxqydyl.app.ys.ui.uploadimage.ImgSelectDialog;
import com.hxqydyl.app.ys.utils.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 完善注册z照片信息
 */
public class EvpiPhotoActivity extends BaseTitleActivity implements View.OnClickListener {

    private MyGridView gridView;
    private EvpiPhotoAdapter adapter;

    private ImgManage imgManage;
    private ImgSelectDialog dialog;
    private Boolean isCutImg = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_evpi);

        initViews();
        initListeners();
    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        imgManage = new ImgManage(EvpiPhotoActivity.this, Constants.UPLOAD_IMAGE,isCutImg);

        gridView = (MyGridView) findViewById(R.id.gridview);
        adapter = new EvpiPhotoAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void initListeners() {
        setBackListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.tempSelectBitmap.size()) {
                    showDialog();
                } else {
                    UIHelper.ToastMessage(EvpiPhotoActivity.this, "--" + position);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }

    //提示对话框方法
    private void showDialog() {
        dialog = new ImgSelectDialog(EvpiPhotoActivity.this, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case ImgSelectDialog.PIC_FROM_CAMERA:
                        imgManage.getImg(ImgSelectDialog.PIC_FROM_CAMERA);
                        break;

                    case ImgSelectDialog.PIC_FROM_LOCALPHOTO:
                        imgManage.getImg(ImgSelectDialog.PIC_FROM_LOCALPHOTO);
                        break;

                    case ImgSelectDialog.CANCEL:
                        imgManage.getImg(ImgSelectDialog.PIC_CUT);
                        break;

                    default:
                        break;
                }
            }
        });

       dialog.Create();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImgSelectDialog.PIC_FROM_CAMERA:
                imgManage.displayImg(ImgSelectDialog.PIC_FROM_CAMERA, data);

                    imgManage.setImgUploadListener(new ImgManage.ImgUploadListener() {

                        @Override
                        public void uploadResult(String url) {
                            if (null == url) {
                                Toast.makeText(EvpiPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EvpiPhotoActivity.this, "图片地址："+url, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                break;

            case ImgSelectDialog.PIC_FROM_LOCALPHOTO:
                imgManage.displayImg(ImgSelectDialog.PIC_FROM_LOCALPHOTO, data);
                    imgManage.setImgUploadListener(new ImgManage.ImgUploadListener() {

                        @Override
                        public void uploadResult(String url) {
                            if (null == url) {
                                Toast.makeText(EvpiPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EvpiPhotoActivity.this, "图片地址："+url, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                break;

            case ImgSelectDialog.PIC_CUT:
                setPicToView(data);
                imgManage.setImgUploadListener(new ImgManage.ImgUploadListener() {

                        @Override
                        public void uploadResult(String url) {
                            if (null == url) {
                                Toast.makeText(EvpiPhotoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EvpiPhotoActivity.this, "图片地址："+url, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                break;
            default:
                break;
        }
    }

    //将进行剪裁后的图片显示到UI界面上
    @SuppressWarnings("deprecation")
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(photo);
            Bimp.tempSelectBitmap.add(takePhoto);
        }
        adapter.update();
    }

}
