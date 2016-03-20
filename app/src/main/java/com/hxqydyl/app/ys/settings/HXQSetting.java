package com.hxqydyl.app.ys.settings;

import android.content.Context;

/**
 * Created by qiliantao on 3/18/16.
 */
public class HXQSetting {
    private static ISettings settings;

    public static void init(Context context) {
        settings = new SettingsImpl(context, "hxq_config");
    }

    public static boolean isSetted(String key) {
        return settings.isSetted(key);
    }

    public static void setSetting(String key, boolean value) {
        settings.setSetting(key, value);
    }

    public static void setSetting(String key, int value) {
        settings.setSetting(key, value);
    }

    public static void setSetting(String key, float value) {
        settings.setSetting(key, value);
    }

    public static void setSetting(String key, long value) {
        settings.setSetting(key, value);
    }

    public static void setSetting(String key, String value) {
        settings.setSetting(key, value);
    }

    public static boolean getBoolean(String key) {
        if (isSetted(key)) {
            return settings.getBoolean(key);
        }
        return false;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return settings.getBoolean(key, defaultValue);
    }

    public static int getInt(String key) {
        return settings.getInt(key);
    }

    public static int getInt(String key, int defaultValue) {
        if (isSetted(key)) {
            return settings.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    public static float getFloat(String key) {
        return settings.getFloat(key);
    }

    public static float getFloat(String key, float defaultValue) {
        return settings.getFloat(key, defaultValue);
    }

    public static long getLong(String key) {
        return settings.getLong(key);
    }

    public static long getLong(String key, long defaultValue) {
        return settings.getLong(key, defaultValue);
    }

    public static String getString(String key) {
        return settings.getString(key);
    }

    public static String getString(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public static void saveObject(String fileName, Object object) {
        settings.saveObject(fileName, object);
    }

    public static Object readObject(String fileName) {
        return settings.readObject(fileName);
    }

    public static void clearObject(String fileName) {
        settings.clearObject(fileName);
    }


}
