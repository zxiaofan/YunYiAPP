package com.zxiaofan.yunyi.update;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class NetworkTool{

	/**
	* Describe:     获取服务器xml版本信息
	* User:         xiaofan
	* Date:         2016/4/12 10:23
	*/
	public static String getContent(String url) throws Exception{
		StringBuilder sb = new StringBuilder();

		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if(entity != null){
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"), 8192);

			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
			}
			reader.close();
		}
		return sb.toString();
	}

	public static void postContent(String url, File file) throws Exception{
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		HttpEntity entity = new FileEntity(file, "binary/octet-stream");
		httpPost.setEntity(entity);
		client.execute(httpPost);
	}

	/**
	 * 检测网络状况
	 * @return
	 */
	public static boolean checkConnection(Context mContext) {
		boolean netState = false;//网络连接失败！
		@SuppressWarnings("static-access")
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
		System.out.println("网络监测：" + (connectivityManager == null) + ","+ (networkinfo == null));

		if (connectivityManager != null && networkinfo != null) {
			netState = true;//当前网络可用
			//new Toast(Context.this).makeText(context.this, "检查到没有可用的网络,请打开网络连接！",
			//		Toast.LENGTH_LONG).show();
		}
		return netState;
	}

	/**
	 * 检查GPS状态
	 */
	private static LocationManager locMgr;
	public static int checkGpState(Context context) {

		int gpsResult = 0;//0、未打开1、已打开
		locMgr = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (!locMgr.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			gpsResult = 0;
		}
		if (locMgr.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			gpsResult = 1;
		}
		return gpsResult;
	}

	/**
	 * 检查SIM卡是否可用
	 */
	public static int checkSimState(Context context) {
		int simResult = 0;//0、SIM卡不可用1、无法读取SIM卡状态2、SIM卡可用
		TelephonyManager telPhoneMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE); // 检查Sim卡状态 int
		int simState = telPhoneMgr.getSimState();
		if (!(simState == TelephonyManager.SIM_STATE_READY)) {
			if (simState == TelephonyManager.SIM_STATE_ABSENT) {
				simResult = 0;
				//simStateAlter = "SIM卡不可用，请重新装入SIM卡，或者更换新卡！";
			} else {
				simResult = 1;
				//simStateAlter = "无法获取SIM卡状态，请重新装入SIM卡，或者更换新卡！";
			}
		} else{
			simResult = 2;
		}
		return simResult;
	}

	/**
	 * 判断服务是否运行
	 *
	 * @return
	 */
	public static  boolean isServiceRunning(Context context,String serviceName) {
		ActivityManager myManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
				.getRunningServices(Integer.MAX_VALUE);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(serviceName)) {
				return true;
			}
		}
		return false;
	}
}
