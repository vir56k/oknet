package cn.jlb.oknet;

import android.util.Log;

/**
 * OknetLogUtil
 * Created by zhangyunfei on 16/3/15.
 */
public class OknetLogUtil {
    public static boolean DEBUG = true;//日志开关，支持外部调用关闭
    private static final String TAG = "oknet";

    public static void i(String msg) {
        if (!DEBUG) return;
        Log.i(TAG, msg == null ? "" : msg);
        if (OknetConfig.getLogInterceptor() != null) {
            OknetConfig.getLogInterceptor().onLog(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (!DEBUG) return;
        Log.e(TAG, msg == null ? "" : msg);
        if (OknetConfig.getLogInterceptor() != null) {
            OknetConfig.getLogInterceptor().onLog(TAG, msg);
        }
    }
}
