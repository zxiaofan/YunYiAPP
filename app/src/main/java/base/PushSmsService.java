package base;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.activity.MainActivity;

import java.util.List;
import java.util.Map;

import bean.MessageBean;
import db.DataBaseManager;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;

/**
 * Describe:
 * Created by ${苗}
 * on 2016/4/8.
 */

public class PushSmsService extends Service {

    private MyThread myThread;

    private NotificationManager manager;

    private Notification notification;

    private PendingIntent pi;



    private boolean flag = true;
    private DataBaseManager db;
    private MessageBean message=new MessageBean();
    String token;
    private OptsharepreInterface o;;



    @Override

    public IBinder onBind(Intent intent) {

        // TODO Auto-generated method stub

        return null;

    }



    @Override

    public void onCreate() {

        System.out.println("oncreate()");
        o=new OptsharepreInterface(getApplicationContext());
        token = o.getPres("token");

       // this.client = new AsyncHttpClient();

        this.myThread = new MyThread();

        this.myThread.start();

        super.onCreate();

    }



    @Override

    public void onDestroy() {

        this.flag = false;

        super.onDestroy();

    }



    private void notification(String content, String number, String date) {

        // 获取系统的通知管理器

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification(R.drawable.user_ic, content,

                System.currentTimeMillis());

        notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯

        notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失

        notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除



        Intent intent = new Intent(getApplicationContext(),

                MainActivity.class);

        intent.putExtra("content", content);

        intent.putExtra("number", number);

        intent.putExtra("date", date);



        pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);



        notification.setLatestEventInfo(getApplicationContext(), number

                + "有新的消息", content, pi);



        // 将消息推送到状态栏

        manager.notify(0, notification);



    }



    private class MyThread extends Thread {

        @Override

        public void run() {

                String url = "http://110.65.99.66:8080/jerry/PushSmsServlet";

                while (flag) {

                    System.out.println("发送请求");

                    try {

                        // 每个10秒向服务器发送一次请求

                        Thread.sleep(10000);

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }


                    // 采用get方式向服务器发送请求
                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    IStringRequest requset = new IStringRequest(Request.Method.GET,
                            Constants.SERVER_ADDRESS + "usermessage/unread?token=" + token,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("service", response);
                                    //parseLogin(response);
                                    insertdb(response);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                   // ToastUtil.ToastShow(getBaseContext(), "服务器好像出了点问题", true);

                                }
                            }
                    );
                    queue.add(requset);


                }

            }


    }
    private void insertdb(String response) {
        Map<String, Object> object = null;
        List< Map<String, Object>> data = null;
        try {
            object= JsonUtils.getMapObj(response);
            data = JsonUtils.getListMap(object.get("data").toString());
//            Message msg=Message.obtain();
//            msg.arg1=data.size();
//            msg.what=0x123;
// Log.i("sizem",msg.arg1+"");
           //handler.sendMessage(msg);

            Log.i("size",data.size()+"");
            if (data.size()>0) {
                o.putPres("unmsg", data.size() + "");
                Intent intent=new Intent();
                intent.putExtra("msg",data.get(0).get("msg").toString());
                intent.putExtra("i", data.size());
                intent.setAction("android.intent.action.test");//action与接收器相同
                sendBroadcast(intent);


            }

            for (int i=0;i<data.size();i++){
                message.setContext1(data.get(i).get("msg").toString());
                message.setContext(data.get(i).get("msg").toString());
                message.setMesid(data.get(i).get("id").toString());
                message.setMessagetype(data.get(i).get("msgtype").toString());
                message.setUpdate1(data.get(i).get("createtime").toString());
                db= DataBaseManager.getInstance(getBaseContext());
                db.insertData1("m"+ MyApplication.phone,message);
                db.getDataCounts("m"+ MyApplication.phone);



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
