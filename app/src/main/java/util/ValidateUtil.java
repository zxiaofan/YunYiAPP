package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 控件验证
 * @author: xiaofan
 */
public class ValidateUtil {
    /**
    * Describe:     验证是否是手机号
    * User:         xiaofan
    * Date:         2016/3/18 11:30
    */
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

        }

    /**
    * Describe:    验证邮箱格式
    * User:         xiaofan
    * Date:         2016/3/18 11:31
    */
    public static boolean isEmail(CharSequence email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
    * Describe:     判断账户格式是否正确（只能是字母或数字，长度在5-15之间）
    * User:         xiaofan
    * Date:         2016/3/18 11:33
    */
    public static boolean checkAccount(String userName) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z][0-9A-Za-z]{5,15}$");
        Matcher m = p.matcher(userName);
        return m.matches();
    }
    /**
    * Describe:     密码格式是否正确（只能是数字、字母、逗号句号）
    * User:         xiaofan
    * Date:         2016/3/18 11:33
    */  
    public static boolean checkPassWord(String passWord) {
        Pattern p = Pattern.compile("^(?![0-9]+$)[a-zA-Z0-9,.]*$");
        Matcher m = p.matcher(passWord);
        return m.matches();
    }
}
