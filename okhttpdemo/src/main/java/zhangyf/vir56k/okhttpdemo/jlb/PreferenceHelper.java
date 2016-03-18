package zhangyf.vir56k.okhttpdemo.jlb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @ClassName: PreferenceHelper
 * @Description: SharedPreferences操作工具包
 * @author wqr
 * @date 2014-11-3 下午5:20:48
 * 
 */
public class PreferenceHelper {
	
	public static void remove(Context context, String key) {
		
		if (null == context || null == key) {
			return;
		}
		
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.remove(key) ;
		editor.commit();
		
	}
	
	public static void write(Context context, String k, int v) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putInt(k, v);
		editor.commit();
	}

	public static void write(Context context, String k, long v) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putLong(k, v);
		editor.commit();
	}
	
	public static void write(Context context, String k, boolean v) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putBoolean(k, v);
		editor.commit();
	}

	public static void write(Context context, String k, String v) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(k, v);
		editor.commit();
	}

	public static int readInt(Context context, String k) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getInt(k, 0);
	}

	public static int readInt(Context context, String k, int defv) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getInt(k, defv);
	}
	
	public static Long readLong(Context context, String k, long defv) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getLong(k, defv);
	}

	public static boolean readBoolean(Context context, String k) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getBoolean(k, false);
	}

	public static boolean readBoolean(Context context, String k, boolean defBool) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getBoolean(k, defBool);
	}

	public static String readString(Context context, String k) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getString(k, null);
	}

	public static String readString(Context context, String k, String defV) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		return preference.getString(k, defV);
	}

	/**
	 * 
	 * @Title: getShareFileName
	 * @Description: 获取配置的文件名
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String getShareFileName() {
		return PropertyUtils.getProperty(PropertyUtils.KEY_SHARE_FILE_NAME);
	}
	
	public static void setObject(Context context ,String key, Object obj) {
		try {
			write(context,key, obj2Str(obj));
		} catch (Exception e) {
		}
	}

	public static Object getObject(Context context,String key) {
		SharedPreferences preference = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
		try {
			return str2Obj(preference.getString(key, null));
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressLint("NewApi")
	private static String obj2Str(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		} catch (Exception e) {
			throw e;
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressLint("NewApi")
	private static Object str2Obj(String str) throws Exception {
		if (str == null) {
			return null;
		}

		ObjectInputStream ois = null;
		try {
			byte[] data = Base64.decode(str.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
