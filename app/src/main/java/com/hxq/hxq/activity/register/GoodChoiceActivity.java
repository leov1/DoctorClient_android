package com.hxq.hxq.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.hxq.hxq.R;
import com.hxq.hxq.adapter.TagsAdapter;
import com.hxq.hxq.ui.swipebacklayout.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择擅长页
 */
public class GoodChoiceActivity extends SwipeBackActivity implements View.OnClickListener{

    private Button nextBtn;
    private Button laterBtn;

    private GridView gvTags;
    private TagsAdapter mAdapter;
    List<String> tvSelectedList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_choice);

        initViews();
        initListeners();
    }


    private void initViews(){
        nextBtn = (Button) findViewById(R.id.next_btn);
        laterBtn = (Button) findViewById(R.id.later_btn);

        gvTags = (GridView) findViewById(R.id.gridview);
        mAdapter = new TagsAdapter(this,onItemClickClass);
        gvTags.setAdapter(mAdapter);
    }
    private void initListeners() {

        nextBtn.setOnClickListener(this);
        laterBtn.setOnClickListener(this);
    }

    /** 实现接口，点击选中或者取消选中，并获取其被选中的集合 */
    TagsAdapter.OnItemClickClass onItemClickClass = new TagsAdapter.OnItemClickClass() {
        @Override
        public void OnItemClick(View v, int position, CheckBox checkBox, TextView textView) {
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    textView.setBackgroundColor(getResources().getColor(R.color.white));
                    textView.setTextColor(getResources().getColor(R.color.black));
                    for (int i = 0; i < tvSelectedList.size(); i++) {
                        if (textView.getText().toString().equals(tvSelectedList.get(i))) {
                            tvSelectedList.remove(i);
                        }
                    }
                } else {
                    checkBox.setChecked(true);
                    textView.setBackgroundColor(getResources().getColor(R.color.orange));
                    textView.setTextColor(getResources().getColor(R.color.white));
                    tvSelectedList.add(textView.getText().toString());
                }

        }
    };

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.next_btn:
               Intent nextIntent = new Intent(this,EvpiPhotoActivity.class);
               startActivity(nextIntent);
               break;
           case R.id.later_btn:
               Intent laterIntent = new Intent(this,EvpiPhotoActivity.class);
               startActivity(laterIntent);
               break;
       }
    }
}
