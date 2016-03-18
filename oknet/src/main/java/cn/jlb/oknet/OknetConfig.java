package cn.jlb.oknet;

import java.io.File;

/**
 * Created by zhangyunfei on 16/3/11.
 */
public class OknetConfig {

    private static File externalCacheDir;
    private static LogInterceptor logInterceptor;

    public static File getExternalCacheDir() {
        return externalCacheDir;
    }

    /**
     * 设置 缓存目录
     * @param externalCacheDir
     */
    public static void setExternalCacheDir(File externalCacheDir) {
        OknetConfig.externalCacheDir = externalCacheDir;
    }

    public static void setRequestParaInterceptor(RequestParaInterceptor mMyRequestParaInterceptor) {
        RequestManager.setRequestParaInterceptor(mMyRequestParaInterceptor);
    }

    /**
     * 设置 默认异常处理器
     * @param defaultExceptionHandler
     */
    public static void setDefaultExceptionHandler(DefaultExceptionHandler defaultExceptionHandler){
        RequestManager.setDefaultExceptionHandler(defaultExceptionHandler);
    }

    public static LogInterceptor getLogInterceptor() {
        return logInterceptor;
    }

    /**
     * 设置 日志 拦截器
     * @param logInterceptor
     */
    public static void setLogInterceptor(LogInterceptor logInterceptor) {
        OknetConfig.logInterceptor = logInterceptor;
    }
}
