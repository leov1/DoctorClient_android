package com.hxqydyl.app.ys.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.PatientGroup;

/**
 * Created by white_ash on 2016/3/20.
 */
public class DialogUtils {
    /**
     * 显示添加患者分组的对话框
     * @param context
     * @param savePatientGroupListener
     */
    public static void showAddPatientGroupDialog(Context context, final SavePatientGroupListener savePatientGroupListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_patient_group, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        final EditText et = (EditText) view.findViewById(R.id.etGroupName);
        Button b = (Button) view.findViewById(R.id.bSaveGroup);
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = et.getText().toString();
                if (!TextUtils.isEmpty(groupName)) {
                    dialog.dismiss();
                    if (savePatientGroupListener != null) {
                        savePatientGroupListener.onSaveGroup(groupName);
                    }
                } else {
                }
            }
        });
        dialog.show();
    }

    /**
     * 显示选择拍照或者从相册选取的对话框
     * @param context
     * @param listener
     */
    public static void showSelcetGetPicWayDialog(Context context,final GetPicWaySelectedListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.select_pic_source))
                .setItems(new String[]{context.getString(R.string.camera), context.getString(R.string.photo_album)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = -1;
                        switch (which) {
                            case 0:
                                result = GetPicUtils.CAMERA;
                                break;
                            case 1:
                                result = GetPicUtils.PHOTO_ALBUM;
                                break;
                            default:
                                break;
                        }
                        if (result != -1 && listener != null) {
                            listener.onWaySelected(result);
                        }
                    }
                }).show();
    }

    public interface SavePatientGroupListener {
        void onSaveGroup(String groupName);
    }

    public interface GetPicWaySelectedListener{
        void onWaySelected(int way);
    }
}
