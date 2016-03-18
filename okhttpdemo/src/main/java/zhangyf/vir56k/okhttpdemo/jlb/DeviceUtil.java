package zhangyf.vir56k.okhttpdemo.jlb;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @ClassName: DeviceUtil
 * @Description: 设备参数相关 工具类
 * @author wqr
 * @date 2014-11-5 上午2:55:16
 * 
 */
public class DeviceUtil {

	public static void printDeviceInfo(Context context) {
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
		float density = metric.density;
		float densityDpi = metric.densityDpi;
		int heightPixels = metric.heightPixels;
		float scaledDensity = metric.scaledDensity;
		float xdpi = metric.xdpi;
		float ydpi = metric.ydpi;
//		Logger.d("DeviceUtil", "density " + density + ",densityDpi " + densityDpi + ", densityDpi " + densityDpi + ", heightPixels " + heightPixels
//				+ ", scaledDensity " + scaledDensity + ", xdpi " + xdpi + ", ydpi " + ydpi);
	}

	/**
	 * get http request UserAgent
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserAgent(Context context) {
		return new WebView(context).getSettings().getUserAgentString();
	}

	/**
	 * get IMSI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();

		return imsi;
	}

	/**
	 * get IMEI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();

		if (imei == null || imei.length() <= 0) {

			try {
				Class<?> c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", String.class);

				imei = (String) get.invoke(c, "ro.serialno");
			} catch (SecurityException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			} catch (IllegalArgumentException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			} catch (ClassNotFoundException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			} catch (NoSuchMethodException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			} catch (IllegalAccessException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			} catch (InvocationTargetException e) {
//				Logger.e("DeviceUtil", e.getLocalizedMessage());
			}

		}

		return imei;
	}

	public static String getPN() {
		return Build.MODEL;
	}

	/**
	 * get sdk version
	 * 
	 * @return
	 */
	public static int getSdkversion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * get width*hight
	 * 
	 * @param context
	 * @return
	 */
	public static String getScreenSize(Context context) {
		return getScreenWidth(context) + "*" + getScreenHight(context);
	}

	/**
	 * get Screen Width
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return getDisplay(context).getWidth();
	}

	/**
	 * get Screen Hight
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHight(Context context) {
		return getDisplay(context).getHeight();
	}

	/**
	 * get Display
	 * 
	 * @param context
	 * @return
	 */
	private static Display getDisplay(Context context) {
		Display display = ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display;
	}

	/**
	 * 根据行 数目显示gridview并计算大小
	 * 
	 * @param resources
	 * @param dimenId
	 * @param lineSize
	 *            列表总数
	 * @param listSize
	 *            列表行数
	 * @param gridLinear
	 * @param gridView
	 */
	public static void setUpGrid(Resources resources, int dimenId, int lineSize, int listSize, LinearLayout gridLinear, GridView gridView) {
		LayoutParams lp = (LayoutParams) gridLinear.getLayoutParams();
		lp.width = resources.getDimensionPixelSize(dimenId) * listSize / lineSize;
		gridLinear.setLayoutParams(lp);
		gridView.setNumColumns(listSize % lineSize == 0 ? listSize / lineSize : listSize / lineSize + 1);
	}

	/**
	 * 指定 gridview 显示的列数
	 * 
	 * @param activity
	 * @param columsNum
	 * @param gridLinear
	 * @param gridView
	 */
	public static void setUpGrid(Activity activity, int columsNum, LinearLayout gridLinear, GridView gridView) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		LayoutParams lp = gridLinear.getLayoutParams();
		lp.width = dm.widthPixels;
		// Display display = activity.getWindowManager().getDefaultDisplay();
		// lp.width = display.getWidth();
		gridLinear.setLayoutParams(lp);
		gridView.setNumColumns(columsNum);
	}

	/**
	 * WebView Settings extended
	 * 
	 * @param webView
	 */
	public static void webViewSettingsExtended(WebView webView) {
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setSupportMultipleWindows(true);
		settings.setJavaScriptEnabled(true);
		settings.setLightTouchEnabled(true);
		settings.setUseWideViewPort(true);
	}

	/**
	 * default WebView Settings
	 * 
	 * @param webView
	 */
	public static void webViewSettings(WebView webView) {
		WebSettings settings = webView.getSettings();
		settings.setSupportZoom(false);
		settings.setJavaScriptEnabled(true);
		settings.setLightTouchEnabled(true);
		settings.setUseWideViewPort(true);
	}

	/**
	 * 计算listview 高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * open Keyboard
	 *
	 * @param context
	 * @param view
	 */
	public static void openKeyboard(Context context, View view) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
	}

	/**
	 * close Keyboard
	 *
	 * @param context
	 * @param view
	 */
	public static void closeKeyboard(Context context, View view) {
		if (view != null) {
			try {
				((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(view.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * @Description: 获取设备信息
	 * @param @param context
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
