package cn.jlb.oknet;

/**
 * 进度条 的 指示器
 * Created by zhangyunfei on 15/12/25.
 */
public interface ProgressIndicator {
    /**
     * 加载中
     */
    void onProgressStart();

    /**
     * 加载结束
     */
    void onProgressEnd();

}
