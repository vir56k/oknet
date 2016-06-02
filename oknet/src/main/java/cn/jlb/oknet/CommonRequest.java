package cn.jlb.oknet;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.Call;

/**
 * 一般请求
 */
public class CommonRequest {
    private static int OBJECT_ID = 0;//对象id,全局类。用于表示 这个对象的唯一属性
    private int mObjectID = 0;//对象id
    private String url;
    private HashMap<String, Object> tags = new HashMap<>();
    private Map<String, String> paras;
    private CommonCallback<?> myJsonResponseHandler3;
    private Context context;
    private ProgressIndicator progressIndicator;

    /**
     *
     * @param url url
     * @param paras paras
     * @param myJsonResponseHandler3 myJsonResponseHandler3
     */
    public CommonRequest(String url, Map<String, String> paras, CommonCallback<?> myJsonResponseHandler3) {
        if (OBJECT_ID++ == Integer.MAX_VALUE) //防止溢出
            OBJECT_ID = 0;
        this.mObjectID = OBJECT_ID;

        this.url = url;
        this.paras = new WeakHashMap<>(paras);
        this.myJsonResponseHandler3 = myJsonResponseHandler3;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParas() {
        return paras;
    }

    public CommonCallbackInterface<?> getCallback() {
        return myJsonResponseHandler3;
    }

    /**
     * 获得对象id
     * @return id
     */
    public int getObjectID() {
        return mObjectID;
    }

    /**
     * 终止
     */
    public void cancel() {
        Object tag = getTag("call");
        if (tag instanceof Call) {
            Call call = (Call) tag;
            call.cancel();
        }
    }

    /**
     * 设置标记
     * @param key key
     * @param value value
     */
    public void setTag(String key, Object value) {
        tags.put(key, value);
    }

    /**
     *
     * @param key key
     * @return tag
     */
    public Object getTag(String key) {
        return tags.get(key);
    }

    /**
     *
     * @param context context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     *
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * 设置 进度指示器
     * @param progressIndicator 进度指示器
     */
    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    /**
     *
     * @return 进度指示器
     */
    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
}
