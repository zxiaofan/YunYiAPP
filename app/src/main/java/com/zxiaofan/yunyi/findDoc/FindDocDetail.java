package com.zxiaofan.yunyi.findDoc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;
import com.zxiaofan.yunyi.registered.RegisteredDetail;

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
import util.SerializableMap;
import util.TitleBarUtils;
import util.ToastUtil;

/**
 * Describe:     医生信息
 * User:         xdxiaofan
 * Date:         2016/3/25 10:12
 */
public class FindDocDetail extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.iv_photo)
    ImageView ivPhoto;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_doctor_level)
    TextView tvDoctorLevel;
    @Bind(R.id.zhicheng)
    TextView zhicheng;
    @Bind(R.id.yiyuan)
    TextView yiyuan;
    @Bind(R.id.jianjie)
    TextView jianjie;
    @Bind(R.id.x1)
    LinearLayout x1;
    @Bind(R.id.x2)
    LinearLayout x2;
    @Bind(R.id.x3)
    LinearLayout x3;
    @Bind(R.id.x4)
    LinearLayout x4;
    @Bind(R.id.x5)
    LinearLayout x5;
    @Bind(R.id.x6)
    LinearLayout x6;
    @Bind(R.id.x7)
    LinearLayout x7;
    @Bind(R.id.a1)
    LinearLayout a1;
    @Bind(R.id.a2)
    LinearLayout a2;
    @Bind(R.id.a3)
    LinearLayout a3;
    @Bind(R.id.a4)
    LinearLayout a4;
    @Bind(R.id.a5)
    LinearLayout a5;
    @Bind(R.id.a6)
    LinearLayout a6;
    @Bind(R.id.a7)
    LinearLayout a7;
    @Bind(R.id.p1)
    LinearLayout p1;
    @Bind(R.id.p2)
    LinearLayout p2;
    @Bind(R.id.p3)
    LinearLayout p3;
    @Bind(R.id.p4)
    LinearLayout p4;
    @Bind(R.id.p5)
    LinearLayout p5;
    @Bind(R.id.p6)
    LinearLayout p6;
    @Bind(R.id.p7)
    LinearLayout p7;
    @Bind(R.id.n1)
    LinearLayout n1;
    @Bind(R.id.n2)
    LinearLayout n2;
    @Bind(R.id.n3)
    LinearLayout n3;
    @Bind(R.id.n4)
    LinearLayout n4;
    @Bind(R.id.n5)
    LinearLayout n5;
    @Bind(R.id.n6)
    LinearLayout n6;
    @Bind(R.id.n7)
    LinearLayout n7;
    private String id;
    private List<Map<String,Object>> arrayjobs=new ArrayList<>();
    @Bind(R.id.btn_date_up)
    Button btnDateUp;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.btn_date_down)
    Button btnDateDown;


    Calendar cal;
    SimpleDateFormat df;
    private Date firstDay;
    private Date endDay;
    private SimpleDateFormat df1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_find_doc_detail);
        ButterKnife.bind(this);
        init();
        super.onCreate(savedInstanceState);

    }

    private void init() {
        cal = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy/MM/dd");
        df1 = new SimpleDateFormat("yyyy-MM-dd");
        id = getIntent().getStringExtra("id").toString();
        initDate();
        initTitle();
        hoslist();
        initListener();
    }

    private void initDate() {
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        firstDay = cal.getTime();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        endDay = cal.getTime();
        Log.e("gnifeifeiing", df.format(firstDay));
        tvDate.setText(df.format(firstDay) + "-" + df.format(endDay));
    }

    private void initListener() {
        btnDateUp.setOnClickListener(this);
        btnDateDown.setOnClickListener(this);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("医生信息");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_date_up:


                rem();

//                cal.setTime(firstDay);
//                cal.add(Calendar.DAY_OF_MONTH, -7);
//                firstDay = cal.getTime();
//                cal.setTime(endDay);
//                cal.add(Calendar.DAY_OF_MONTH, -7);
//                endDay = cal.getTime();
                setDate(-7);
                hoslist();


                break;
            case R.id.btn_date_down:


                rem();

//                cal.setTime(firstDay);
//                cal.add(Calendar.DAY_OF_MONTH, 7);
//                firstDay = cal.getTime();
//                cal.setTime(endDay);
//                cal.add(Calendar.DAY_OF_MONTH, 7);
//                endDay = cal.getTime();
                setDate(7);
                hoslist();
                break;
            default:
                break;
        }
    }
    private void rem(){
        removeView(a1);
        removeView(a2);
        removeView(a3);
        removeView(a4);
        removeView(a5);
        removeView(a6);
        removeView(a7);
        removeView(p1);
        removeView(p2);
        removeView(p3);
        removeView(p4);
        removeView(p5);
        removeView(p6);
        removeView(p7);
        removeView(n1);
        removeView(n2);
        removeView(n3);
        removeView(n4);
        removeView(n5);
        removeView(n6);
        removeView(n7);
        removeView(n7);
    }

    /**
     * Describe:     计算七天的时间段
     * User:         xiaofan
     * Date:         2016/3/22 16:26
     */
    private void setDate(int poorDay) {
        cal.setTime(firstDay);
        cal.add(Calendar.DAY_OF_MONTH, poorDay);
        firstDay = cal.getTime();

        cal.setTime(endDay);
        cal.add(Calendar.DAY_OF_MONTH, poorDay);
        endDay = cal.getTime();
        tvDate.setText(df.format(firstDay) + "-" + df.format(endDay));
        //  Log.i("shijian",df1.format(firstDay)+"\n+;;;;"+df1.format(endDay));
        //ToastUtil.ToastShow(getBaseContext(),df1.format(firstDay)+"\n+;;;;"+df1.format(endDay),false);

    }


    @Override
    public int getLayoutId() {
        return 0;
    }
    private String zhuan(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E");
        String xingqi = format.format(date);
        return xingqi;
    }
    private void removeView(LinearLayout linearLayout) {
        //获取linearlayout子view的个数
        int count = linearLayout.getChildCount();

        //第count-1个是那个文字被置中的textview
        //因此，在remove的时候，只能操作的是0<location<count-1这个范围的
        //在执行每次remove时，我们从count-2的位置即textview上面的那个控件开始删除~
        if (count > 0) {
            //count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
            linearLayout.removeViewAt(0);
        }
    }

    @Override
    public void initView() {
        long time = System.currentTimeMillis();
       // Date date = new Date(time);
       // SimpleDateFormat format = new SimpleDateFormat("E");
        String xingqi = zhuan(time);
        switch (xingqi) {
            case "周一":
                x1.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周二":
                x2.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周三":
                x3.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周四":
                x4.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周五":
                x5.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周六":
                x6.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;
            case "周日":
                x7.setBackgroundColor(Color.parseColor("#9EE9BE"));
                break;


        }

    }

    @Override
    public void initAction() {

    }

    public void hoslist() {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "doctor/" + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("医生详情", response);
                        parsedoc(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                return map;
            }
        };


        IStringRequest requset1 = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "arrayJob",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("医生详情----", response);
                        parsedoc1(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("docId", id);
                map.put("dateStart", df1.format(firstDay));
                map.put("dateEnd", df1.format(endDay));

                return map;
            }
        };

        queue.add(requset);

        queue.add(requset1);

    }

    private void parsedoc1(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            arrayjobs=JsonUtils.getListMap(data.get("arrayjobs").toString());
            for ( int i=0;i<arrayjobs.size();i++){
                long l = Long.parseLong(arrayjobs.get(i).get("arrayjobDate").toString());
                String time=zhuan(l);
                switch (time) {
                    case "周一":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);

                            String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (MyApplication.loginFlag==true) {
                                            Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                            Map<String, Object> data = arrayjobs.get(j);
                                            SerializableMap tmpmap = new SerializableMap();
                                            tmpmap.setMap(data);

                                            intent.putExtra("arrayJob", tmpmap);

                                            startActivity(intent);

                                        }else {
                                            ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }

                            a1.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p1.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n1.addView(btn1);
                        }

                        break;
                    case "周二":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a2.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p2.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n2.addView(btn1);
                        }
                        break;
                    case "周三":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                             Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(getBaseContext(), RegisteredDetail.class);

                                        Map<String,Object> data=arrayjobs.get(j);
                                        SerializableMap tmpmap=new SerializableMap();
                                        tmpmap.setMap(data);

                                        intent.putExtra("arrayJob",tmpmap);

                                        startActivity(intent);

                                    }
                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a3.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        Intent intent=new Intent(getBaseContext(), RegisteredDetail.class);

                                        Map<String,Object> data=arrayjobs.get(j);
                                        SerializableMap tmpmap=new SerializableMap();
                                        tmpmap.setMap(data);

                                        intent.putExtra("arrayJob",tmpmap);

                                       startActivity(intent);

                                    }
                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p3.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n3.addView(btn1);
                        }
                        break;
                    case "周四":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a4.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p4.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);

                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n4.addView(btn1);
                        }
                        break;
                    case "周六":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a6.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p6.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n6.addView(btn1);
                        }
                        break;
                    case "周五":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a5.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);

                             String s1=arrayjobs.get(i).get("allowReservationNum").toString();

                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p5.addView(btn1);


                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);


                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);


                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n5.addView(btn1);
                        }
                        break;
                    case "周日":
                        if(arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("上午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            a7.addView(btn1);

                        }else if (arrayjobs.get(i).get("regisrationPeriodDn").toString().equals("下午")){
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            p7.addView(btn1);
                        }else {
                            Button btn1 = new Button(this);
                            ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1,
                                    ViewGroup.LayoutParams.WRAP_CONTENT-1);
                            btn1.setLayoutParams(vlp2);

                            btn1.setTextAppearance(getBaseContext(),R.style.table_btn_yes);
                          String s1=arrayjobs.get(i).get("allowReservationNum").toString();
                            int b = Integer.parseInt(s1);
                            if (b>0) {
                                btn1.setText("预约");
                                btn1.setBackgroundColor(Color.parseColor("#FF9900"));
                                btn1.setTextColor(Color.WHITE);
                                final int j=i;
                                            btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                            if (MyApplication.loginFlag==true) {
                                                Intent intent = new Intent(getBaseContext(), RegisteredDetail.class);

                                                Map<String, Object> data = arrayjobs.get(j);
                                                SerializableMap tmpmap = new SerializableMap();
                                                tmpmap.setMap(data);

                                                intent.putExtra("arrayJob", tmpmap);

                                                startActivity(intent);

                                            }else {
                                                ToastUtil.ToastShow(getBaseContext(),"您还没有登录,请登录",false);
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }


                                });
                            }else {
                                btn1.setText("约满");
                                btn1.setBackgroundColor(Color.parseColor("#CCCCCC"));


                            }
                            n7.addView(btn1);
                        }
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }











    private void parsedoc(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;

        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            tvDoctorName.setText(data.get("name").toString());
            zhicheng.setText(data.get("profeTitleDn").toString());
            yiyuan.setText(data.get("hosOrgName").toString() + "   " + data.get("occuRange").toString());
            jianjie.setText(data.get("resume").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
