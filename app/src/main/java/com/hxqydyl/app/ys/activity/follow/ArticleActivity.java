package com.hxqydyl.app.ys.activity.follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hxqydyl.app.ys.R;
import com.hxqydyl.app.ys.activity.BaseTitleActivity;
import com.hxqydyl.app.ys.adapter.ArticlesAdapter;

/**
 * 患教库列表
 */
public class ArticleActivity extends BaseTitleActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ListView listView;
    private ArticlesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_article);

        initViews();
        initListeners();
    }

    private void initListeners() {
        setBackListener(this);
        listView.setOnItemClickListener(this);
    }

    private void initViews() {
        initViewOnBaseTitle("患教库");
        listView = (ListView) findViewById(R.id.article_lv);

        adapter = new ArticlesAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(ArticleActivity.this,ArticleDetailActivity.class));
    }
}
