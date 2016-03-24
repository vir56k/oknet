package cn.jlb.oknet;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求管理器
 * Created by zhangyunfei on 15/12/15.
 */
public class RequestManager {
    private static RequestParaInterceptor sMyRequestParaInterceptor;
    private static DefaultExceptionHandler sDefaultExceptionHandler;
    private static Handler mHandler;

    /**
     * 参数拦截器
     *
     * @param requestParaInterceptor
     */
    public static void setRequestParaInterceptor(RequestParaInterceptor requestParaInterceptor) {
        sMyRequestParaInterceptor = requestParaInterceptor;
    }

    /**
     * 默认异常处理器
     *
     * @param defaultExceptionHandler
     */
    public static void setDefaultExceptionHandler(DefaultExceptionHandler defaultExceptionHandler) {
        sDefaultExceptionHandler = defaultExceptionHandler;
    }

    /**
     * 执行请求
     *
     * @param myRequest
     */
    public static CommonRequest excute(final CommonRequest myRequest) {
        ensureInit();
        try {
            ProgressIndicator progressIndicator = myRequest.getProgressIndicator();//进度条指示器
            if (progressIndicator != null) progressIndicator.onProgressStart();

            HashMap<String, String> userPara;
            userPara = (myRequest.getParas() == null) ? new HashMap<String, String>() : new HashMap<>(myRequest.getParas());
            // 公共参数处理
            if (sMyRequestParaInterceptor != null)
                sMyRequestParaInterceptor.onProcessCommonRequestPara(myRequest.getParas());
            pringlog(myRequest.getUrl(), userPara, myRequest.getParas(), null, myRequest.getObjectID() + "");

            Request request = RequestConvert.convert(myRequest);
            Callback callback = null;
            if (myRequest.getCallback() != null)
                callback = OkHttpResponseHandler.create(myRequest);
            Call call = OknetHttpUtil.enqueue(request, callback);
            myRequest.setTag("call", call);
            return myRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonCallbackInterface<?> callback = myRequest.getCallback();
            if (callback != null)
                callback.onFailure(0, ex, null, null);
            return myRequest;
        }

    }

    public static CommonRequest excute(final FileUploadRequest myRequest) {
        ensureInit();
        try {
            ProgressIndicator progressIndicator = myRequest.getProgressIndicator();//进度条指示器
            if (progressIndicator != null) progressIndicator.onProgressStart();

            HashMap<String, String> userPara;
            userPara = (myRequest.getParas() == null) ? new HashMap<String, String>() : new HashMap<>(myRequest.getParas());
            // 公共参数处理
            if (sMyRequestParaInterceptor != null)
                sMyRequestParaInterceptor.onProcessUploadFileRequestPara(myRequest.getParas(), myRequest.fileParas);
            pringlog(myRequest.getUrl(), userPara, myRequest.getParas(), myRequest.getFileParas(), myRequest.getObjectID() + "");

            Request request = RequestConvert.convert(myRequest);
            Callback callback = null;
            if (myRequest.getCallback() != null)
                callback = OkHttpResponseHandler.create(myRequest);
            Call call = OknetHttpUtil.enqueue(request, callback);
            myRequest.setTag("call", call);
            return myRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            CommonCallbackInterface<?> callback = myRequest.getCallback();
            if (callback != null)
                callback.onFailure(0, ex, null, null);
            return myRequest;
        }

    }

