
package com.example.formapp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.formapp.app.AppContext;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	private static Dialog mProgressDialog;
	private static RotateAnimation mRotateAnimation;
	private static Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]*$");

	public  static void makeTransparentStatusBar(Activity activity) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.setNavigationBarColor(Color.TRANSPARENT);
		}
	}

	/**
	 * ??????????????????
	 */
	private static long lastClickTime;
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * @param
	 * @return void
	 * @throws
	 * @Description: ???????????????Toast
	 */
	public static void shortToast(CharSequence sequence) {
		Toast.makeText(AppContext.getInstance(), sequence, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @param
	 * @return void
	 * @throws
	 * @Description: ???????????????Toast
	 */
	public static void longToast(CharSequence sequence) {
		Toast.makeText(AppContext.getInstance(), sequence, Toast.LENGTH_SHORT).show();
	}
	
	// View?????????
	public static int[] getLocation(View v) {
	    int[] loc = new int[4];
	    int[] location = new int[2];
	    v.getLocationOnScreen(location);
	    loc[0] = location[0];
	    loc[1] = location[1];
	    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    v.measure(w, h);

	    loc[2] = v.getMeasuredWidth();
	    loc[3] = v.getMeasuredHeight();

	    //base = computeWH();
	    return loc;
	}
	
	public static int[] getWH(View v) {
	    int[] location = new int[2];
	   
	    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    v.measure(w, h);

	    location[0] = v.getMeasuredWidth();
	    location[1] = v.getMeasuredHeight();

	    return location;
	}

	/**
	 * ????????????activity????????????
	 *
	 * @param pClass
	 *            ????????????activity
	 */
	public static void startActivity(Context context, Class<?> pClass) {
		Intent intent = new Intent(context, pClass);
		context.startActivity(intent);
	}

	/**
	 * ????????????activity ??????Serializable??????
	 *
	 * @param pClass
	 *            ????????????activity
	 * @param name
	 *            ??????
	 * @param value
	 *            ?????? ??????????????????????????????
	 */
	public static void startActivity(Context context, Class<?> pClass,
			String name, Serializable value) {
		Intent intent = new Intent(context, pClass);
		intent.putExtra(name, value);
		context.startActivity(intent);
	}

	/**
	 * ????????????activity,??????bundle??????
	 *
	 * @param clazz
	 *            ????????????activity
	 * @param bundle
	 */
	public static void startActivity(Context context, Class<?> clazz,
			Bundle bundle) {
		Intent intent = new Intent(context, clazz);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}




	/**
	 * ???????????????????????????????????????????????????ASCII???
	 *
	 * @param input
	 * @return
	 */
	public static int character2ASCII(String input) {
		char[] temp = input.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char each : temp) {
			builder.append((int) each);
		}
		String result = builder.toString();
		return Integer.parseInt(result);
	}

	/**
	 * ???textView????????????
	 *
	 * @param context
	 * @param resId
	 *            ??????id
	 * @return
	 */
	public static Drawable setTextViewImage(Context context, int resId) {
		Drawable drawable = ContextCompat.getDrawable(context,resId);
		// / ?????????????????????,??????????????????.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		return drawable;
	}

	/**
	 * hprof-conv dump.hprof converted-dump.hprof
	 *
	 * @param context
	 * @return
	 */
	public static boolean createDumpFile(Context context) {
		String LOG_PATH = "/dump.gc/";
		boolean bool = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ssss");
		String createTime = sdf.format(new Date(System.currentTimeMillis()));
		String state = Environment.getExternalStorageState();
		// ??????SdCard??????????????????????????????
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + LOG_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			String hprofPath = file.getAbsolutePath();
			if (!hprofPath.endsWith("/")) {
				hprofPath += "/";
			}

			hprofPath += createTime + ".hprof";
			try {
				android.os.Debug.dumpHprofData(hprofPath);
				bool = true;
				Log.d("ANDROID_LAB", "create dumpfile done!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			bool = false;
			Log.d("ANDROID_LAB", "nosdcard!");
		}

		return bool;
	}

	/********************************** jpush???????????? *********************************/

	public static final String PREFS_NAME = "JPUSH_EXAMPLE";
	public static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
	public static final String PREFS_START_TIME = "PREFS_START_TIME";
	public static final String PREFS_END_TIME = "PREFS_END_TIME";
	public static final String KEY_APP_KEY = "JPUSH_APPKEY";

	public static boolean isEmpty(String s) {
		if (null == s) {
            return true;
        }
		if (s.length() == 0) {
            return true;
        }
		if (s.trim().length() == 0) {
            return true;
        }
		return false;
	}

	// ??????Tag Alias ???????????????,?????????????????????
	public static boolean isValidTagAndAlias(String s) {
		Matcher m = p.matcher(s);
		return m.matches();
	}

	// ??????AppKey
	public static String getAppKey(Context context) {
		Bundle metaData = null;
		String appKey = null;
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
                metaData = ai.metaData;
            }
			if (null != metaData) {
				appKey = metaData.getString(KEY_APP_KEY);
				if ((null == appKey) || appKey.length() != 24) {
					appKey = null;
				}
			}
		} catch (NameNotFoundException e) {

		}
		return appKey;
	}

	// ???????????????
	public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}

	public static void showToast(final String toast, final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}).start();
	}
	public static boolean isConnected(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);//??????????????????????????????????????????
		NetworkInfo info = conn.getActiveNetworkInfo();//?????????????????????????????????
		return (info != null && info.isConnected());
}

	/********************************** jpush???????????? *********************************/

	public static String dateF(String date, boolean showS, boolean showHM) {
		date = date.substring(date.indexOf("(") + 1, date.indexOf(")"));

		SimpleDateFormat format;
		if (date.length() == 10) {
			date = date + "000";
		}
		if (showS) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			if (showHM) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else {
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
		}
		try {
			Long time = new Long(date);
			String d = format.format(time);
			return d;
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * ??????????????????????????????
	 * 
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllApps(Context context) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		// ???????????????????????????
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);//??????permission
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			// ?????????????????????????????????????????????
			if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				apps.add(pak);
			}

		}
		return apps;
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context, int type) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			if (type == 1) {
				return String.valueOf(pi.versionCode);
			} else {
				return pi.versionName;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Description:???????????????
	 * @return ?????????
	 * @modified by @time
	 */
	public static String getVersionName(Context context) {
		// ?????????????????????apk
		PackageManager pManager = context.getPackageManager();
		// ????????????apk???????????????
		try {
			PackageInfo pInfo = pManager.getPackageInfo(
					context.getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getIntVersion(Context context) {
		return version2Int(getVersionName(context));
	}
	public static int version2Int(String version) {
		// 1.0.2
		String[] strVersion = version.split("\\.");
		int intVersion = 0;
		for (int i = 0; i < strVersion.length; i++) {
			intVersion += Integer.parseInt(strVersion[i])
					* Math.pow(10, strVersion.length - 1 - i);
		}
		return intVersion;
	}
	// ??????Service??????????????????????????????????????????
	public static boolean messageServiceIsStart(
			List<ActivityManager.RunningServiceInfo> mServiceList,
			String className) {
		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static <T> List<T> reverseList(List<T> rawList) {
		List<T> resultList = new ArrayList<T>();
		ListIterator<T> li = rawList.listIterator();
		// ??????????????????????????????
		for (li = rawList.listIterator(); li.hasNext();) {
			li.next();
		}
		// ??????????????????????????????
		for (; li.hasPrevious();) {
			resultList.add(li.previous());
		}
		return resultList;
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int resId) {
		Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * ??????????????????????????? dp ????????? ????????? px(??????)
	 */
	public static int dip2px(float dpValue) {
		final float scale = AppContext.getInstance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int dp2px(Context context, float dpVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}
	/**
	 * ??????????????????????????? px(??????) ????????? ????????? dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;//?????????????????????????????????????????????
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ???????????????
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone) {
		Pattern pattern = Pattern.compile("^(1[3-9][0-9])\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	/**
	 * ??????1?????????
	 * @param value
	 * @param digits ?????????0??????1??????2??????
	 * @return
	 */
	public static String formatFloatNumber(double value, int digits) {
		String str = "";
		if (digits == 0) {
			DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
			str = decimalFormat.format(value);
		} else if (digits == 1) {
			str = String.format("%.1f", value);
		} else if (digits == 2) {
			str = String.format("%.2f", value);
		}
		return str;
	}

	/**
	 * ??????editText????????????
	 * 1???????????????????????????
	 * 2?????????????????????
	 * 3?????????????????????
	 * @param editText
	 */
	public static void setInputFilter(EditText editText) {
		InputFilter spaceFilter = (source, start, end, dest, dstart, dend) -> {
			if(source.equals(" ")) {
				return "";
			} else {
				return null;
			}
		};
		InputFilter specialFilter = (source, start, end, dest, dstart, dend) -> {
			String speChat="[`~???!@#$%^&*()+\\-=_|{}':;,\\[\\].<>??????/?~???@#???%??????&*????????????+|{}????????????????????????????????????]";
			Pattern pattern = Pattern.compile(speChat);
			Matcher matcher = pattern.matcher(source.toString());
			if(matcher.find()) {
				return "";
			} else {
				return null;
			}
		};
		InputFilter numberFilter = (source, start, end, dest, dstart, dend) -> {
			String speChat="[0123456789]";
			Pattern pattern = Pattern.compile(speChat);
			Matcher matcher = pattern.matcher(source.toString());
			if(matcher.find()) {
				return "";
			} else {
				return null;
			}
		};
		if (null != editText) {
			editText.setFilters(new InputFilter[]{spaceFilter,specialFilter,numberFilter});
		}
	}
}