package cn.jlb.oknet;

/**
 * Created by zhangyunfei on 16/3/15.
 */
public interface LogInterceptor {
    void onLog(String tag, String msg);
}
