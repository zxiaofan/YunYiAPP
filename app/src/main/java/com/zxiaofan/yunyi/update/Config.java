package com.zxiaofan.yunyi.update;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
* Describe:     版本更新参数
* User:         xiaofan
* Date:         2016/4/12 11:10
*/
public class Config {

	public static final  String UPDATE_SERVER = "http://zxiaofan.com:9401/";//服务器下载地址
	public static final  String SERVER_APP_NAME = "yunyi.apk";//服务器下载的app名称
	public static final  String LOCAl_PACKAGE_NAME = "com.zxiaofan.yunyi";//包名
	//sd/PeoHosp/njztc_normal.apk
	public static final  String SD_FILE_NAME = "yunyi";//sd卡下载的文件夹名称
	public static final  String APP_NAME = "医疗助手";//服务器下载的app名称
	/**
	 * 获取版本号（PS：1）
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(LOCAl_PACKAGE_NAME,
					0).versionCode;
		} catch (NameNotFoundException e) {
		}
		return verCode;
	}

	/**
	 * 获取版本名称（PS：1.0.2）
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(LOCAl_PACKAGE_NAME,
					0).versionName;
		} catch (NameNotFoundException e) {
		}
		return verName;

	}

	/**
	 * strings.xml
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context,int appName) {
		String verName = context.getResources().getText(appName)
				.toString();
		return verName;
	}
	
	/** 
     * 
     *  
     * @param ipInt 
     * @return 
     */  
    public static String int2ip(int ipInt) {  
        StringBuilder sb = new StringBuilder();  
        try {
        	sb.append(ipInt & 0xFF).append(".");  
            sb.append((ipInt >> 8) & 0xFF).append(".");  
            sb.append((ipInt >> 16) & 0xFF).append(".");  
            sb.append((ipInt >> 24) & 0xFF);  
		} catch (Exception e) {
			return String.valueOf(ipInt);
		}
        return sb.toString();  
    }  
  
    /** 
     * 
     *  
     * @param context 
     * @return 
     */  
    public static String getLocalIpAddress(Context context) {  
        try {  
            WifiManager wifiManager = (WifiManager) context  
                    .getSystemService(Context.WIFI_SERVICE);  
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();  
            return int2ip(i);  
        } catch (Exception ex) { 
            return " " + ex.getMessage();  
        }  
    }  
}
