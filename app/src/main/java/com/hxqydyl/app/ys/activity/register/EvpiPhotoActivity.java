package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.activity.register.listener.RegisterSucListener;
import com.hxqydyl.app.ys.activity.register.listener.RegisterSucMag;
import com.hxqydyl.app.ys.adapter.EvpiPhotoAdapter;
import com.hxqydyl.app.ys.adapter.ImagePagerAdapter;
import com.hxqydyl.app.ys.bean.Query;
import com.hxqydyl.app.ys.bean.register.IconBean;
import com.hxqydyl.app.ys.bean.register.ImageItem;
import com.hxqydyl.app.ys.bean.register.TagsBean;
import com.hxqydyl.app.ys.http.MyInterface.OnSingleTapDismissBigPhotoListener;
import com.hxqydyl.app.ys.http.register.SaveUserIconNet;
import com.hxqydyl.app.ys.http.register.UploadIconsNet;
import com.hxqydyl.app.ys.ui.MyViewPager;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.scrollviewandgridview.MyGridView;
import com.hxqydyl.app.ys.ui.uploadimage.UploadPhotoUtil;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 完善注册z照片信息
 */
public class EvpiPhotoActivity extends BaseTitleActivity implements View.OnClickListener,OnSingleTapDismissBigPhotoListener,UploadIconsNet.OnUploadIconsListener ,SaveUserIconNet.OnSaveUserIconListener{

    private String takePictureUrl;
    private int addTakePicCount = 1;
    private Intent intent;
    private int NONE = 0,TAKE_PICTURE = 1, LOCAL_PICTURE = 2;
    private final int SHOW_TAKE_PICTURE = 9;
    private final int SHOW_LOCAL_PICTURE = 10;
    private List<String> uploadImgUrlList = new ArrayList<String>();
    public ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();//选择的图片临时列表
    private int viewpagerPosition;
    private ImagePagerAdapter imagePagerAdapter;
    private boolean isBigImageShow = false;

    private MyGridView gridView;
    private EvpiPhotoAdapter adapter;

    private Animation get_photo_layout_out_from_up, get_photo_layout_in_from_down;
    private RelativeLayout edit_photo_fullscreen_layout,display_big_image_layout,edit_photo_outer_layout;
    private TextView take_picture;
    private TextView select_local_picture;
    private TextView cancel;
    private Button registerBtn;

    private MyViewPager image_viewpager;
    private TextView position_in_total;
    private ImageView delete_image;

    private UploadIconsNet uploadIconsNet;
    private SaveUserIconNet saveUserIconNet;

    File sdcardDir = Environment.getExternalStorageDirectory();
    private String photo_path = sdcardDir.getPath() + "/hxq/cache/photoes/";

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
        cancel = (TextView) findViewById(R.id.cancel);
        registerBtn = (Button) findViewById(R.id.register_btn);

        display_big_image_layout = (RelativeLayout) findViewById(R.id.display_big_image_layout);
        image_viewpager = (MyViewPager) findViewById(R.id.image_viewpager);
        position_in_total = (TextView) findViewById(R.id.position_in_total);
        delete_image = (ImageView) findViewById(R.id.delete_image);

