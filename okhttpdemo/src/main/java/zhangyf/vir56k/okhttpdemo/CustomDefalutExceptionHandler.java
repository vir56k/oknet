package zhangyf.vir56k.okhttpdemo;//package cn.jlb.oknet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import cn.jlb.oknet.CommonMessage;
import cn.jlb.oknet.DefaultExceptionHandler;

/**
 * HTTP  异常处理器
 * Created by zhangyunfei on 15/12/25.
 */
public class CustomDefalutExceptionHandler implements DefaultExceptionHandler {
    private static final String TAG = "HTTP";


    public void alert(final Context context, final int id) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void alert(final Context context, final String msg) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void handleException(Context context, int httpCode, Exception ex1, CommonMessage commonMessage, String responseString) {
        if (ex1 == null)
            throw new NullPointerException();

        StringBuilder sb = new StringBuilder();
        sb.append("【HTTP默认异常处理器活动：】 ");
        sb.append(String.format("http code ：%s, ", httpCode));
        sb.append(String.format("Exception type：%s, ", ex1.getClass().toString()));
        sb.append(String.format("Exception message：%s, ", ex1.getMessage() == null ? "null" : ex1.getMessage()));
        sb.append(String.format("response message：%s, ", commonMessage));
        sb.append(String.format("response responseString：%s", responseString));
        Log.e(TAG, sb.toString());

        if (ex1 instanceof JsonSyntaxException) {
            alert(context, "解析服务端返回的数据时，发生了序列化错误");
            return;
        }

        if (ex1 instanceof IOException) {
            alert(context, "网络异常");
            return;
        }

        alert(context, "意外的错误：" + (ex1 == null ? "" : ex1.getMessage()));
    }
}
