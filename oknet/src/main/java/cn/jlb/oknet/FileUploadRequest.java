package cn.jlb.oknet;

import java.io.File;
import java.util.Map;

/**
 * Created by zhangyunfei on 16/3/16.
 */
public class FileUploadRequest extends CommonRequest {
    Map<String, File> fileParas;

    public FileUploadRequest(String url, Map<String, String> paras, Map<String, File> fileParas, CommonCallback<?> myJsonResponseHandler3) {
        super(url, paras, myJsonResponseHandler3);
        this.fileParas = fileParas;
    }

    public Map<String, File> getFileParas() {
        return fileParas;
    }
}
