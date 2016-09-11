package com.zxiaofan.yunyi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zxiaofan.yunyi.activity.FindDocByDisease;
import com.zxiaofan.yunyi.activity.FindHosByDisease;
import com.zxiaofan.yunyi.activity.JBZZView;
import com.zxiaofan.yunyi.fragment.Frag_User;

import java.util.TreeMap;

import util.ToastUtil;
import widget.ProgressDialogStyle;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by lenovo on 2016/6/4.
 */
public class ServiceAPI extends  Activity{

    private static final String TAG = "ServiceAPI";

    public void login(String name, String pwd, final Context context) {
//        final Dialog pb = ProgressDialogStyle.createLoadingDialog(context, "正在登录...");
//        pb.show();
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("arg0", name);
        params.put("arg1", pwd);
        String action = "login";
        SoapHelper.start(action, params, new SoapHelper.Callback() {

            @Override
            public void onSuccess(String text) {
                System.out.println("请求成功：" + text);
                boolean bool = "true".equals(text) ? true : false;
                if (bool) {
//                    pb.dismiss();
                    ToastUtil.show(context, "登录成功!");
//                    Intent intent=new Intent(context, Frag_User.class);
//                    startActivity(intent);
                } else {
//                    pb.dismiss();
                    ToastUtil.show(context, "登录失败!");
                }
            }

            @Override
            public void onStart() {
//                System.out.println("开始请求");
                ToastUtil.show(context, "登录中...");
            }

            @Override
            public void onError(int errNo) {
                System.out.println("请求错误，错误码：" + errNo);
            }
        });
    }

    public void queryDiseaseBySymptom(final String param, final Context context) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("arg0", param);//"[流口水]"
        SoapHelper.start("queryDiseaseBySymptom", params, new SoapHelper.Callback() {
            @Override
            public void onSuccess(String text) {
                Log.i(TAG, "onSuccess: " + text);
                Intent intent = new Intent(context, JBZZView.class);
                if (intent == null) {
                    Log.i(TAG, "intent==null ");
                }
                intent.putExtra("data", text);
                intent.putExtra("param", param);
                context.startActivity(intent);
            }

            @Override
            public void onStart() {
                System.out.println("开始请求");
                ToastUtil.show(context, "查询中...");
            }

            @Override
            public void onError(int errNo) {
                System.out.println("请求错误，错误码：" + errNo);
            }
        });
    }
    public void register(final String param, final Context context) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("arg0", param);
        SoapHelper.start("register", params, new SoapHelper.Callback() {
            @Override
            public void onSuccess(String text) {
                Log.i(TAG, "onSuccess: " + text);
                ToastUtil.show(context, "注册成功！");
            }

            @Override
            public void onStart() {
                System.out.println("开始请求");
            }

            @Override
            public void onError(int errNo) {
                System.out.println("请求错误，错误码：" + errNo);
                ToastUtil.show(context, "注册失败");
            }
        });
    }
    public void queryHospitalsByDisease(final String param, final Context context) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("arg0", param);
        SoapHelper.start("queryHospitalsByDisease", params, new SoapHelper.Callback() {
            @Override
            public void onSuccess(String text) {
                Log.i(TAG, "onSuccess: " + text);
                ToastUtil.show(context, "查询医院成功！");
                Intent intent = new Intent(context, FindHosByDisease.class);
                if (intent == null) {
                    Log.i(TAG, "intent==null ");
                }
                intent.putExtra("data", text);
                intent.putExtra("param", param);
                context.startActivity(intent);
            }

            @Override
            public void onStart() {
                System.out.println("开始请求");
            }

            @Override
            public void onError(int errNo) {
                System.out.println("请求错误，错误码：" + errNo);
                ToastUtil.show(context, "查询医院失败，请输入正确的疾病名！");
            }
        });
    }
    public static void queryDoctorsByDisease(final String param,final Context context) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("arg0", param);
        SoapHelper.start("queryDoctorsByDisease", params, new SoapHelper.Callback() {
            @Override
            public void onSuccess(String text) {
                Log.i(TAG, "onSuccess: " + text);
                ToastUtil.show(context, "查询医生成功！");
                Intent intent = new Intent(context, FindDocByDisease.class);
                if (intent == null) {
                    Log.i(TAG, "intent==null ");
                }
                intent.putExtra("data", text);
                intent.putExtra("param", param);
                context.startActivity(intent);
            }

            @Override
            public void onStart() {
                System.out.println("开始请求");
            }

            @Override
            public void onError(int errNo) {
                System.out.println("请求错误，错误码：" + errNo);
                ToastUtil.show(context, "查询医院失败，请输入正确的疾病名！");
            }
        });
    }


}
