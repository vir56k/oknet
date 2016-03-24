package cn.jlb.oknet;


/**
 * 回调方法
 * Created by zhangyunfei on 16/3/11.
 */
public interface CommonCallbackInterface<T> {

    void onSuccess(T messsageBodyObject, CommonMessage responseMessage, String allResponseString);

    boolean onFailure(int httpCode, Exception ex1, CommonMessage responseMessage, String allResponseString);

    void onStart();

    T parseResponse(String bodyString, CommonMessage rootEntity, String allResponseString);
}
