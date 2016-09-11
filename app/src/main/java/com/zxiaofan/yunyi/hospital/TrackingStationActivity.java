package com.zxiaofan.yunyi.hospital;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
* Describe:     叫号跟踪
* User:         xiaofan
* Date:         2016/4/14 17:02
*/
public class TrackingStationActivity extends BaseActivity {

    @Bind(R.id.call_num_list)
    RecyclerView callNumList;
    @Bind(R.id.btn_refresh)
    Button btnRefresh;
    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;

    private Dialog pb;

    List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> myLists = new ArrayList<Map<String, Object>>();
    Map<String, Object> object = null;
    //机构名（医院名）
    String hosOrgName;
    //机构码（医院id）
    String hosOrgCode;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_station);
        ButterKnife.bind(this);
        hosOrgName = getIntent().getStringExtra("hosname");
        hosOrgCode = getIntent().getStringExtra("hosOrgCode");
        init();
    }

    private void init() {
        initTitle();
        initListener();
        initData();
    }

    /**
     * Describe:     刷新监听
     * User:         xiaofan
     * Date:         2016/4/14 16:58
     */
    private void initListener() {
        tvHospitalName.setText(hosOrgName);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.clear();
                myLists.clear();
                handler.removeCallbacks(runnable);
                initData();
            }
        });
    }

    /**
     * Describe:     查询该医院所有门诊的跟踪号码
     * User:         xiaofan
     * Date:         2016/4/14 11:35
     */
    private void initData() {
        pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "his/getCallNoOut?hosOrgCode=" + hosOrgCode + "&hosOrgName=" + hosOrgName;
        Log.e(Constants.TAG,url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(TrackingStationActivity.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     格式化叫号数据
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void loadData(String json) {
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                lists = JsonUtils.getListMap(object.get("data").toString());
                for (Map<String, Object> obj : lists) {
                    myLists.add(obj);
                }
                formatData();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
        }
    }

    private void formatData() {
        adapter = new MyAdapter();
        callNumList.setLayoutManager(new LinearLayoutManager(this));
        callNumList.setAdapter(adapter);
        handler.post(runnable);


    }

    /**
    * Describe:     刷新时间
    * User:         xiaofan
    * Date:         2016/4/14 17:03
    */
    private Handler handler = new Handler();
    int time, adaptertime, hour, minute, sec;
    public Runnable runnable = new Runnable() {

        public void run() {
            for (Map<String, Object> obj : lists) {
                if (obj.get("time") == null) {
                    obj.put("time", 1);
                } else {
                    time = Integer.parseInt(obj.get("time").toString());
                    time++;
                    obj.put("time", time);
                }
                myLists.add(obj);
            }

            adapter.notifyDataSetChanged();
            handler.postDelayed(runnable, 1000);
        }
    };

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(TrackingStationActivity.this).inflate(R.layout.call_number_list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                //当前号
                if (TextUtils.isEmpty(myLists.get(position).get("currentNo").toString())) {
                    ((MyHolder) holder).tv_call_num.setText("无");
                } else {
                    ((MyHolder) holder).tv_call_num.setText(myLists.get(position).get("currentNo").toString());
                }
                //门诊类型
                if (TextUtils.isEmpty(myLists.get(position).get("deptName").toString())) {
                    ((MyHolder) holder).tv_mz.setText("无");
                } else {
                    ((MyHolder) holder).tv_mz.setText(myLists.get(position).get("deptName").toString());
                }
                //时间
                if (myLists.get(position).get("time") == null) {
                    ((MyHolder) holder).tv_update_time.setText("刚刚");
                } else {
                    if (TextUtils.isEmpty(myLists.get(position).get("time").toString())) {
                        ((MyHolder) holder).tv_update_time.setText("无");
                    } else {
                        adaptertime = Integer.parseInt(myLists.get(position).get("time").toString());
                        if (adaptertime > 60 && adaptertime < 3600) {
                            minute = adaptertime / 60;
                            sec = adaptertime % 60;
                            ((MyHolder) holder).tv_update_time.setText(minute + "分" + sec + "秒前");
                        } else if (adaptertime > 3600) {
                            hour = adaptertime / 3600;
                            minute = (adaptertime % 3600) / 60;
                            sec = (adaptertime % 3600) % 60;
                            ((MyHolder) holder).tv_update_time.setText(hour + "小时" + minute + "分" + sec + "秒前");
                        } else {
                            ((MyHolder) holder).tv_update_time.setText("刚刚");
                        }

                    }
                }

            }
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            TextView tv_call_num, tv_mz, tv_update_time;

            public MyHolder(View itemView) {
                super(itemView);
                tv_call_num = (TextView) itemView.findViewById(R.id.tv_call_num);
                tv_mz = (TextView) itemView.findViewById(R.id.tv_mz);
                tv_update_time = (TextView) itemView.findViewById(R.id.tv_update_time);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("叫号跟踪");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
