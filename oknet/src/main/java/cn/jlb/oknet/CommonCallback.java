package cn.jlb.oknet;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * 回调方法
 *
 * @param <T> 类型
 */
public abstract class CommonCallback<T> implements CommonCallbackInterface<T> {
    private Class cls;
    private Type responseType;

    /**
     * 指定 class
     *
     * @param cls class
     */
    public CommonCallback(Class cls) {
        if (cls == null) throw new NullPointerException();
        this.cls = cls;
    }

    /**
     * 指定
     *
     * @param type 类型
     */
    public CommonCallback(Type type) {
        if (type == null) throw new NullPointerException();
        this.responseType = type;
    }

    /**
     * 成功时
     * @param messsageBodyObject 消息对象bean
     * @param commonMessage msg
     * @param allResponseString 所有的字符串消息体
     */
    public abstract void onSuccess(T messsageBodyObject, CommonMessage commonMessage, String allResponseString);

    /**
     * 失败时
     * @param httpCode http code
     * @param exception ex
     * @param responseMessage 消息
     * @param allResponseString 所有的字符串消息体
     * @return isok
     */
    @Override
    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
        return false;
    }

    /**
     * on start
     */
    @Override
    public void onStart() {

    }

    /**
     * 解析
     * @param bodyString bodyString
     * @param commonMessage 消息
     * @param allResponseString 所有的字符串消息体
     * @return bean
     */
    @Override
    public T parseResponse(String bodyString, CommonMessage commonMessage, String allResponseString) {
        if (cls != null && cls.equals(String.class)) {
            return cast(bodyString);
        } else {
            if (cls != null)
                return cast(new Gson().fromJson(bodyString, cls));
            else
                return cast(new Gson().fromJson(bodyString, responseType));
        }
    }

    /**
     * 转换
     * @param obj 对象
     * @param <T> 类型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    protected static <T> T cast(Object obj) {
        return (T) obj;
    }

}
