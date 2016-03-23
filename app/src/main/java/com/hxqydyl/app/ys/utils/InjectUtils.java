package com.hxqydyl.app.ys.utils;

import android.app.Activity;
import android.view.View;
import java.lang.reflect.Field;

/**
 * Created by white_ash on 2016/3/24.
 */
public class InjectUtils {
    public static <T> void injectView(T holder,View view){
        Field[] fields = holder.getClass().getDeclaredFields();
        for(Field field : fields){
            if (field.isAnnotationPresent(InjectId.class)) {
                InjectId viewInject = field.getAnnotation(InjectId.class);
                int viewId = viewInject.id();
                try {
                    field.setAccessible(true);
                    field.set(holder, view.findViewById(viewId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void injectView(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(InjectId.class)) {
                    injectView(activity, field);
                }
            }
        }
    }

    private static void injectView(Activity activity, Field field) {
        if (field.isAnnotationPresent(InjectId.class)) {
            InjectId viewInject = field.getAnnotation(InjectId.class);
            int viewId = viewInject.id();
            try {
                field.setAccessible(true);
                field.set(activity, activity.findViewById(viewId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
