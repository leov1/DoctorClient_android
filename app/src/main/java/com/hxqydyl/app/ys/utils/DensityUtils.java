package com.hxqydyl.app.ys.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by white_ash on 2016/3/19.
 */
public class DensityUtils {
    /**
     * dp值转化为px值
     *
     * @param context 上下文
     * @param dip     dp值
     * @return px值
     */
    public static int dp2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * px值转化为dp值
     *
     * @param context 上下文
     * @param px      px值
     * @return dp值
     */
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * sp转换成px
     *
     * @param context 上下文
     * @param sp      sp值
     * @return px值
     */
    public static int sp2px(Context context, float sp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sp * scale + 0.5f);
    }

    /**
     * px转换成sp
     *
     * @param context 上下文
     * @param px      px值
     * @return sp值
     */
    public static int px2sp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
