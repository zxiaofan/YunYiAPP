package base;

import android.content.Context;
import android.content.SharedPreferences;

import util.Constants;

/**
 * Describe:     sharepreference属性存储
 * User:         xiaofan
 * Date:         2016/3/18 16:32
 */
public class OptsharepreInterface {

    private SharedPreferences settings; // static

    public OptsharepreInterface(Context context) {

        // 载入配置文件
        settings = context.getSharedPreferences(Constants.SHARE_FILES,
                Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return settings.edit();
    }


    public void putPres(String optName, String values) {
        SharedPreferences.Editor editor = settings.edit();
        if (optName.equals("guid")) {
            editor.putString("guid", values);// 登录人guid
        } else if (optName.equals("account")) {
            editor.putString("account", values);// 登录账号
        } else if (optName.equals("name")) {
            editor.putString("name", values);// 登录用户名
        } else if (optName.equals("loginFlag")) {
            editor.putString("loginFlag", values);// 登录状态
        } else if (optName.equals("password")) {
            editor.putString("password", values);// 登录密码
        } else if (optName.equals("phonenumber")) {
            editor.putString("phonenumber", values);// 手机号
        } else if (optName.equals("unmsg")) {
            editor.putString("unmsg", values);// 未读消息数
        } else if (optName.equals("id")) {
            editor.putString("id", values);// 就诊人id
        } else if (optName.equals("isFirstLogin")) {
            editor.putString("isFirstLogin", values);// 是否首次登陆(0:是	1:否)
        } else if (optName.equals("token")) {
            editor.putString("token", values);//登录成功后返回的token值
        } else if (optName.equals("total")) {
            editor.putString("total", values);//就诊人的数量
        } else {
            editor.putString(optName, values);
        }

        editor.commit();
    }

    public String getPres(String optName) {
        String values = "";
        if (optName.equals("guid")) {// 获取登陆人guid
            values = settings.getString("guid", "");
        } else if (optName.equals("account")) {// 登录账号
            values = settings.getString("account", "0");
        } else if (optName.equals("password")) {// 登录密码
            values = settings.getString("password", "0");
        } else if (optName.equals("isFirstLogin")) {// 是否首次登陆
            values = settings.getString("isFirstLogin", "0");
        } else if (optName.equals("name")) {// 账户的名字
            values = settings.getString("name", "");
        } else if (optName.equals("total")) {// 就诊人的数量
            values = settings.getString("total", "0");
        } else if (optName.equals("id")) {// 就诊人的id
            values = settings.getString("id", "1");
        } else if (optName.equals("phonenumber")) {// 手机号
            values = settings.getString("phonenumber", "0");
        } else if (optName.equals("unmsg")) {// 手机号
            values = settings.getString("unmsg", "0");
        } else if (optName.equals("loginFlag")) {// 手机号
            values = settings.getString("loginFlag", "");
        } else if (optName.equals("token")) {//登录成功后返回token值
            values = settings.getString("token", "");
        } else {
            values = settings.getString(optName, "");
        }
        // System.out.println("读取配置文件操作------" + optName + "---" + values);
        return values;
    }


    public boolean existResult(String result) {
        return settings.contains(result);
    }

    public void removePre(String preName) {
        // 必须马上提交，否则就删不了？？！
        settings.edit().remove(preName).commit(); // .commit()
        // settings.edit().commit();
    }

}
