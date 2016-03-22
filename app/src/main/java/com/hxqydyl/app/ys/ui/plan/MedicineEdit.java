package com.hxqydyl.app.ys.ui.plan;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.hxqydyl.app.ys.R;

/**
 * Created by wangchao36 on 16/3/22.
 * 用药
 */
public class MedicineEdit extends LinearLayout implements View.OnClickListener {

    private EditText etName;
    private TextView tvTimeMorning;
    private TextView tvTimeNoon;
    private TextView tvTimeNight;
    private TextView tvFoodRelation;    //与食物的关系，是饭前，饭后

    public MedicineEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.medicine_edit, this);

        etName = (EditText) findViewById(R.id.etName);
        tvTimeMorning = (TextView) findViewById(R.id.tvTimeMorning);
        tvTimeNoon = (TextView) findViewById(R.id.tvTimeNoon);
        tvTimeNight = (TextView) findViewById(R.id.tvTimeNight);
        tvFoodRelation = (TextView) findViewById(R.id.tvFoodRelation);

        tvFoodRelation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFoodRelation:
                foodRelationDialog();
                break;
        }
    }

    private void foodRelationDialog() {
        final String[] items = {"饭前服用", "饭后服用", "不与餐同服"};
        final NormalListDialog dialog = new NormalListDialog(this.getContext(), items);
        dialog.title("请选择与食物的关系")
                .titleBgColor(getResources().getColor(R.color.color_home_topbar))
                .layoutAnimation(null)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvFoodRelation.setText(items[position]);
                dialog.dismiss();
            }
        });
    }

}
