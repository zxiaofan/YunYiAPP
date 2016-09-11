package util;

import android.content.Context;

import base.OptsharepreInterface;

/**
 * Created by lenovo on 2016/3/17.
 */
public class Constants {
    /**
     * sharepre共享文件名
     */
    public static final String SHARE_FILES = "BXYY_FILES";
    /**
     * log输出标识
     */
    public static final String TAG = "gnifeifeiing";
    /**
     * 默认每页显示的item数量
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 网络请求失败监听
     */
    public static final String VOLLEY_ERROR = "请求失败";
    /**
     * 服务器地址
     */
    public static final String SERVER_ADDRESS = "http://zxiaofan.com:8080/MedicalAssistanService/apiService?wsdl";
    /**
     * 网络请求失败监听
     */
    public static final String XZQH_CODE = "xzqh_code";


    public static String getPatientId(Context context){
        OptsharepreInterface share = new OptsharepreInterface(context);
        share.getPres("PatientId");
        return "1";
    }

}
