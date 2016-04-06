package com.hxqydyl.app.ys.bean.follow.plan;

import com.alibaba.fastjson.JSONArray;
import com.hxqydyl.app.ys.utils.StringUtils;

import java.util.List;

/**
 * Created by wangchao36 on 16/4/4.
 */
public class Scale {

    private String id;
    private String title;
    private String digest;
    private String self;        //self：0——患者自评，1——医生评测

    public static List<Scale> parse(String string) {
        return JSONArray.parseArray(string, Scale.class);
    }

    public static String parseIdStr(List<Scale> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Scale scale : list) {
            if (scale != null && StringUtils.isNotEmpty(scale.getId())) {
                if (sb.length() > 0) sb.append(",");
                sb.append(scale.getId());
            }
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }
}
