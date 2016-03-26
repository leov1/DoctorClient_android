package com.hxqydyl.app.ys.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/25.
 */
public class GetPicUtils implements DialogUtils.GetPicWaySelectedListener{
    public static final int CAMERA = 0xff;
    public static final int PHOTO_ALBUM = 0xfe;
    // 图片保存品质
    private int picQuality = 80;
    // 图片大小
    private float picSize = 720.0f;
    // 缩略图片大小
    private final float thumbPicSize = 150.0f;

    private Activity activity;
    private Uri picUri;
    private int picGetWay;
    private String picPath;
    private String picThumbPath;

    public GetPicUtils(Activity activity){
        this.activity = activity;
    }

    /**
     * 获取处理完成之后的图片路径
     * 该方法仅在onActivity调用完成以后调用
     * @return String
     */
    public String getPicPath(){
        return picPath;
    }

    /**
     * 获取处理完成之后的图片的缩略图
     * 该方法仅在onActivity调用完成以后调用
     * @return String
     */
    public String getPicThumbPath(){
        return picThumbPath;
    }

    /**
     * 获取所拍照片的uri
     * @return
     */
    public Uri getPicUri() {
        return picUri;
    }

    /**
     * 调用该方法会自动调用系统的拍照或者相册选取程序
     * activity需要自行复写onActivityResult，针对resultCode(结果码)为RESULT_OK并且requestCode(请求码)为CAMERA和PHOTO_ALBUM的情况进行处理
     * 也可直接调用或参考本类提供的onActivityResult(int requestCode,int resultCode,Intent data)方法处理后续
     */
    public void getPic(){
        DialogUtils.showSelcetGetPicWayDialog(activity,this);
    }

    /**
     * 跳转相机
     */
    private void toCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = getPicOutFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        activity.startActivityForResult(intent, CAMERA);
    }

    /**
     * 跳转到相册
     */
    private void toAlbum() {
        picUri = null;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, PHOTO_ALBUM);
    }

    /**
     * 根据将要存储相片的位置创建file对象,并返回对应uri
     * @return File
     */
    private Uri getPicOutFileUri() {
        File mediaStorageDir = null;
        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_REMOVED.equals(storageState)){
            mediaStorageDir = new File(Environment.getRootDirectory(),"MyPictures");
        }else{
            mediaStorageDir = new File (Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    ,"MyPictures");
        }
        if (!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                throw new NullPointerException("file");
            }
        }
        File outFile = new File(getFilePath(mediaStorageDir));
        return Uri.fromFile(outFile);
    }

    /**
     * 生成输出文件路径
     * @return String 创建的文件路径
     */
    private String getFilePath(File mediaStorageDir){
        String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String filePath = mediaStorageDir.getPath() + File.separator;
        filePath += ("IMG_" + timeStamp + ".jpg");

        return filePath;
    }

    @Override
    public void onWaySelected(int way) {
        switch(way){
            case CAMERA:
                picGetWay = CAMERA;
                toCamera();
                break;
            case PHOTO_ALBUM:
                picGetWay = PHOTO_ALBUM;
                toAlbum();
                break;
        }
    }

    /**
     * 在拍照或者选取照片完成以后调用
     * 默认保存图片最大边界为720 (picSize)
     * 默认保存图片缩略图最大边界为150 (thumbPicSize)
     * 默认压缩图片质量为80 (picQuality)
     * 以上三个属性可以在调用本方法之前调用set方法进行设置
     * 默认保存图片格式为jpg
     * 对照片的处理完成后，可通过getPicPath()与getPicThumbPath()分别获取
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if (null == picUri && null!=data) {
            picUri = data.getData();
        }
        if(picUri == null){
            return;
        }
        // 拍照或相册中文件的路径
        String picAbsolutePath = ImageUtils.getImageAbsolutePath(activity,picUri);
        Bitmap bitmap = ImageUtils.compressBitmap(picAbsolutePath,picSize,picQuality);
        Bitmap thumbBitmap = ImageUtils.compressBitmap(picAbsolutePath,thumbPicSize,picQuality);;

        // 将获得的照片文件备份保存到自己应用的目录
        File picPath = activity.getFilesDir();
        // 将要保存的文件路径-包含文件名
        String picName = getFilePath(picPath);
        String thumbPicName = picName.replace(".jpg","_THUMB.jpg");
        // 保存照片到SD卡（大图和缩略图）
        ImageUtils.savePhoto(bitmap, picName, picQuality);
        ImageUtils.savePhoto(bitmap,thumbPicName,picQuality);
        // 将拍照生成的图片删除
        if (picGetWay == CAMERA) {
            if(StringUtils.isEmpty(picAbsolutePath)){
                return;
            }
            File file = new File(picAbsolutePath);
            if (file.isFile()) {
                file.delete();
            }
        }
    }
}
