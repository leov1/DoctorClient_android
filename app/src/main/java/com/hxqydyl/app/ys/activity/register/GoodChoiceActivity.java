package com.hxqydyl.app.ys.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.TagsAdapter;
import com.hxqydyl.app.ys.bean.register.TagsBean;
import com.hxqydyl.app.ys.bean.register.TagsResultBean;
import com.hxqydyl.app.ys.http.register.TagsNet;
import com.hxqydyl.app.ys.ui.swipebacklayout.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择擅长页
 */
public class GoodChoiceActivity extends BaseTitleActivity implements View.OnClickListener,TagsNet.OnTagsListener {

    private Button nextBtn;
    private Button laterBtn;

    private GridView gvTags;
    private TagsAdapter mAdapter;
    List<TagsBean> tvSelectedList = new ArrayList<TagsBean>();
    List<TagsBean> tagList = new ArrayList<TagsBean>();

    private TagsNet tagsNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_choice);


        initViews();
        initListeners();
        loadData();
    }


    private void initViews() {
        initViewOnBaseTitle("完善信息");

        tagsNet = new TagsNet();
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
                Intent nextIntent = new Intent(this, EvpiPhotoActivity.class);
                startActivity(nextIntent);
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
}
