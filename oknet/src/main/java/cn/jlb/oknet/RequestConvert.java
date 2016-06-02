package cn.jlb.oknet;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * RequestConvert
 * Created by zhangyunfei on 16/3/11.
 */
public class RequestConvert {
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    public static Request convert(CommonRequest inRequest) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (null != inRequest.getParas()) {

            for (Map.Entry<String, String> item : inRequest.getParas().entrySet()) {
                formBodyBuilder.add(item.getKey(), item.getValue() + "");
            }
        }
        return new Request.Builder()
                .url(inRequest.getUrl())
                .post(formBodyBuilder.build())
                .addHeader("Accept-Encoding", "gzip,deflate")
                .build();
    }

    public static Request convert(FileUploadRequest inRequest) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, String> item : inRequest.getParas().entrySet()) {
            builder.addFormDataPart(item.getKey(), item.getValue() + "");
        }
        for (Map.Entry<String, File> item : inRequest.getFileParas().entrySet()) {
            builder.addFormDataPart(item.getKey(), item.getValue().getName(),
                    RequestBody.create(MEDIA_TYPE_STREAM, item.getValue()));
        }
        MultipartBody requestBody = builder.build();
        return new Request.Builder()
                .url(inRequest.getUrl())
                .post(requestBody)
                .build();
    }

}
