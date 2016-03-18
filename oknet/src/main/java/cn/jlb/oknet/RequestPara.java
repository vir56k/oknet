package cn.jlb.oknet;

import java.util.LinkedHashMap;

/**
 * 参数封装
 * Created by songzhongkun on 15/9/24.
 */
public class RequestPara extends LinkedHashMap<String, String> {
    private RequestPara() {

    }

    public static RequestPara create() {
        return new RequestPara();
    }

    public RequestPara with(String key, String value) {
        if (value != null) {
            put(key, value);
        }
        return this;
    }

    public RequestPara with(String key, int value) {
        put(key, "" + value);
        return this;
    }

    public RequestPara with(String key, long value) {
        put(key, "" + value);
        return this;
    }

    public RequestPara end() {
        return this;
    }

}
