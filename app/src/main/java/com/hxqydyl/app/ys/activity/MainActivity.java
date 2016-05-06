
package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.fragment.HomePageFrg;
import com.hxqydyl.app.ys.fragment.MyPatientFrg;
import com.hxqydyl.app.ys.fragment.MyTaskFrg;
import com.hxqydyl.app.ys.fragment.PersonalFrg;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.utils.DialogUtils;
import com.hxqydyl.app.ys.utils.InjectId;
import com.hxqydyl.app.ys.utils.InjectUtils;
import com.hxqydyl.app.ys.utils.LoginManager;
import com.hxqydyl.app.ys.utils.SharedPreferences;
import com.hxqydyl.app.ys.utils.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import common.AppManager;
import framework.BaseFragmentActivity;
import framework.listener.RegisterSucListener;

public class MainActivity extends BaseFragmentActivity implements RegisterSucListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    @InjectId(id = R.id.group)
    private RadioGroup group;

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
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "PatientFragment", "TaskFragment", "UserFragment"));
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
                        case R.id.foot_bar_interest:
                            chageIndex(2);
                            break;
                        case R.id.main_footbar_user:
                            chageIndex(3);
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
        currIndex = index;
        if (index == 0 || LoginManager.isHasLogin()) {
            showFragment();
        } else {
            UIHelper.showLoginForResult(this);
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
            case 2:
                return new MyTaskFrg();
            case 3:
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
                        showFragment();
                        if (currIndex == 0) {
                            fragmentManager.findFragmentByTag(fragmentTags.get(0)).onActivityResult(requestCode, resultCode, data);
                        }
                    } else {
                        currIndex = 0;
                        group.check(R.id.foot_bar_home);
                    }
                    break;
                case UIHelper.LOGINOUT_REQUEST_CODE:
                    currIndex = 0;
                    fragmentManager.findFragmentByTag(fragmentTags.get(0)).onActivityResult(requestCode, resultCode, data);
                    group.check(R.id.foot_bar_home);
                    break;
            }

        }
    }

    @Override
    public void onRegisterSuc() {
        boolean firstTip = SharedPreferences.getInstance().getBoolean("first-time-tip", true);
        if (firstTip) {
            SharedPreferences.getInstance().putBoolean("first-time-tip", false);
            DialogUtils.showNormalDialog(this);
        }
    }

    @Override
    protected void onDestroy() {
        removeRegisterListener(this);
        super.onDestroy();
    }
}
