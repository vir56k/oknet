package cn.jlb.oknet;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by songzhongkun on 15/9/24.
 */
public class CommonMessageParser {
    private static final String TAG = CommonMessageParser.class.getSimpleName();

    /**
     * @Description: 解析成实体
     */
    public static CommonMessage parse(String responseString) throws JSONException {
        if (responseString == null)
            return null;
        JSONObject object = new JSONObject(responseString);
        String code = object.getString("code");
        String errmsg = object.getString("msg");
        String body = object.getString("body");

        CommonMessage entity = new CommonMessage();
        if (TextUtils.isEmpty(code))
            throw new JSONException("远程服务意外的返回内容：code = 空");
        entity.code = Integer.parseInt(code);
        entity.msg = errmsg == null ? "" : errmsg;
        entity.body = body == null ? "" : body;
        return entity;
    }


}
