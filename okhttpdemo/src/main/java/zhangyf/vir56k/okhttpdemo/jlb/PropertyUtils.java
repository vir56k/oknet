package zhangyf.vir56k.okhttpdemo.jlb;

import java.security.AccessControlException;
import java.util.Properties;

/**
 * 
 * @ClassName: PropertyUtils
 * @Description: Property 工具类
 * @author wqr
 * @date 2014-11-3 下午5:43:04
 * 
 */
public class PropertyUtils {

	/**
	 * key
	 */
	public static final String KEY_SHARE_FILE_NAME = "key_share_file_name";

	/**
	 * 
	 */
	public static final String KEY_DEBUG = "key_jlb_debug";

	/**
	 * 
	 */
	public static final String SHARE_FILE_NAME = "jlb_mobile";

	/**
	 * key
	 */
	public static final String KEY_DEFAULT_IMAGE = "key_image_load";

	/**
	 * 
	 */
	private static Properties mDefaultProperty;

	static {
		init();
	}

	static void init() {
		mDefaultProperty = new Properties();
		mDefaultProperty.setProperty(KEY_DEBUG, "false");
		mDefaultProperty.setProperty(KEY_SHARE_FILE_NAME, SHARE_FILE_NAME);
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public static void setProperty(String name, String value) {
		mDefaultProperty.setProperty(name, value);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return getProperty(name, null);
	}

	/**
	 * 
	 * @param name
	 * @param fallbackValue
	 * @return
	 */
	private static String getProperty(String name, String fallbackValue) {
		String value;
		try {
			value = System.getProperty(name, fallbackValue);
			if (null == value) {
				value = mDefaultProperty.getProperty(name);
			}
			if (null == value) {
				String fallback = mDefaultProperty.getProperty(name + ".fallback");
				if (null != fallback) {
					value = System.getProperty(fallback);
				}
			}
		} catch (AccessControlException ace) {
			value = fallbackValue;
		}
		return replace(value);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private static String replace(String value) {
		if (null == value) {
			return value;
		}
		String newValue = value;
		int openBrace = 0;
		if (-1 != (openBrace = value.indexOf("{", openBrace))) {
			int closeBrace = value.indexOf("}", openBrace);
			if (closeBrace > (openBrace + 1)) {
				String name = value.substring(openBrace + 1, closeBrace);
				if (name.length() > 0) {
					newValue = value.substring(0, openBrace) + getProperty(name) + value.substring(closeBrace + 1);
				}
			}
		}
		if (newValue.equals(value)) {
			return value;
		} else {
			return replace(newValue);
		}
	}
}
