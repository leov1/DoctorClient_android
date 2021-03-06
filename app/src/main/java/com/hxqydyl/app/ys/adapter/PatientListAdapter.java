package com.hxqydyl.app.ys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.Patient;
import com.hxqydyl.app.ys.bean.PatientGroup;
import com.hxqydyl.app.ys.utils.DensityUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/21.
 */
public class PatientListAdapter extends BaseExpandableListAdapter {
    public static final int MOVE = 1;//移动
    public static final int DELETE = 2; // 删除
    private DisplayImageOptions options;
    private Context context;
    private ArrayList<PatientGroup> patientGroups;
    private SwipeLayout currentExpandedSwipeLayout;
    private OnChildClickListener listener;
    private boolean isClick = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isClick = true;
        }
    };

    public void setOnClildClickListener(OnChildClickListener listener) {
        this.listener = listener;
    }


    public PatientListAdapter(Context context, ArrayList<PatientGroup> patientGroups) {
        this.context = context;
        this.patientGroups = patientGroups;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(DensityUtils.dp2px(context, 50)))
                .showImageForEmptyUri(R.mipmap.portrait_man)
                .showImageOnFail(R.mipmap.portrait_man)
                .build();
    }


    @Override
    public int getGroupCount() {
        return patientGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return patientGroups.get(groupPosition).getCustomers().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return patientGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return patientGroups.get(groupPosition).getCustomers().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_list_group_item, null);
        }
        ImageView ivExpandFlag = BaseViewHolder.get(convertView, R.id.ivExpandFlag);
        if (isExpanded) {
            ivExpandFlag.setImageResource(R.mipmap.icon_list_group_expand);
        } else {
            ivExpandFlag.setImageResource(R.mipmap.icon_list_group_collapse);
        }
        final PatientGroup patientGroup = (PatientGroup) getGroup(groupPosition);
        TextView tvGroupName = BaseViewHolder.get(convertView, R.id.tvGroupName);
        tvGroupName.setText(patientGroup.getGroupName() + "");
        TextView tvMemberNum = BaseViewHolder.get(convertView, R.id.tvMemberNum);
        tvMemberNum.setText(String.format(context.getString(R.string.member_num_format), getChildrenCount(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_list_child_item, null);
        }
        final Patient patient = (Patient) getChild(groupPosition, childPosition);
        ImageView ivPatientPortrait = BaseViewHolder.get(convertView, R.id.ivPatientPortrait);
        ImageLoader.getInstance().displayImage(patient.getCustomerImg(), ivPatientPortrait, options);

        TextView tvPatientName = BaseViewHolder.get(convertView, R.id.tvPatientName);
        tvPatientName.setText(patient.getCustomerName());

        ImageView ivSexFlag = BaseViewHolder.get(convertView, R.id.ivSexFlag);
        if (TextUtils.isEmpty(patient.getSex())) {
            ivSexFlag.setVisibility(View.GONE);
        } else {
            ivSexFlag.setVisibility(View.VISIBLE);
            if ("女".equals(patient.getSex())) {
                ivSexFlag.setImageResource(R.mipmap.icon_woman_flag);
            } else {
                ivSexFlag.setImageResource(R.mipmap.icon_man_flag);
            }
        }

        TextView tvPatientAge = BaseViewHolder.get(convertView, R.id.tvPatientAge);
        if (TextUtils.isEmpty(patient.getAge())) {
            tvPatientAge.setVisibility(View.GONE);
        } else {
            tvPatientAge.setText(String.format(context.getString(R.string.age_xx), patient.getAge()));
            tvPatientAge.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(patient.getCustomerUuid())){
            tvPatientAge.setVisibility(View.VISIBLE);
            tvPatientAge.setText("未注册");
        }

        TextView tvPatientFollowTime = BaseViewHolder.get(convertView, R.id.tvPatientFollowTime);

        if (TextUtils.isEmpty(patient.getApplyTime())) {
            tvPatientFollowTime.setVisibility(View.GONE);
        } else {
            tvPatientFollowTime.setText(patient.getApplyTime());
            tvPatientFollowTime.setVisibility(View.VISIBLE);
        }
        TextView tvDescription = BaseViewHolder.get(convertView, R.id.tvDescription);

        if (TextUtils.isEmpty(patient.getCustomerMessage())) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText("问题："+patient.getCustomerMessage());
        }

        ImageView ivPatientPro = BaseViewHolder.get(convertView,R.id.ivPatientPro);
        if (TextUtils.isEmpty(patient.getVisitPreceptUuid())){
            ivPatientPro.setVisibility(View.GONE);
        }else {
            ivPatientPro.setVisibility(View.VISIBLE);
        }

        ImageView ivMoveToOtherGroup = BaseViewHolder.get(convertView, R.id.ivMoveToOtherGroup);
        ivMoveToOtherGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMenuClick(groupPosition, childPosition, MOVE);
                }
            }
        });
        ImageView ivDeletePatient = BaseViewHolder.get(convertView, R.id.ivDeletePatient);
        ivDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMenuClick(groupPosition, childPosition, DELETE);
                }
            }
        });
        final SwipeLayout swipeLayout = BaseViewHolder.get(convertView, R.id.swipLayout);

        if (TextUtils.isEmpty(patient.getCustomerUuid())){
            swipeLayout.setSwipeEnabled(false);
            swipeLayout.setEnabled(false);
        }else{
            swipeLayout.setSwipeEnabled(true);
            swipeLayout.setEnabled(true);
        }

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, BaseViewHolder.get(convertView, R.id.llSwipMenu));
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                if (currentExpandedSwipeLayout != null && currentExpandedSwipeLayout != layout)
                    currentExpandedSwipeLayout.close(true);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                currentExpandedSwipeLayout = layout;
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                isClick = false;
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 200);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
            }
        });
        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick) {
                    if (listener != null) {
                        listener.onChildClick(groupPosition, childPosition);
                    }
                }
            }
        });
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnChildClickListener {
        void onChildClick(int groupPosition, int childPosition);

        void onMenuClick(int groupPosition, int childPosition, int menu);
    }

    public class GroupViewHolder {

    }


}
