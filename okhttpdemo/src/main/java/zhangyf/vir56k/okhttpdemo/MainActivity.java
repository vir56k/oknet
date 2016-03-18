package zhangyf.vir56k.okhttpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import cn.jlb.oknet.CommonCallback;
import cn.jlb.oknet.CommonMessage;
import cn.jlb.oknet.DialogProgressIndicator;
import cn.jlb.oknet.NoZeroException;
import cn.jlb.oknet.RequestBuilder;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        demo1();
//        demo3();
//        demo2();
//        demo_jlb_app_login();
//        demo_uploadFile();
        demo4_prgressDialog();
    }

    /* 这里还无法通过服务器鉴权*/
//    private void demo_jlb_app_login() {
//        File f = new File(Environment.getExternalStorageDirectory().getPath(), "ImageCache/CloseIcon.png");
//        if (!f.exists())
//            throw new RuntimeException("not found ImageCache/CloseIcon.png");
//        RequestBuilder.with(getActivity())
////                .URL("http://10.0.1.246:8888/eten/app/cart/goods/transfer")
//                .URL("http://10.0.1.232:8888/uc/slogin/normal")
//                .para("name", "15910622863")
//                .para("password", "123456")
//                .onSuccess(new CommonCallback<String>(String.class) {
//                    @Override
//                    public void onSuccess(String result, CommonMessage responseMessage, String responseString) {
//                        Log.i(TAG, "!!! 成功:" + result);
//                        alert("!!成功" + result);
//                    }
//                })
//                .excute();
//    }

    /* 这里还无法通过服务器鉴权，执行此方法时需要服务端关闭鉴权*/
    private void demo_uploadFile() {
        File f = new File(Environment.getExternalStorageDirectory().getPath(), "ImageCache/CloseIcon.png");
        if (!f.exists())
            throw new RuntimeException("not found ImageCache/CloseIcon.png");
        RequestBuilder.with(getActivity())
                .URL("http://10.0.1.232:8888/uc/suser/upload_avatar")
                .para("uid", "100202")
                .para("sid", "50e2904ca493d5d25475e4e080858925")
                        /************************ 威力仅仅在这一行,其他都一样 ***************************/
                .file("file", f)
                        /************************ 威力仅仅在这一行,其他都一样 ***************************/
                .onSuccess(new CommonCallback<Demo3Bean>(Demo3Bean.class) {
                    @Override
                    public void onSuccess(Demo3Bean result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.count);
                        alert("!!成功" + result.count);
                    }
                })
                .excute();
    }


    /**
     * 正常演示.带进度条指示器
     * 响应： List<Demo2Cell>
     */
    private void demo4_prgressDialog() {
        RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_NOTICE_LIST)
                .para("cabinet_code", "1412345678")
                        /******** 没错，你没有看错，仅仅 下面 一行，进度条就闪亮登场 ************/
                .progress(new DialogProgressIndicator(getActivity()))
                        /******** 没错，你没有看错，仅仅 上面 一行，进度条就闪亮登场 ************/
                .onSuccess(new CommonCallback<Demo3Bean>(Demo3Bean.class) {
                    @Override
                    public void onSuccess(Demo3Bean result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.count);
                        alert("!!成功" + result.count);
                    }
                })
                .excute();
    }

    /**
     * 正常演示.获得格口信息
     * 响应： List<Demo2Cell>
     */
    private void demo3() {
        RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_NOTICE_LIST)
                .para("cabinet_code", "1412345678")
                .onSuccess(new CommonCallback<Demo3Bean>(Demo3Bean.class) {
                    @Override
                    public void onSuccess(Demo3Bean result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.count);
                        alert("!!成功" + result.count);
                    }
                })
                .excute();
    }

    public static class Demo3Bean {
        int count;
    }

    /**
     * 正常演示.获得格口信息
     * 响应： List<Demo2Cell>
     */
    private void demo2() {
        Type t = new TypeToken<List<Demo2Cell>>() {
        }.getType();
        RequestBuilder.with(getActivity())
                .URL(Apis.Cab_Urls.GET_BOX_FREE_NEWS)
                .para("cabinet_code", "1412345678")
                .onSuccess(new CommonCallback<List<Demo2Cell>>(t) {
                    @Override
                    public void onSuccess(List<Demo2Cell> result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "!!! 成功:" + result.get(0));
                        alert("!!成功" + result.get(0));
                    }
                })
                .excute();
    }

    public static class Demo2Cell {
        public String type;//": 10901,
        public int status;//": 11101,
        public int code; //": "1"

        @Override
        public String toString() {
            return type + " " + status + " " + code;
        }
    }


    /**
     * 异常演示
     * 响应： string 类型的字符串
     * 异常： 自定义处理 服务端返回 code 不为0 的类型
     */
    private void demo1() {
        RequestBuilder.with(getActivity())
                .URL(Apis.GAEA_URLS.CAB_ADVERT_LIST)
                .onSuccess(new CommonCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result, CommonMessage responseMessage, String responseString) {
                        Log.i(TAG, "==成功:" + result);
                        alert("==成功");
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception ex, CommonMessage responseMessage, String responseString) {
                        if (ex instanceof NoZeroException) {
                            NoZeroException noZeroException = (NoZeroException) ex;
                            int code = noZeroException.getCode();
                            Log.i(TAG, "!!!!!!!!失败:" + noZeroException);
                            alert("!!!!!!!!!!!!!!!!失败," + noZeroException);
//                          return false;//如果不需要 默认异常处理器再次处理，这里可以返回true
                        }
                        return super.onFailure(httpCode, ex, responseMessage, responseString);
                    }
                })
                .excute();
    }

    private void alert(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), s, 0).show();
            }
        });
    }

    public Activity getActivity() {
        return this;
    }
}
