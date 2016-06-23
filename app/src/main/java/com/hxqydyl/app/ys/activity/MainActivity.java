
package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.bean.response.BaseResponse;
import com.hxqydyl.app.ys.fragment.HomePageFrg;
import com.hxqydyl.app.ys.fragment.MyPatientFrg;
import com.hxqydyl.app.ys.fragment.MyTaskFrg;
import com.hxqydyl.app.ys.fragment.PersonalFrg;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import common.AppManager;

public class MainActivity extends BaseRequstActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    @InjectId(id = R.id.group)
    private RadioGroup group;
    @InjectId(id = R.id.textUnreadLabel2)
    private TextView poi;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    private static boolean isQuit = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectUtils.injectView(this);
        addRegisterListener(this);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            initData();
            initView();
        } else {
            initFromSavedInstantsState(savedInstanceState);
        }
        Update.getIncetence(this).cheackIsUp();
        getIconRdePoi();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
        outState.putStringArrayList(FRAGMENT_TAGS, fragmentTags);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initFromSavedInstantsState(savedInstanceState);
    }

    private void initFromSavedInstantsState(Bundle savedInstanceState) {
        currIndex = savedInstanceState.getInt(CURR_INDEX);
        fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);
        showFragment();
    }

    private void initData() {
        currIndex = 0;
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "PatientFragment", "UserFragment"));// "TaskFragment",
    }

    private void initView() {

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (((RadioButton) findViewById(checkedId)).isChecked()) {
                    switch (checkedId) {
                        case R.id.foot_bar_home:
                            chageIndex(0);
                            break;
                        case R.id.foot_bar_im:
                            chageIndex(1);
                            break;
                      /*  case R.id.foot_bar_interest:
                            chageIndex(2);
                            break;*/
                        case R.id.main_footbar_user:
                            chageIndex(2);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        showFragment();
    }

    public void chageIndex(int index) {

        if ((!LoginManager.isHasLogin()) && (index == 1 || index == 2)) {
            UIHelper.showLoginForResult(this, false);
            checkGroup(currIndex);
            return;
        }
        //   if (index == 1 && (!TextUtils.isEmpty(isCompleteInfo()))) {
//        if(index == 1){
//            UIHelper.ToastMessage(this, "该功能即将开通，敬请期待。");
//            checkGroup(currIndex);
//            return;
//        }

        currIndex = index;
        showFragment();
    }

    private void checkGroup(int idex) {
        switch (idex) {
            case 0:
                group.check(R.id.foot_bar_home);
                break;
            case 2:
                group.check(R.id.main_footbar_user);
                break;
        }
    }

    private void showFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new HomePageFrg();
            case 1:
                return new MyPatientFrg();
//            case 2:
//                return new MyTaskFrg();
            case 2:
                return new PersonalFrg();
            default:
                return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                Update.clearUpdate();
                isQuit = true;
                UIHelper.ToastMessage(this, R.string.press_back);
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                moveTaskToBack(true);
                AppManager.getAppManager().AppExit(this);
            }

        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case UIHelper.LOGIN_REQUEST_CODE:
                    if (LoginManager.isHasLogin()) {

                        if (TextUtils.isEmpty(isCompleteInfo())) {
                            showFragment();
                        } else {
                            currIndex = 0;
                            checkGroup(currIndex);
                        }
                        if (currIndex == 0) {
                            fragmentManager.findFragmentByTag(fragmentTags.get(0)).onActivityResult(requestCode, resultCode, data);
                        }

                    } else {
                        currIndex = 0;
                        checkGroup(currIndex);
                    }
                    break;
                case UIHelper.LOGINOUT_REQUEST_CODE:
                    currIndex = 0;
                    fragmentManager.findFragmentByTag(fragmentTags.get(0)).onActivityResult(requestCode, resultCode, data);
                    checkGroup(currIndex);
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        removeRegisterListener(this);
        super.onDestroy();
    }

    public void getIconRdePoi() {
        if (LoginManager.isHasLogin()){
            toNomalNet(toGetParams(toParamsBaen("doctorUuid", LoginManager.getDoctorUuid())), BaseResponse.class, 1, UrlConstants.getWholeApiUrl(UrlConstants.GET_UNREADCONS_ULTRECORD, "2.0"), "正在获取未读信息");
        }else {
            showOrHidePoi(false);
        }
    }

    public void showOrHidePoi(boolean b) {
        poi.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    /**
     * 刷新我的患者页面
     */
    private void refreshMyPatient(){
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(1));
        if (fragment == null) return;
        ((MyPatientFrg)fragment).reload();
    }

    @Override
    public void onSuccessToBean(Object bean, int flag) {
        super.onSuccessToBean(bean, flag);
        BaseResponse<Double> baseResponse = (BaseResponse<Double>) bean;
        if (bean != null) {
            showOrHidePoi(baseResponse.value > 0);
            refreshMyPatient();
        }
    }
}
