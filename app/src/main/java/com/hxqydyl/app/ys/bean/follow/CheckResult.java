package com.hxqydyl.app.ys.bean.follow;

import java.util.List;

/**
 * Created by wangxu on 2016/4/21.
 */
public class CheckResult {
    private String uuid;//检查id
    private String name;//检查名称
    private String result;//检查结果
    private List<String> imgs;//图片

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
