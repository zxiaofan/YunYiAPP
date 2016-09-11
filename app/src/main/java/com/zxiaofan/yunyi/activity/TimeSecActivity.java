package com.zxiaofan.yunyi.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.adapter.MyAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;


/**
 * Describe:预约挂号时间选择
 * Created by ${苗}
 * on 2016/4/6.
 */

public class TimeSecActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    @Bind(R.id.text5)
    TextView text5;
    @Bind(R.id.text6)
    TextView text6;
    @Bind(R.id.text7)
    TextView text7;
    @Bind(R.id.listView10)
    ListView listView10;
    List<Map<String,Object>> list1;
    List<String> list=new ArrayList<>();
    private long time;
    private String mtime,mtime1;
    private Date date,date1,date2,date3,date4,date5,date6,date0,date7;
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.yuyuetime);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        text1.setSelected(true);


    }


    @Override
    public int getLayoutId() {
        return 0;
    }
    private Long getStartTime(){
           Calendar todayStart = Calendar.getInstance();
              todayStart.set(Calendar.HOUR, 0);
              todayStart.set(Calendar.MINUTE, 0);
             todayStart.set(Calendar.MILLISECOND, 0);
              return todayStart.getTime().getTime();
          }


    @Override
    public void initView() {
        initTitle();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);


       // System.out.println(cal.getTimeInMillis());
      //  time = cal.getTimeInMillis();
        time=getStartTime();
        long time0 = time - 10*24 * 60 * 60 * 1000;
        long time1 = time + 24 * 60 * 60 * 1000;
        long time2 = time + 2 * 24 * 60 * 60 * 1000;
        long time3 = time + 3 * 24 * 60 * 60 * 1000;
        long time4 = time + 4 * 24 * 60 * 60 * 1000;
        long time5 = time + 5 * 24 * 60 * 60 * 1000;
        long time6 = time + 6 * 24 * 60 * 60 * 1000;
        long time7 = time + 7 * 24 * 60 * 60 * 1000;
        date0 =new Date(time0);


        date  = new Date(time);
         date1 = new Date(time1);
        date2 = new Date(time2);
         date3 = new Date(time3);
        date4 = new Date(time4);
         date5 = new Date(time5);
        date6 = new Date(time6);
        date7 = new Date(time7);





        text1.setText(riqi(date));
        text2.setText(riqi(date1));
        text3.setText(riqi(date2));
        text4.setText(riqi(date3));
        text5.setText(riqi(date4));
        text6.setText(riqi(date5));
        text7.setText(riqi(date6));

        list.add("上午");
        list.add("下午");
        list.add("晚上");
        initData(1);




        text2.setOnClickListener(this);
        text1.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
        text7.setOnClickListener(this);

    }



    private String riqi(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("E");
        String xingqi = format.format(date);
        format = new SimpleDateFormat("MM-dd");
        String riqi = format.format(date);

        return xingqi + "\n" + riqi;
    }
    private String riqi1(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        String xingqi = format.format(date);

        Log.i("time",xingqi);
        return xingqi;
    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("预约挂号");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void changeStatus(int position) {
        text1.setBackgroundColor(Color.parseColor("#ffffff"));
        text2.setBackgroundColor(Color.parseColor("#ffffff"));
        text3.setBackgroundColor(Color.parseColor("#ffffff"));
        text4.setBackgroundColor(Color.parseColor("#ffffff"));
        text5.setBackgroundColor(Color.parseColor("#ffffff"));
        text6.setBackgroundColor(Color.parseColor("#ffffff"));
        text7.setBackgroundColor(Color.parseColor("#ffffff"));
        switch (position) {
           case 1:
                text1.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(1);
                break;
            case 2:
                text2.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(2);
                break;
            case 3:
                text3.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(3);
                break;
            case 4:

                text4.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(4);
                break;
            case 5:
                text5.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(5);
                break;
            case 6:
                text6.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(6);
                break;
            case 7:
                text7.setBackgroundColor(Color.parseColor("#C2E8D4"));
                initData(7);
                break;
            default:
                break;
        }

    }
    private void initData(final int postion){
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS+"arrayJob",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("haoyuan",response);
                        pb.dismiss();
                         parsehaoyuan(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(),"开发中，敬请期待 . . .",true);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

               // map.put("hosId","1");
             //   map.put("deptCode","");
                switch (postion){
                    case 1:
                        mtime=riqi1(date);
                        mtime1=riqi1(date1);
                        break;
                    case 2:
                        mtime=riqi1(date1);
                        mtime1=riqi1(date2);
                        break;
                    case 3:
                        mtime=riqi1(date2);
                        mtime1=riqi1(date3);
                        break;
                    case 4:
                        mtime=riqi1(date3);
                        mtime1=riqi1(date4);
                        break;
                    case 5:
                        mtime=riqi1(date4);
                        mtime1=riqi1(date5);
                        break;
                    case 6:
                        mtime=riqi1(date5);
                        mtime1=riqi1(date6);
                        break;
                    case 7:
                        mtime=riqi1(date6);
                        mtime1=riqi1(date7);
                        break;


                }
               map.put("dateStart",mtime);
                map.put("dateEnd",mtime1);
                return map;

            }
        };



        queue.add(requset);

    }

    private void parsehaoyuan(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;

        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            list1=JsonUtils.getListMap(data.get("arrayjobs").toString());
            listView10.setAdapter(new MyAdapter(this,list,list1));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }



    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.text1:
          changeStatus(1);
             break;
         case R.id.text2:

             changeStatus(2);
             break;
         case R.id.text3:
             changeStatus(3);

             break;
         case R.id.text4:
             changeStatus(4);

             break;
         case R.id.text5:
             changeStatus(5);

             break;
         case R.id.text6:
             changeStatus(6);

             break;
         case R.id.text7:
             changeStatus(7);
             break;


     }
    }


}
