package cn.jlb.oknet;


/**
 * 回调方法
 * Created by zhangyunfei on 16/3/11.
 */
public interface CommonCallbackInterface<T> {

    void onSuccess(T json_type, CommonMessage responseMessage, String responseString);

    boolean onFailure(int httpCode, Exception ex1, CommonMessage responseMessage, String responseString);

    void onStart();

    T parseResponse(String bodyString, CommonMessage rootEntity, String responseString);
}
