package util;

import android.util.Log;

/**
 * coder by 背离记 on 2015/11/3.
 */
public class LogUtil {
    private static boolean LOG = true;

    public static void i(String TAG, String string) {
        if (LOG) {
            Log.i(TAG, string);
        }
    }

    public static void i(String TAG,int i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

    public static void i(String TAG,double i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

    public static void i(String TAG,boolean i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

}
