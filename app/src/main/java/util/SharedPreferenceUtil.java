package util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mr.Wang on 2015/11/12 15:01
 */
public class SharedPreferenceUtil {


    /*
    * String key:键
    * String defaultValue:默认返回值
    *
    * 默认返回String类型,可通过parse解析为需要得到的相应类型
     */
    public static String readString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, defaultValue);
        sharedPreferences = null;
        return value;
    }

    /*
   * String key:键
   * String value:从配置文件中读取到的值
   *
   * 默认写入String类型,可通过parse解析把需要传入的值转为String类型
    */
    public static boolean writeString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        boolean isSuccess = editor.commit();
        sharedPreferences = null;
        editor = null;
        return isSuccess;
    }

    /*
    *清除
    * String key:键
    */
    public static boolean clean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        boolean isSuccess = editor.commit();
        sharedPreferences = null;
        editor = null;
        return isSuccess;
    }

}
