package zhangyf.vir56k.okhttpdemo.jlb;//package cn.jlb.oknet;

import java.io.File;
import java.util.Map;

import cn.jlb.oknet.RequestParaInterceptor;
import zhangyf.vir56k.okhttpdemo.BoxApp;

/**
 * 用户参数拦截器 ..这里的配置还不正确，无法通过服务器鉴权
 * Created by zhangyunfei on 16/3/11.
 */
public class CustomRequestParaInterceptor_jlb_app implements RequestParaInterceptor {
    public static String SIGN_MD5_KEY_STRING = "hello";
    public static final String KEY_GARDEN_ID = "garden_id";

    @Override
    public void onProcessCommonRequestPara(Map<String, String> params) {
        params.put("ts", System.currentTimeMillis() + "");
        params.put("sid", UserUtils.getSID());
        params.put("sign_type", "MD5");

        if (params.get("district_id") == null || params.get("district_id").equals("0")) {
            params.put("district_id", PreferenceHelper.readInt(BoxApp.getContext(), KEY_GARDEN_ID, 0) + "");
//            params.put("district_id", "14");
        }

        if (!params.containsKey("uid"))
            params.put("uid", "0");//保证至少有个uid，未登录时会需要传这个参数
        if (false) {
            params.put("uid", "0");
            boolean transfer = PreferenceHelper.readBoolean(BoxApp.getContext(), "transfer");
            if (!transfer) {
                params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
                PreferenceHelper.write(BoxApp.getContext(), "transfer", true);
            }
            if (params.get("isFirstTime") != null && params.get("isFirstTime").equals("0")) {
                params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
                PreferenceHelper.write(BoxApp.getContext(), "isFirstTime", 1);
            }
        } else {
//            params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
        }

        String sign = SignUtils.signByMd5(params, SIGN_MD5_KEY_STRING);
        params.put("sign", sign);
    }

    @Override
    public void onProcessUploadFileRequestPara(Map<String, String> params, Map<String, File> fileParas) {
        params.put("ts", System.currentTimeMillis() + "");
        params.put("sid", UserUtils.getSID());
        params.put("sign_type", "MD5");

        if (params.get("district_id") == null || params.get("district_id").equals("0")) {
            params.put("district_id", PreferenceHelper.readInt(BoxApp.getContext(), KEY_GARDEN_ID, 0) + "");
//            params.put("district_id", "14");
        }

        if (!params.containsKey("uid"))
            params.put("uid", "0");//保证至少有个uid，未登录时会需要传这个参数
        if (false) {
            params.put("uid", "0");
            boolean transfer = PreferenceHelper.readBoolean(BoxApp.getContext(), "transfer");
            if (!transfer) {
                params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
                PreferenceHelper.write(BoxApp.getContext(), "transfer", true);
            }
            if (params.get("isFirstTime") != null && params.get("isFirstTime").equals("0")) {
                params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
                PreferenceHelper.write(BoxApp.getContext(), "isFirstTime", 1);
            }
        } else {
            params.put("tmpid", UserUtils.getTempId(BoxApp.getContext()));
        }

        String sign = SignUtils.signByMd5(params, SIGN_MD5_KEY_STRING);
        params.put("sign", sign);

    }

}