    private static void ensureInit() {
        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper());
    }

    private static class OkHttpResponseHandler implements Callback {
        private CommonCallbackInterface callbackObject;
        private CommonRequest mCommonRequest;

        private OkHttpResponseHandler(CommonRequest myRequest, CommonCallbackInterface callbackObject) {
            this.mCommonRequest = myRequest;
            this.callbackObject = callbackObject;
        }

        public static OkHttpResponseHandler create(CommonRequest myRequest) {
            if (myRequest == null)
                throw new NullPointerException();
            final CommonCallbackInterface callback = myRequest.getCallback();
            if (callback != null)
                callback.onStart();
            return new OkHttpResponseHandler(myRequest, callback);
        }

        @Override
        public void onFailure(Call call, IOException e) {
            ProgressIndicator progressIndicator = mCommonRequest.getProgressIndicator();//进度条指示器
            if (progressIndicator != null) progressIndicator.onProgressEnd();

            failure(mCommonRequest, 0, e, null, null);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            ProgressIndicator progressIndicator = mCommonRequest.getProgressIndicator();//进度条指示器
            if (progressIndicator != null) progressIndicator.onProgressEnd();

            String responseString = null;
            CommonMessage commonMessage = null;
            int httpCode = 0;
            try {
                httpCode = response.code();
                responseString = response.body().string();
                response.body().close();
                OknetLogUtil.i(String.format("【%s】【解析响应】httpCode=%s, responseString= %s", mCommonRequest.getObjectID(), httpCode, responseString));
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);
                commonMessage = CommonMessageParser.parse(responseString);
                if (commonMessage.code == 0) {
                    if (callbackObject != null) {
                        final Object responseObj = callbackObject.parseResponse(commonMessage.body, commonMessage, responseString);
                        //在ui线程执行
                        runOnUiThread(new ParaRunnable(new Object[]{responseObj, commonMessage, responseString}) {
                            @Override
                            protected void run(Object[] paras) {
                                CommonMessage commonMessage1 = (CommonMessage) paras[1];
                                String responseString1 = (String) paras[2];
                                callbackObject.onSuccess(responseObj, commonMessage1, responseString1);
                            }
                        });
                    }
                } else {//遇到服务端返回：非0的code,我们自定义异常
                    throw new NoZeroException(commonMessage.code, commonMessage.msg, commonMessage.body);
                }
            } catch (Exception ex) {
                failure(mCommonRequest, httpCode, ex, commonMessage, responseString);
            }
        }

        /**
         * 运行在ui线程
         *
         * @param runnable
         */
        private static void runOnUiThread(ParaRunnable runnable) {
            mHandler.post(runnable);
        }

        private void failure(CommonRequest commonRequest, int httpCode, Exception ex1, CommonMessage responseMessage, String responseString) {
            printError(commonRequest, httpCode, ex1, responseMessage, responseString);
            if (callbackObject == null)
                return;
            //如果 自己不处理，则交给默认的 异常处理器处理
            if (!callbackObject.onFailure(httpCode, ex1, responseMessage, responseString)) {
                if (sDefaultExceptionHandler != null)
                    sDefaultExceptionHandler.handleException(mCommonRequest.getContext(), httpCode, ex1, responseMessage, responseString);
            }
        }
    }

    private static void printError(CommonRequest commonRequest, int httpCode, Exception ex1, CommonMessage responseMessage, String responseString) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("【%s】【响应异常：】 ", commonRequest.getObjectID()));
        sb.append(String.format("http code ：%s, ", httpCode));
        sb.append(String.format("exception message：%s, ", ex1.getMessage() == null ? "null" : ex1.getMessage()));
        sb.append(String.format("exception type：%s, ", ex1.getClass().toString()));
        sb.append(String.format("response message：%s, ", responseMessage));
        sb.append(String.format("response responseString：%s", responseString));
        OknetLogUtil.e(sb.toString());
    }

    private static void pringlog(String url, Map<String, String> userParas, Map<String, String> allPara, Map<String, File> fileUploadPara, String objectID) {
        OknetLogUtil.i(String.format("【%s】【目标地址：】%s", objectID, url));
        Iterator it;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("【%s】【用户参数：】", objectID));
        if (userParas != null) {
            it = userParas.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(String.format("%s = %s; ", entry.getKey(), entry.getValue()));
            }
        }
        OknetLogUtil.i(sb.toString());

        sb.setLength(0);
        it = allPara.entrySet().iterator();
        sb.append(String.format("【%s】【完整参数：】", objectID));
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            sb.append(String.format("%s=%s; ", entry.getKey(), entry.getValue()));
        }
        OknetLogUtil.i(sb.toString());

        if (fileUploadPara != null && fileUploadPara.size() > 0) {
            sb.setLength(0);
            sb.append(String.format("【%s】【文件上传参数：】", objectID));
            for (Map.Entry<String, File> item : fileUploadPara.entrySet())
                sb.append(String.format("%s=%s; ", item.getKey(), item.getValue().getName()));
            OknetLogUtil.i(sb.toString());
        }
    }


    private abstract static class ParaRunnable implements Runnable {
        private Object[] paras;

        public ParaRunnable(Object[] para) {
            this.paras = para;
        }

        public Object[] getParas() {
            return paras;
        }

        public Object getPara(int index) {
            return paras[index];
        }

        @Override
        public void run() {
            run(getParas());
        }

        protected abstract void run(Object[] paras);
    }

}
