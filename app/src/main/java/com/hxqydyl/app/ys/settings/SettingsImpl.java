package com.hxqydyl.app.ys.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

final class SettingsImpl implements ISettings {
    private static final String TAG = "SettingsImpl";
    private SharedPreferences mSharedPref;

    public SettingsImpl(Context context, String name) {
        this.mSharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public boolean isSetted(String key) {
        return this.mSharedPref.contains(key);
    }

    public void setSetting(String key, boolean value) {
        try {
            Editor e = this.mSharedPref.edit();
            e.putBoolean(key, value);
            e.commit();
        } catch (Exception var4) {
            Log.e("SettingsImpl", "setSetting(" + key + ", " + value + ")", var4);
        }

    }

    public void setSetting(String key, int value) {
        try {
            Editor e = this.mSharedPref.edit();
            e.putInt(key, value);
            e.commit();
        } catch (Exception var4) {
            Log.e("SettingsImpl", "setSetting(" + key + ", " + value + ")", var4);
        }

    }

    public void setSetting(String key, float value) {
        try {
            Editor e = this.mSharedPref.edit();
            e.putFloat(key, value);
            e.commit();
        } catch (Exception var4) {
            Log.e("SettingsImpl", "setSetting(" + key + ", " + value + ")", var4);
        }

    }

    public void setSetting(String key, long value) {
        try {
            Editor e = this.mSharedPref.edit();
            e.putLong(key, value);
            e.commit();
        } catch (Exception var5) {
            Log.e("SettingsImpl", "setSetting(" + key + ", " + value + ")", var5);
        }

    }

    public void setSetting(String key, String value) {
        if(null != value) {
            value = value.replace("\u0000", "");
        }

        try {
            Editor e = this.mSharedPref.edit();
            e.putString(key, value);
            e.commit();
        } catch (Exception var4) {
            Log.e("SettingsImpl", "setSetting(" + key + ", " + value + ")", var4);
        }

    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        boolean result = defaultValue;

        try {
            result = this.mSharedPref.getBoolean(key, result);
        } catch (Exception var5) {
            Log.e("SettingsImpl", "getBoolean()", var5);
        }

        return result;
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        int value = defaultValue;

        try {
            value = this.mSharedPref.getInt(key, defaultValue);
        } catch (Exception var5) {
            Log.e("SettingsImpl", "getSetting()", var5);
        }

        return value;
    }

    public float getFloat(String key) {
        return this.getFloat(key, 0.0F);
    }

    public float getFloat(String key, float defaultValue) {
        float value = defaultValue;

        try {
            value = this.mSharedPref.getFloat(key, defaultValue);
        } catch (Exception var5) {
            Log.e("SettingsImpl", "getLongSetting()", var5);
        }

        return value;
    }

    public long getLong(String key) {
        return this.getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue) {
        long value = defaultValue;

        try {
            value = this.mSharedPref.getLong(key, defaultValue);
        } catch (Exception var7) {
            Log.e("SettingsImpl", "getLongSetting()", var7);
        }

        return value;
    }

    public String getString(String key) {
        return this.getString(key, (String)null);
    }

    public String getString(String key, String defaultValue) {
        String value = defaultValue;

        try {
            value = this.mSharedPref.getString(key, defaultValue);
        } catch (Exception var5) {
            Log.e("SettingsImpl", "getString()", var5);
        }

        return value;
    }

    public void saveObject(String fileName, Object object) {
        ObjectOutputStream objectOutputStream = null;

        try {
            File e = new File(fileName);
            if(e.exists()) {
                e.delete();
            }

            e.createNewFile();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(e));
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception var13) {
            Log.e("SettingsImpl", "saveObject()", var13);
        } finally {
            if(objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException var12) {
                    Log.e("SettingsImpl", "saveObject()", var12);
                }
            }

        }

    }

    public Object readObject(String fileName) {
        Object object = null;
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            object = objectInputStream.readObject();
        } catch (Exception var13) {
            Log.e("SettingsImpl", "readObject()" + var13);
        } finally {
            if(objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException var12) {
                    Log.e("SettingsImpl", "readObject()" + var12);
                }
            }

        }

        return object;
    }

    public void clearObject(String fileName) {
        try {
            File e = new File(fileName);
            if(e.exists()) {
                e.delete();
                Log.d("SettingsImpl", "delete file success");
            }
        } catch (Exception var3) {
            Log.e("SettingsImpl", " clearObject()", var3);
        }

    }

    public void removeSetting(String key) {
        try {
            if(!TextUtils.isEmpty(key)) {
                Editor e = this.mSharedPref.edit();
                e.remove(key);
                e.commit();
            }
        } catch (Exception var3) {
            Log.e("SettingsImpl", "removeSetting(" + key + ")", var3);
        }

    }

    public boolean containKey(String key) {
        boolean isContains = false;

        try {
            isContains = this.mSharedPref.contains(key);
        } catch (Exception var4) {
            Log.e("SettingsImpl", "", var4);
        }

        return isContains;
    }
}