        gridView = (MyGridView) findViewById(R.id.gridview);
        adapter = new EvpiPhotoAdapter(this,tempSelectBitmap);
        gridView.setAdapter(adapter);
    }

    private void initListeners() {
        setBackListener(this);
        take_picture.setOnClickListener(this);
        select_local_picture.setOnClickListener(this);
        delete_image.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == tempSelectBitmap.size()) {
                    edit_photo_fullscreen_layout.setVisibility(View.VISIBLE);
                    get_photo_layout_in_from_down = AnimationUtils.loadAnimation(EvpiPhotoActivity.this, R.anim.search_layout_in_from_down);
                    edit_photo_outer_layout.startAnimation(get_photo_layout_in_from_down);
                } else {
                    //点击图片查看大图
                    showImageViewPager(position, uploadImgUrlList);
                    viewpagerPosition = position;
                }
            }
        });
    }

    public void showImageViewPager(int position, final List<String> localUrlList) {
        List<String> urlList;
            urlList = localUrlList;
        Log.d("gaolei", "urlList.toString()------------------" + urlList.toString());
        display_big_image_layout.setVisibility(View.VISIBLE);
        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urlList);
        image_viewpager.setAdapter(imagePagerAdapter);
        imagePagerAdapter.notifyDataSetChanged();
        image_viewpager.setOffscreenPageLimit(imagePagerAdapter.getCount());
        image_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                viewpagerPosition = position;
                position_in_total.setText((position + 1) + "/" + localUrlList.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
        });
            image_viewpager.setCurrentItem(position);
            delete_image.setVisibility(View.VISIBLE);
            position_in_total.setText((position+1) + "/" + urlList.size());
            isBigImageShow = true;

        com.hxqydyl.app.ys.ui.imageDisplayFragment.PhotoViewAttacher.setOnSingleTapToPhotoViewListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.take_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                takePictureUrl = photo_path + "take_pic" + addTakePicCount + ".png";
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
            case R.id.delete_image:
                if (uploadImgUrlList.size() == 0) {
                    return;
                }
                uploadImgUrlList.remove(viewpagerPosition);
                tempSelectBitmap.remove(viewpagerPosition);
                imagePagerAdapter.changeList(uploadImgUrlList);
                adapter.update(tempSelectBitmap);
                position_in_total.setText((viewpagerPosition + 1) + "/" + uploadImgUrlList.size());
                if (uploadImgUrlList.size() == 0) {
                    display_big_image_layout.setVisibility(View.GONE);
                    isBigImageShow = false;
                }
                break;
            case R.id.register_btn:
                uploadIcoms();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;

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
                    Bitmap bitmap = UploadPhotoUtil.getInstance().trasformToZoomBitmapAndLessMemory(takePictureUrl);
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bitmap);
                    tempSelectBitmap.add(takePhoto);
                    adapter.update(tempSelectBitmap);
                    uploadImgUrlList.add(takePictureUrl);
                    break;

                case SHOW_LOCAL_PICTURE:
                    Uri uri = intent.getData();
                    String[] pojo = {MediaStore.Images.Media.DATA};

                    CursorLoader cursorLoader = new CursorLoader(EvpiPhotoActivity.this, uri, pojo, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    cursor.moveToFirst();
                    String photo_local_file_path = cursor.getString(cursor.getColumnIndex(pojo[0]));
                    Bitmap bitmap2 = UploadPhotoUtil.getInstance()
                            .trasformToZoomBitmapAndLessMemory(photo_local_file_path);
                    ImageItem takePhoto1 = new ImageItem();
                    takePhoto1.setBitmap(bitmap2);
                    tempSelectBitmap.add(takePhoto1);
                    uploadImgUrlList.add(photo_local_file_path);
                    adapter.update(tempSelectBitmap);
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

    @Override
    public void onDismissBigPhoto() {
        hideDisplayBigImageLayout();
    }

    private void hideDisplayBigImageLayout() {
        display_big_image_layout.setVisibility(View.GONE);
        isBigImageShow = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
           if(isBigImageShow){
                hideDisplayBigImageLayout();
                return false;
            }
        }
        finish();
        return false;
    }

    /**
     * 上传图片，然后在注册
     */
    private void uploadIcoms(){
        if (uploadImgUrlList.size() == 0) {
            UIHelper.ToastMessage(EvpiPhotoActivity.this,"请上传图片");
            return;
        }
        showDialog("请稍等...");
        uploadIconsNet.saveIcons(uploadImgUrlList);
        System.out.println("list --->" + uploadImgUrlList.toString());
    }

    @Override
    public void requestUploadIconsSuc(ArrayList<IconBean> iconBeans) {
        if (iconBeans == null) return;

        saveUserIconNet.saveUserIcon(LoginManager.getDoctorUuid(), StringUtils.listToString(uploadImgUrlList, ','));
 //       UIHelper.ToastMessage(EvpiPhotoActivity.this,query.getMessage());
    }

    @Override
    public void requestUploadIconsFail() {
            dismissDialog();
    }

    @Override
    public void requestSaveIconSuc(Query query) {
        dismissDialog();
        if(query == null){
            UIHelper.ToastMessage(EvpiPhotoActivity.this,"请求出错");
            return;
        }
        if (query.getSuccess().equals("1")){
            UIHelper.ToastMessage(EvpiPhotoActivity.this,"注册成功");
            removeBeforViews();
            finish();
        }else{
            UIHelper.ToastMessage(EvpiPhotoActivity.this,query.getMessage());
        }

    }

    /**
     * 观察者移除之前页面
     */
    private void removeBeforViews(){
        ArrayList<RegisterSucListener> registerSucListeners = RegisterSucMag.getInstance().downloadListeners;
        if (registerSucListeners == null || registerSucListeners.size() == 0)return;
        for (int i = 0;i<registerSucListeners.size();i++){
            registerSucListeners.get(i).onRegisterSuc();
        }
    }

    @Override
    public void requestSaveIconFail() {
      dismissDialog();
    }

}
