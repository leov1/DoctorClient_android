package com.hxqydyl.app.ys.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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


    public interface SavePatientGroupListener {
        void onSaveGroup(String groupName);
    }
}
