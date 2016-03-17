package com.hxqydyl.app.ys.ui.uploadimage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.hxqydyl.app.ys.http.OkHttpClientManager;
import com.hxqydyl.app.ys.http.OkHttpClientManager.Param;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.squareup.okhttp.Request;


public class ImgManage {

	private Activity mActivity;
	private String mUploadImgUrl;
	private Boolean mIsCutImg;
	private ImgUploadListener listener;
	private String picName;
	private String imgUri;

	public ImgManage(Activity activity, String uploadImgUrl, Boolean isCutImg) {
		this.mActivity = activity;
		this.mUploadImgUrl = uploadImgUrl;
		this.mIsCutImg = isCutImg;
	}

	public void getImg(int way) {
		switch (way) {
		case ImgSelectDialog.PIC_FROM_CAMERA:
			openCamera();
			break;

		case ImgSelectDialog.PIC_FROM_LOCALPHOTO:
			openAlbum();
			break;

		default:
			break;
		}
	}

	public void displayImg(int way, Intent data) {
		switch (way) {
		case ImgSelectDialog.PIC_FROM_CAMERA:
			imgUri = Scheme.FILE.wrap(Environment.getExternalStorageDirectory()+"/select-uploadimage/"+picName);
			if (mIsCutImg) {
				cutImg(imgUri);
			}
			
			if (null != mUploadImgUrl && !mIsCutImg) {
				uploadImage(mUploadImgUrl, imgUri);
			}
			break;

		case ImgSelectDialog.PIC_FROM_LOCALPHOTO:
			imgUri = Scheme.FILE.wrap(analyzePhotoPathFromLocalUri(mActivity, data));
			if (mIsCutImg) {
				cutImg(imgUri);
			}
			
			if (null != mUploadImgUrl && !mIsCutImg) {
				uploadImage(mUploadImgUrl, imgUri);
			}
			break;
		
		case ImgSelectDialog.PIC_CUT:
			
			if (null != mUploadImgUrl) {
				uploadImage(mUploadImgUrl, imgUri);
			}
			break;

		default:
			break;
		}
	}

	public interface ImgUploadListener{
		public void uploadResult(String url);
	}

	public void setImgUploadListener(ImgUploadListener listener){
		this.listener = listener;
	}

	//通过拍照获取图片
	private void openCamera() {
		try {
			File picFileFolder = new File(Environment.getExternalStorageDirectory(), "/select-uploadimage");
			if (!picFileFolder.exists()) {
				picFileFolder.mkdirs();
			}

			SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentTime = mFormat.format(System.currentTimeMillis()); 
			picName = currentTime + ".jpeg";

			File picFile = new File(picFileFolder, picName);
			if (!picFile.exists()) {
				picFile.createNewFile();
			}

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
			mActivity.startActivityForResult(intent, ImgSelectDialog.PIC_FROM_CAMERA);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//通过相册获取图片
	private void openAlbum(){
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		mActivity.startActivityForResult(intent, ImgSelectDialog.PIC_FROM_LOCALPHOTO);
	}

	//解析相册图片路径
	private String analyzePhotoPathFromLocalUri(Activity activity, Intent data){
		Uri imageUri = data.getData();
		String[] filePathColumn = {MediaStore.Images.Media.DATA}; 
		Cursor cursor = mActivity.getContentResolver().query(imageUri,filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	//调用裁剪
	private void cutImg(String path) {
		System.out.println("path--->"+path);
		File picFile = new File(path.replace("file://",""));
		if (!picFile.exists()) {
			return;
		}else {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(picFile), "image/*");
			//set params
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 600);
			intent.putExtra("outputY", 600);
		//	intent.putExtra("crop", "true");
		//	intent.putExtra("scale", true);
		//	intent.putExtra("noFaceDetection", true);
			intent.putExtra("return-data", true);
		//	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
		//	intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			mActivity.startActivityForResult(intent, ImgSelectDialog.PIC_CUT);
		}
	}

	//图片上传
	private void uploadImage(String uploadUrl, String imgUri) {
		try {
			Param[] params = new Param[]{new Param("icon", Base64.encode(imgUri.getBytes(),Base64.DEFAULT).toString())};
			OkHttpClientManager.getUploadDelegate().postAsyn(uploadUrl, "upload", new File(imgUri.replace("file://", "")), new OkHttpClientManager.ResultCallback<String>() {
				@Override
				public void onError(Request request, Exception e) {
                   listener.uploadResult(null);
				}

				@Override
				public void onResponse(String response) throws JSONException {
                  listener.uploadResult(response);
				}
			},null);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
