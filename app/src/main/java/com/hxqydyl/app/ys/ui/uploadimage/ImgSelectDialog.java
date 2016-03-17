package com.hxqydyl.app.ys.ui.uploadimage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hxqydyl.app.ys.R;


public class ImgSelectDialog{
	
	public final static int PIC_FROM_CAMERA = 0;
	public final static int PIC_FROM_LOCALPHOTO = 1;
	public final static int CANCEL = 2;
	public final static int PIC_CUT = 3;
	
	private Context mContext;
	private DialogInterface.OnClickListener mOnClickListener;
	private AlertDialog imgSelectDialog;

	public ImgSelectDialog(Context context, DialogInterface.OnClickListener onClickListener) {
		this.mContext = context;
		this.mOnClickListener = (DialogInterface.OnClickListener) onClickListener;
	}
	
	public void Create() {
		imgSelectDialog = new AlertDialog.Builder(mContext)
		.setIcon(R.mipmap.ic_dialog_info)
		.setTitle("«Î—°‘Ò")
		.setItems(R.array.PicSelectDialogItem, mOnClickListener)
		.create();
		imgSelectDialog.show();
	}

	public void Dismiss(){
		imgSelectDialog.dismiss();
	}
}
