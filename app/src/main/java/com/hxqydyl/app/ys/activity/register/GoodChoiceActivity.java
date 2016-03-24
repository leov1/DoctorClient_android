package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.TagsAdapter;
import com.hxqydyl.app.ys.bean.register.AddressParamBean;
import com.hxqydyl.app.ys.bean.register.GoodTagResultBean;
import com.hxqydyl.app.ys.bean.register.TagsBean;
import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.http.register.GoodTagNet;
import com.hxqydyl.app.ys.http.register.TagsNet;
import com.hxqydyl.app.ys.ui.UIHelper;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;
import com.hxqydyl.app.ys.utils.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择擅长页
 */
public class GoodChoiceActivity extends BaseTitleActivity implements View.OnClickListener,TagsNet.OnTagsListener,GoodTagNet.OnGoodTagListener {

    private AddressParamBean addressParamBean;

    private Button nextBtn;
    private Button laterBtn;

    private GridView gvTags;
    private TagsAdapter mAdapter;
    List<TagsBean> tvSelectedList = new ArrayList<TagsBean>();
    List<TagsBean> tagList = new ArrayList<TagsBean>();

    private Intent intent;
    private TagsNet tagsNet;
    private GoodTagNet goodTagNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_choice);

        getIntentData();
        initViews();
        initListeners();
        loadData();
    }

    private void getIntentData() {
        intent = getIntent();
        if (intent != null){
             addressParamBean = (AddressParamBean) intent.getSerializableExtra("bean");
        }
    }

    private void initViews() {
        initViewOnBaseTitle("完善信息");

        tagsNet = new TagsNet();
        goodTagNet = new GoodTagNet();
        goodTagNet.setListener(this);
        tagsNet.setListener(this);

        nextBtn = (Button) findViewById(R.id.next_btn);
        laterBtn = (Button) findViewById(R.id.later_btn);

        gvTags = (GridView) findViewById(R.id.gridview);
        mAdapter = new TagsAdapter(this, onItemClickClass,tagList);
        gvTags.setAdapter(mAdapter);
    }

    private void initListeners() {
        setBackListener(this);
        nextBtn.setOnClickListener(this);
        laterBtn.setOnClickListener(this);
    }

    /**
     * 获取gridview数据
     */
    private void loadData(){
        tagsNet.obtainTags();
    }

    /**
     * 实现接口，点击选中或者取消选中，并获取其被选中的集合
     */
    TagsAdapter.OnItemClickClass onItemClickClass = new TagsAdapter.OnItemClickClass() {
        @Override
        public void OnItemClick(View v, int position, CheckBox checkBox, TextView textView) {

            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_not_select_shape));
                textView.setTextColor(getResources().getColor(R.color.black));
                for (int i = 0; i < tvSelectedList.size(); i++) {
                    if (textView.getText().toString().equals(tvSelectedList.get(i).getTagName())) {
                        tvSelectedList.remove(i);
                    }
                }
            } else {
                if (tvSelectedList.size() == 5) {
                    UIHelper.ToastMessage(GoodChoiceActivity.this,"最多选5个");
                    return;
                }
                checkBox.setChecked(true);
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_select_shape));
                textView.setTextColor(getResources().getColor(R.color.white));
                for (int i = 0; i < tagList.size(); i++) {
                    if (textView.getText().toString().equals(tagList.get(i).getTagName())) {
                        tvSelectedList.add(tagList.get(i));
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                if (tvSelectedList.size() == 0){
                    UIHelper.ToastMessage(GoodChoiceActivity.this,"请选择擅长项");
                    return;
                }

                String selectStr = "";
                for(TagsBean tag:tvSelectedList){
                    selectStr += tag.getTagName()+",";
                 }
                addressParamBean.setDoctorUuid(LoginManager.getDoctorUuid());
                addressParamBean.setSpeciality(selectStr);
                goodTagNet.obtainTagResult(addressParamBean);

                break;
            case R.id.later_btn:
                Intent laterIntent = new Intent(this, EvpiPhotoActivity.class);
                startActivity(laterIntent);
                break;
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void requestTagsSuc(TagsResultBean tagsResultBean) {
        System.out.println("tagsResultBean--->"+tagsResultBean.toString());
        tagList = tagsResultBean.getTagsBeans();
        mAdapter.changeData(tagList);
    }

    @Override
    public void requestTagsFail() {

    }


    @Override
    public void requestGoodTagSuc(GoodTagResultBean goodTagResultBean) {

    }

    @Override
    public void requestGoodTagFail() {

    }
}
