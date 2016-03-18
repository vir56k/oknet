package zhangyf.vir56k.okhttpdemo.jlb;

import android.content.Context;

import zhangyf.vir56k.okhttpdemo.BoxApp;

/**
 * @author wqr
 * @ClassName: UserUtils
 * @Description: 用户工具类
 * @date 2014-11-21 下午5:18:37
 */
public class UserUtils {

    private static final String TAG = "UserUtils";
    private static final String KEY_USERINFO = "userInfo";
    private static final String KEY_SOCIALINFO = "socialInfo";

    public static final String KEY_SID = "key_sid";

    /**
     * @param @return
     * @return int
     * @throws
     * @Description: 获取session
     */
    public static String getSID() {
        return PreferenceHelper.readString(BoxApp.getContext(), KEY_SID);
    }

    public static String getTempId(Context mContext) {
        String deviceID = DeviceUtil.getIMEI(mContext);
        return MD5Util.GetMD5Code(deviceID);
    }

}
