package com.hxqydyl.app.ys.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by white_ash on 2016/3/20.
 */
public class DialogUtils {
    /**
     *
     */
    public static void showDeleteDialog(Context context,final DeleteListener deleteListener,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.prompt));
        builder.setMessage(message);
        builder.setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(deleteListener!=null){
                    deleteListener.onConfirmDelete();
                }
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(deleteListener!=null){
                    deleteListener.onCancelDelete();
                }
            }
        });
        builder.create().show();
    }

    public interface DeleteListener{
        void onConfirmDelete();
        void onCancelDelete();
    }



    /**
     * 显示重命名分组的对话框
     * @param context
     * @param renameGroupListener
     */
    public static void showRenamePatientGroupDialog(Context context, final RenameGroupListener renameGroupListener,final String groupId,String groupName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_patient_group, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(context.getString(R.string.rename_group));
        final EditText et = (EditText) view.findViewById(R.id.etGroupName);
        et.setText(groupName);
        Button b = (Button) view.findViewById(R.id.bSaveGroup);
        b.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = et.getText().toString();
                if (!TextUtils.isEmpty(groupName)) {
                    dialog.dismiss();
                    if (renameGroupListener != null) {
                        renameGroupListener.onConfirmRename(groupId,groupName);
                    }
                } else {
                }
            }
        });
        dialog.show();
    }

    public interface RenameGroupListener{
        void onConfirmRename(String groupId,String groupName);
    }

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

    /**
     * 弹窗，选择周期
     * @param tv
     * @param items
     */
    public static void cycleDialog(Context context, final TextView tv, final String[] items) {
        final NormalListDialog dialog = new NormalListDialog(context, items);
        dialog.title("请选择周期")
                .titleBgColor(context.getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(items[position]);
                dialog.dismiss();
            }
        });
    }

    /**
     * 添加其他检查项周期dialog
     * @param context
     * @param saveCheckSycleListener
     */
    public static void showAddCheckSycleDialog(final Context context, final SaveCheckSycleListener saveCheckSycleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_check_sycle, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        final EditText etName = (EditText) view.findViewById(R.id.etName);
        final ImageButton ibSelectSycle = (ImageButton) view.findViewById(R.id.ibSelectSycle);
        final LinearLayout llSelectSycle = (LinearLayout) view.findViewById(R.id.llSelectSycle);
        final TextView tvSycle = (TextView) view.findViewById(R.id.tvSycle);
        TextView tvSycle1 = (TextView) view.findViewById(R.id.tvSycle1);
        TextView tvSycle2 = (TextView) view.findViewById(R.id.tvSycle2);
        TextView tvSycle4 = (TextView) view.findViewById(R.id.tvSycle4);
        TextView tvSycle8 = (TextView) view.findViewById(R.id.tvSycle8);
        TextView tvOk = (TextView) view.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        View.OnClickListener sycleClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scyle = ((TextView)v).getText().toString();
                tvSycle.setText(scyle);
                llSelectSycle.setVisibility(View.GONE);
                ibSelectSycle.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_down_arrow));
            }
        };
        tvSycle1.setOnClickListener(sycleClick);
        tvSycle2.setOnClickListener(sycleClick);
        tvSycle4.setOnClickListener(sycleClick);
        tvSycle8.setOnClickListener(sycleClick);
        ibSelectSycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llSelectSycle.getVisibility() == View.VISIBLE) {
                    llSelectSycle.setVisibility(View.GONE);
                    ibSelectSycle.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_down_arrow));
                } else {
                    llSelectSycle.setVisibility(View.VISIBLE);
                    ibSelectSycle.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_up_arrow));
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (StringUtils.isEmpty(name)) {
                    Toast.makeText(context, "请输入检查项名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sycle = tvSycle.getText().toString();
                boolean result = saveCheckSycleListener.save(name, sycle);
                if (result) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public interface SaveCheckSycleListener {
        boolean save(String name, String sycle);
    }

    /**
     * 仅有一个输入框的
     * @param context
     * @param listener
     */
    public static void showSignleEditTextDialog(final Context context, final SaveTextListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_text, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        final EditText etName = (EditText) view.findViewById(R.id.etName);
        TextView tvOk = (TextView) view.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (StringUtils.isEmpty(name)) {
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean result = listener.save(name);
                if (result) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    public interface SaveTextListener {
        boolean save(String text);
    }

    public static void showNormalDialog(Context context) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("恭喜您成功注册好心情医生版!")
                .setContentText("您可以点击头像或个人中心进行认证。")
                .setConfirmText("确认")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();

    }
    public static void showNormalDialog(Context context,String title,String content) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确认")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        pDialog.show();

    }
    public static void showNormalDialog(Context context,String title,String content,SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确认")
                .showCancelButton(true)
                .setConfirmClickListener(onSweetClickListener);
        pDialog.show();

    }
}
