package com.hxqydyl.app.ys.activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseRequstActivity;
import com.hxqydyl.app.ys.activity.CommentWebActivity;
import com.hxqydyl.app.ys.fragment.QuakuducationFragment;
import com.hxqydyl.app.ys.fragment.QualiditingFragment;
import com.hxqydyl.app.ys.http.UrlConstants;
import com.hxqydyl.app.ys.ui.UIHelper;

import java.util.Map;

/**
 * Created by wangxu on 2016/5/6.
 * 医生认证
 *
 */
public class QualidicationActivity extends BaseRequstActivity implements QuakuducationFragment.OnSubmitSuccess{
    private int status;
    private Fragment content;

    /**
     * 医生认证
     * @param activity
     * @param status 0待审核，1审核通过，2审核不通过
     */
    public static void toQualidicationActivity(Activity activity, String status) {
        int mystatus=0;
        try{
            mystatus=Integer.parseInt(status);
            return;
        }catch (Exception e){
            UIHelper.ToastMessage(activity,"医生认证状态有误，请联系管理员");
        }
        if (mystatus==0|mystatus==3){
            Intent intent = new Intent(activity, QualidicationActivity.class);
            intent.putExtra("status", mystatus);
            activity.startActivity(intent);
        }else{
            CommentWebActivity.toCommentWebForResult(UrlConstants.getWholeApiUrl(UrlConstants.PERSONAL_INFORMATION), activity, 1, false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_qualification);
        init();
        initView();
    }

    private void init() {
         status = getIntent().getExtras().getInt("status", 0);

    }

    private void initView() {
        initViewOnBaseTitle("医生认证");
        setBackListener();
        setDefaultFragment(status);

    }

    private void setDefaultFragment(int status) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (status) {
            case 0://未认证
                content = new QuakuducationFragment();
                break;
            case 3://认证中
                content = new QualiditingFragment();
                break;
        }
        if (content instanceof  QuakuducationFragment){
            QuakuducationFragment fragment=(QuakuducationFragment)content;
            fragment.setOnSubmitSuccess(this);
        }
        transaction.replace(R.id.fragment_container, content);
        transaction.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        content.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSucess() {
        setDefaultFragment(3);
    }
}
