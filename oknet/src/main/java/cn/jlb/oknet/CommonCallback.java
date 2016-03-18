package cn.jlb.oknet;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;


/**
 * 回调方法
 * Created by zhangyunfei on 16/3/11.
 */
public abstract class CommonCallback<T> implements CommonCallbackInterface<T> {
    private Class cls;
    private Type responseType;

    public CommonCallback(Class cls) {
        if (cls == null) throw new NullPointerException();
        this.cls = cls;
    }

    public CommonCallback(Type type) {
        if (type == null) throw new NullPointerException();
        this.responseType = type;
    }

    public abstract void onSuccess(T reslut, CommonMessage responseMessage, String responseString);

    @Override
    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String responseString) {
        return false;
    }

    @Override
    public void onStart() {

    }

    @Override
    public T parseResponse(String bodyString, CommonMessage rootEntity, String responseString) {
        if (cls != null && cls.equals(String.class)) {
            return cast(bodyString);
        } else {
            if (cls != null)
                return cast(new Gson().fromJson(bodyString, cls));
            else
                return cast(new Gson().fromJson(bodyString, responseType));
        }
    }

    @SuppressWarnings("unchecked")
    protected static <T> T cast(Object obj) {
        return (T) obj;
    }

}
