package com.hxqydyl.app.ys.settings;

public interface ISettings {
    boolean isSetted(String var1);

    void setSetting(String var1, boolean var2);

    void setSetting(String var1, int var2);

    void setSetting(String var1, float var2);

    void setSetting(String var1, long var2);

    void setSetting(String var1, String var2);

    void removeSetting(String var1);

    boolean getBoolean(String var1);

    boolean getBoolean(String var1, boolean var2);

    int getInt(String var1);

    int getInt(String var1, int var2);

    float getFloat(String var1);

    float getFloat(String var1, float var2);

    long getLong(String var1);

    long getLong(String var1, long var2);

    String getString(String var1);

    String getString(String var1, String var2);

    void saveObject(String var1, Object var2);

    Object readObject(String var1);

    void clearObject(String var1);

    boolean containKey(String var1);
}
