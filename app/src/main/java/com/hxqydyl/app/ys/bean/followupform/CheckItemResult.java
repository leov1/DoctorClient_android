package com.hxqydyl.app.ys.bean.followupform;

import com.hxqydyl.app.ys.bean.Pic;

import java.util.ArrayList;

/**
 * Created by white_ash on 2016/3/27.
 */
public class CheckItemResult {
//    结果文字描述
    private String text;
//    结果图片
    private ArrayList<Pic> pics = new ArrayList<Pic>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Pic> getPics() {
        return pics;
    }

    public void addPic(Pic pic){
        pics.add(pic);
    }
}
