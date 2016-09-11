package util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zxiaofan.yunyi.MyApplication;


public class NetUtil {
static String TAG = "NetUtil";
    /**
     * 不可用已处理，自定义处理请接收返回值
     * @return
     */
    public static boolean isNetWork() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
            if (info!=null&&info.isAvailable()) {
                LogUtil.i(TAG, "网络可用");
                return true;
            } else {
               // UIHelper.ToastUtil("无网络,请检查网络连接");
            }
            return false;
        }

    /**
     * 工作的不好
     * @return
     */
    public static boolean isWifiWork() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info!=null&&info.isAvailable()) {
                LogUtil.i(TAG, "WIFI可用");
                return true;
            } else {
                LogUtil.i(TAG, "WIFI不可用");
                return false;
            }
        }

    /**
     * 工作的不好
     * @return
     */
    public static boolean isMobileWork() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info!=null&&info.isAvailable()) {
                LogUtil.i(TAG, "移动网络可用");
                return true;
            } else {
                LogUtil.i(TAG, "移动网络不可用");
                return false;
            }
        }

    public static int getConnetType(Context context){
        if(context!=null){
            ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info!=null&&info.isAvailable()){
                switch (info.getType()){
                    case ConnectivityManager.TYPE_MOBILE:
                        LogUtil.i(TAG,"移动网络");
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        LogUtil.i(TAG,"WIFI");
                        break;
                }
                return info.getType();
            }
        }
        return -1;
    }

}
