package com.hxqydyl.app.ys.http;

import com.squareup.okhttp.Request;

import org.json.JSONException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hxq on 2016/3/25.
 */
public abstract class ResultCallback<T> {
    Type mType;

    public ResultCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    public void onBefore(Request request)
    {
    }

    public void onAfter()
    {
    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response) throws JSONException;
}
