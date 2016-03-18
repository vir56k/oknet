package zhangyf.vir56k.okhttpdemo;//package cn.jlb.oknet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jlb.oknet.RequestParaInterceptor;

/**
 * 用户参数拦截器
 * Created by zhangyunfei on 16/3/11.
 */
public class CustomRequestParaInterceptor1 implements RequestParaInterceptor {

    @Override
    public void onProcessCommonRequestPara(Map<String, String> params) {
        params.put("ts", System.currentTimeMillis() + "");
        params.put("sid", getSID());
        params.put("sign_type", "MD5");

        if (params.get("district_id") == null || params.get("district_id").equals("0")) {
            params.put("district_id", district_id());
//            params.put("district_id", "14");
        }

        params.put("uid", "0");//保证至少有个uid，未登录时会需要传这个参数
        if (isLogin()) {
            params.put("uid", getUid() + "");
            boolean transfer = true;//PreferenceHelper.readBoolean(JlbApp.getContext(), "transfer");
            if (!transfer) {
                params.put("tmpid", getTmpID());
//                PreferenceHelper.write(JlbApp.getContext(), "transfer", true);
            }
            if (params.get("isFirstTime") != null && params.get("isFirstTime").equals("0")) {
                params.put("tmpid", getTmpID());
//                PreferenceHelper.write(JlbApp.getContext(), "isFirstTime", 1);
            }
        } else {
            params.put("tmpid", getTmpID());
        }

        String sign = getSign(params);
        params.put("sign", sign);
    }

    @Override
    public void onProcessUploadFileRequestPara(Map<String, String> paras, Map<String, File> fileParas) {


    }

    private String getSign(Map<String, String> params) {
        return "xxxx";//SignUtils.signByMd5(params, Constans.SIGN_MD5_KEY_STRING);
    }

    private String district_id() {
        return "1";
    }

    public String getSID() {
        return "1";
    }

    public boolean isLogin() {
        return true;
    }

    public String getUid() {
        return "1";
    }

    public String getTmpID() {
        return "1";//UserUtils.getTempId(JlbApp.getContext());
    }
}
