package com.hxqydyl.app.ys.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hxqydyl.app.ys.activity.LoginActivity;
import com.hxqydyl.app.ys.activity.MainActivity;
import com.hxqydyl.app.ys.activity.register.RegisterActivity;
import com.hxqydyl.app.ys.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

	public final static String TAG = "UIHelper";

	public final static int RESULT_OK = 0x00;
	public final static int REQUEST_CODE = 0x99;
    public final static int LOGIN_REQUEST_CODE = 0x98;
    public final static int LOGINOUT_REQUEST_CODE = 0x97;

	public static void ToastMessage(Context cont, String msg) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
        if(cont == null || msg <= 0) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, time).show();
	}

    public static void showHome(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showLogin(Activity context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showRegister(Activity context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    public static void showLoginForResult(final  Activity context,boolean isBtn){
        if (isBtn){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("isNeedCallBack",true);
            context.startActivityForResult(intent,LOGIN_REQUEST_CODE);
        }else{
            DialogUtils.showNormalDialog(context,"登录才能使用该功能","登录","取消",new SweetAlertDialog.OnSweetClickListener(){

                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("isNeedCallBack",true);
                    context.startActivityForResult(intent,LOGIN_REQUEST_CODE);
                    sweetAlertDialog.dismiss();
                }
            });
        }


    }
}
