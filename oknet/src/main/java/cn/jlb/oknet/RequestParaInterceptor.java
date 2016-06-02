package cn.jlb.oknet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送请求时，拦截 发送的请求参数
 * Created by zhangyunfei on 16/3/14.
 */
public interface RequestParaInterceptor {

    /**
     * 普通消息的 参数拦截
     * @param para 参数
     */
    void onProcessCommonRequestPara(Map<String, String> para);

    /**
     * 文件上传时的消息拦截
     * @param paras 参数
     * @param fileParas 参数
     */
    void onProcessUploadFileRequestPara(Map<String, String> paras, Map<String, File> fileParas);
}
