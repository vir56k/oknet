package zhangyf.vir56k.okhttpdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cn.jlb.oknet.LogInterceptor;
import cn.jlb.oknet.OknetConfig;
import zhangyf.vir56k.okhttpdemo.jlb.CustomRequestParaInterceptor_jlb_app;


/**
 * 程序主入口
 *
 * @author wqr
 */
public class BoxApp extends Application {
    private static final String TAG = "BoxApp";
    private static BoxApp app;

    public static BoxApp getApp() {
        return app;
    }

    public static Context getContext() {
        return app;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        app = this;

        //配置okhttp 缓存位置
        OknetConfig.setExternalCacheDir(getExternalCacheDir());
//        OknetConfig.setRequestParaInterceptor(new CustomRequestParaInterceptor1());
        OknetConfig.setRequestParaInterceptor(new CustomRequestParaInterceptor_jlb_app());
        OknetConfig.setDefaultExceptionHandler(new CustomDefalutExceptionHandler());
        OknetConfig.setLogInterceptor(new LogInterceptor() {
            @Override
            public void onLog(String tag, String msg) {
//                Log.i("日志拦截器拦截到 tag =" + tag, " msg = " + msg);
            }
        });

    }

}