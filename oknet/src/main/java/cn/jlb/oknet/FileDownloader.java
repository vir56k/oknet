package cn.jlb.oknet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 文件下载
 * Created by zhangyunfei on 16/3/29.
 */
public class FileDownloader {

    private static OkHttpClient createOkHttpClient(final ResponseProgressListener progressListener) {
        if (OknetConfig.getExternalCacheDir() == null)
            throw new RuntimeException("在使用okhttp前，请调用OkHttpConfig.setExternalCacheDir配置缓存目录");

        return new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).
                addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拦截
                        Response originalResponse = chain.proceed(chain.request());
                        //包装响应体并返回
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                }).
                build();
    }

    public static DownloadRequestHandler downloadFile(String url, File targetFile, DownloadFileProgressListener2 progressListener) {
        if (targetFile == null)
            throw new NullPointerException();
        progressListener.setFile(targetFile);
        Request request = new Request.Builder().url(url).build();
        okhttp3.Call call = createOkHttpClient(progressListener).newCall(request);
        call.enqueue(progressListener);
        DownloadRequestHandler downloadRequestHandler = new DownloadRequestHandler();
        downloadRequestHandler.call = call;
        return downloadRequestHandler;
    }

    public static class DownloadRequestHandler {
        private okhttp3.Call call;

        public void cancel() {
            if (call != null) call.cancel();
        }
    }

    public static abstract class DownloadFileProgressListener2 implements ResponseProgressListener {
        File file;

        private void setFile(File file) {
            this.file = file;
        }

        @Override
        public void onResponse(okhttp3.Call call, Response response) {
            if (response.isSuccessful()) {
                byte[] buf = new byte[2048];
                int len = 0;
                InputStream is = response.body().byteStream();
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    onSuccess(call, file);
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call, e);
                }
            } else {
                onFailure(call, new IOException("未期望的http coe " + response.code()));
            }
        }

        protected abstract void onSuccess(Call call, File file);
    }

    public interface ResponseProgressListener extends Callback {
        void onProgress(long bytesRead, long contentLength, boolean done);
    }

    /**
     * 包装的响体，处理进度
     * User:lizhangqu(513163535@qq.com)
     * Date:2015-09-02
     * Time: 17:18
     */
    public static class ProgressResponseBody extends ResponseBody {
        //实际的待包装响应体
        private final ResponseBody responseBody;
        //进度回调接口
        private final ResponseProgressListener progressListener;
        //包装完成的BufferedSource
        private BufferedSource bufferedSource;

        /**
         * 构造函数，赋值
         *
         * @param responseBody     待包装的响应体
         * @param progressListener 回调接口
         */
        public ProgressResponseBody(ResponseBody responseBody, ResponseProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }


        /**
         * 重写调用实际的响应体的contentType
         *
         * @return MediaType
         */
        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        /**
         * 重写调用实际的响应体的contentLength
         * @return ex
         */
        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        /**
         * 重写进行包装source
         * @return BufferedSource
         */
        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                //包装
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        /**
         * 读取，回调进度接口
         *
         * @param source Source
         * @return Source
         */
        private Source source(Source source) {

            return new ForwardingSource(source) {
                //当前读取字节数
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    //回调，如果contentLength()不知道长度，会返回-1
                    progressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }
}
