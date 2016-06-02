package cn.jlb.oknet;

import android.content.Context;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * http 请求 构造器
 * 目前不支持 文件上传的同步放过
 * 不支持 get 方式
 * Created by zhangyunfei on 15/12/15.
 */
public class RequestBuilder {
    private WeakReference<Context> activityWeakReference;
    private String url;
    private HashMap<String, String> paras;
    private CommonCallback callback;
    private ProgressIndicator mProgressObserver;
    private WeakHashMap<String, File> fileParas;

    private RequestBuilder() {
        paras = new HashMap<>();
        fileParas = new WeakHashMap<>();
    }

    public void setContext(Context context) {
        this.activityWeakReference = new WeakReference<>(context);
    }

    public static RequestBuilder with(Context activity) {
        RequestBuilder myRequestBuilder = new RequestBuilder();
        myRequestBuilder.setContext(activity);
        return myRequestBuilder;
    }

    public RequestBuilder URL(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder para(String key, String value) {
        paras.put(key, value);
        return this;
    }

    public RequestBuilder para(String key, int value) {
        paras.put(key, value + "");
        return this;
    }

    public RequestBuilder file(String key, File value) {
        fileParas.put(key, value);
        return this;
    }

    public RequestBuilder onSuccess(CommonCallback myJsonResponseHandler3) {
        this.callback = myJsonResponseHandler3;
        return this;
    }

    public RequestBuilder progress(ProgressIndicator isShowProgress) {
        this.mProgressObserver = isShowProgress;
        return this;
    }

    public CommonRequest get() {
        CommonRequest request;
        if (fileParas.size() > 0) {
            request = new FileUploadRequest(url, paras, fileParas, callback);
        } else {
            request = new CommonRequest(url, paras, callback);
        }
        request.setContext(activityWeakReference.get());
        request.setProgressIndicator(mProgressObserver);
        return request;
    }

    /**
     * 同步执行
     *
     * @return CommonRequest
     */
    public CommonRequest syncExcute() {
        try {
            CommonRequest request = this.get();
            return RequestManager.syncExcute(request);
        } finally {
            clear();
        }
    }

    /**
     * 执行请求
     * @return CommonRequest
     */
    public CommonRequest excute() {
        try {
            CommonRequest request = this.get();
            if (request instanceof FileUploadRequest) {
                return RequestManager.excute((FileUploadRequest) request);
            } else {
                return RequestManager.excute(request);
            }
        } finally {
            clear();
        }
    }

    private void clear() {
        if (activityWeakReference != null && activityWeakReference.get() != null)
            activityWeakReference.clear();
        url = null;
        if (paras != null)
            paras.clear();
        paras = null;
        callback = null;
        mProgressObserver = null;
    }


}
