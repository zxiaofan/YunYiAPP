package com.zxiaofan.yunyi.hospital;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import util.SerializableMap;
import util.SpaceItemDecoration;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
* Describe:     就医导航列表
* User:         xiaofan
* Date:         2016/4/18 10:38
*/
public class MedicalNavigationActivity extends BaseActivity {


    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private Dialog pb;

    List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
    Map<String, Object> object = null;
    SerializableMap map = null;
    String hosId,hosname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_navigation);
        ButterKnife.bind(this);
        hosId=getIntent().getStringExtra("hosId");//医院id
        if(hosId==null){
            hosId="1";
        }
        hosname=getIntent().getStringExtra("hosname");//医院名称
        if(hosname==null||TextUtils.isEmpty(hosname)){
            hosname="测试";
        }
        init();
    }

    private void init() {
        initTitle();
        initData();
        initHospitalName();
    }

    private void initHospitalName() {
        tvHospitalName.setText(hosname);
    }

    private void initTitle() {
        titleBar = (TitleBarUtils) this.findViewById(R.id.titleBar);
        Log.e(Constants.TAG, titleBar.toString());
        titleBar.setTitle("就医导航");
        titleBar.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Describe:     获取所有导航布局列表
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void initData() {
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        }
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        //此处未分页，直接取30条数据
        String url = Constants.SERVER_ADDRESS + "hospital/innerNav?hosId="+hosId+"&limit=30";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(MedicalNavigationActivity.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     格式化数据
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void loadData(String json) {
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                object = JsonUtils.getMapObj(object.get("data").toString());
                lists = JsonUtils.getListMap(object.get("tHosNavs").toString());
                initWidget();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
        }
    }

    private void initWidget() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.addItemDecoration(new SpaceItemDecoration(this,1));
        recyclerview.setAdapter(new MyRecyclerview());
    }

    class MyRecyclerview extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyviewHolder holder = new MyviewHolder(LayoutInflater.from(MedicalNavigationActivity.this).inflate(R.layout.medical_navi_list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyviewHolder) {
                if (TextUtils.isEmpty(lists.get(position).get("navtitle").toString())) {
                    ((MyviewHolder) holder).tv_name.setText("无");
                } else {
                    ((MyviewHolder) holder).tv_name.setText(lists.get(position).get("navtitle").toString());
                }
            }
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        class MyviewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;

            public MyviewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        map = new SerializableMap();
                        Intent intent = new Intent(MedicalNavigationActivity.this, MedicalNaviDetail.class);
                        map.setMap(lists.get(getPosition()));
                        intent.putExtra("model", map);
                        intent.putExtra("hosId",hosId);
                        intent.putExtra("hosname",hosname);
                        startActivity(intent);
                    }
                });
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
}
