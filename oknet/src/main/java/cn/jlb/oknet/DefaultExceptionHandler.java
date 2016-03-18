package cn.jlb.oknet;


import android.content.Context;

/**
 * HTTP  异常处理器
 * Created by zhangyunfei on 15/12/25.
 */
public interface DefaultExceptionHandler {
    void handleException(Context context, int httpCode,Exception ex1, CommonMessage commonMessage, String responseString);
}
