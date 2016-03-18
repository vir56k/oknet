package cn.jlb.oknet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangyunfei on 16/3/11.
 */
public class OknetHttpUtil {
    private static final int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static final String TAG = OknetHttpUtil.class.getSimpleName();

    private static OkHttpClient mOkHttpClient;

    static {
        if (OknetConfig.getExternalCacheDir() == null)
            throw new RuntimeException("在使用okhttp前，请调用OkHttpConfig.setExternalCacheDir配置缓存目录");
        Cache cache = new Cache(OknetConfig.getExternalCacheDir(), cacheSize);

        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).
                retryOnConnectionFailure(true).
                cache(cache).
                build();
    }

    /**
     * 同步执行
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步访问
     */
    public static Call enqueue(Request request, Callback responseCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(responseCallback);
        return call;
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);

        }
    }



}