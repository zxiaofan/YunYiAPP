package com.zxiaofan.yunyi.update;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import util.JsonUtils;

/**
 * Describe:     检查更新异步任务
 * User:         xiaofan
 * Date:         2016/4/12 10:21
 */
@SuppressLint("NewApi")
public class CheckVersionTask extends AsyncTask<Void, Integer, Boolean> {

    private int newVerCode = -1;
    private String newVerName = "";
    private Context currentContext;
    private int curVerCode;
    private int type; //
    public static String UPDATE_SERVER;//更新的服务器地址
    public static String UPDATE_VERJSON;//更新的版本号
    public static String SERVER_APP_NAME;//更新的app名称
    public static String UPDATE_SAVENAME;
    private TaskInf inf;
    Map<String, Object> object = null;
    Map<String, Object> backObj = null;

    public CheckVersionTask(Context context) {
        this.currentContext = context;
        this.UPDATE_SERVER =Config.UPDATE_SERVER;
        this.SERVER_APP_NAME = Config.SERVER_APP_NAME;
        this.curVerCode = Config.getVerCode(context);//当前应用版本号
    }

    public void setListener(TaskInf inf) {
        this.inf = inf;
    }

    private Integer getServerVerCode() {
        try {
//			String verjson = NetworkTool.getContent(UPDATE_SERVER
//					+ UPDATE_VERJSON);
            String verjson = "{'success':'1','msg':'成功','data':{'version':'5720','versionName':'1.1（build:2016-06-02）','content':'修复bug'}}";

            object = JsonUtils.getMapObj(verjson);
            if (object.get("success").toString().equals("0")) {
                return 0;
            } else if(object.get("success").toString().equals("1")) {
                object = JsonUtils.getMapObj(object.get("data").toString());
                return 1;
            }else{
                return 2;
            }
        } catch (Exception e) {
            return 2;
        }
    }

    @Override
    protected void onPreExecute() {
        inf.onPreExecute();
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (getServerVerCode()==1) {
            newVerCode=Integer.parseInt(object.get("version").toString());//获取服务器存储的版本号
            if (newVerCode > curVerCode) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        backObj=new HashMap<>();
        if (result) {
            backObj.put("version",object.get("version").toString());//更新版本号
            backObj.put("curVerName",Config.getVerName(currentContext));//当前版本名称1.0.2
            backObj.put("newVerName",object.get("versionName").toString());//更新版本名称1.0.3
            backObj.put("content",object.get("content").toString());//更新内容
            backObj.put("checkTime", String.valueOf(System.currentTimeMillis()));//更新时间
            backObj.put("isUpdate",true);//是否更新
        } else {
            backObj.put("isUpdate",false);
        }
        inf.isSuccess(backObj);
    }
}
