package com.hxqydyl.app.ys.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
	/**
	 * 将Bitmap保存为指定文件名的jpg文件
	 * @param photoBitmap 待保存Bitmap对象
	 * @param photoName 保存的文件路径-带文件名
	 * @param picQuality 图片质量
	 */
	public static void savePhoto(Bitmap photoBitmap, String photoName, int picQuality) {
		File photoFile = new File(photoName);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(photoFile);
			if (photoBitmap != null) {
				if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, picQuality, fileOutputStream)) {
					fileOutputStream.flush();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			photoFile.delete();
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按照指定的size进行图片的压缩，
	 * 并将压缩后的图片对象返回
	 * @param sourcePath 源图片
	 * @param maxSize 指定的压缩大小
	 * @return Bitmap
	 */
	public static Bitmap compressBitmap(String sourcePath, float maxSize,int picQuality) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(sourcePath, options);

		final float originalWidth = options.outWidth;
		final float originalHeight = options.outHeight;

		float convertedWidth;
		float convertedHeight;

		if (originalWidth > originalHeight) {
			convertedWidth = maxSize;
			convertedHeight = maxSize * originalHeight / originalWidth;
		} else {
			convertedHeight = maxSize;
			convertedWidth = maxSize * originalWidth / originalHeight;
		}

		final float ratio = originalWidth / convertedWidth;

		options.inSampleSize = (int) ratio;
		options.inJustDecodeBounds = false;

		Bitmap convertedBitmap = BitmapFactory.decodeFile(sourcePath, options);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		convertedBitmap.compress(Bitmap.CompressFormat.JPEG, picQuality, byteArrayOutputStream);

		return convertedBitmap;
	}

	/**
	 * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
	 *
	 * @param context activity
	 * @param imageUri uri
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getImageAbsolutePath(Activity context, Uri imageUri) {
		if (context == null || imageUri == null){
			return null;
		}
		// DocumentProvider
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
				&& DocumentsContract.isDocumentUri(context, imageUri)) {
			if (isExternalStorageDocument(imageUri)) {
				// ExternalStorageProvider
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
			} else if (isDownloadsDocument(imageUri)) {
				// DownloadsProvider
				String id = DocumentsContract.getDocumentId(imageUri);
				Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(imageUri)) {
				// MediaProvider
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				String selection = MediaStore.Images.Media._ID + "=?";
				String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		} else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
			// MediaStore (and general)
			// Return the remote address
			if (isGooglePhotosUri(imageUri)){
				return imageUri.getLastPathSegment();
			}
			return getDataColumn(context, imageUri, null, null);
		} else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
			// File
			return imageUri.getPath();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {
		Cursor cursor = null;
		String column = MediaStore.Images.Media.DATA;
		String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null){
				cursor.close();
			}
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}
}
