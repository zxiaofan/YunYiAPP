package com.zxiaofan.yunyi.registered;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashSet;
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
 * Describe:     科室选择
 * User:         xiaofan
 * Date:         2016/4/5 14:40
 */
public class DepartmentRegistered extends BaseActivity {

    @Bind(R.id.department_one)
    RecyclerView departmentOne;
    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @Bind(R.id.department_two)
    RecyclerView departmentTwo;
    @Bind(R.id.ll_contain)
    LinearLayout llContain;

    private Dialog pb;

    List<Map<String, Object>> listsAll = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> listsOne = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> listsTwo = new ArrayList<Map<String, Object>>();
    Map<String, Object> object = null;

    String hosId, hosname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_registered);
        ButterKnife.bind(this);
        hosId = getIntent().getStringExtra("hosId");//医院id
        if (hosId == null || TextUtils.isEmpty(hosname)) {
            hosId = "1";
        }
        hosname = getIntent().getStringExtra("hosname");//医院名称
        if (hosname == null || TextUtils.isEmpty(hosname)) {
            hosname = "测试";
        }
        init();
    }


    private void init() {
        initTitle();
        initHospitalName();
        initData();
    }

    private void initHospitalName() {
        tvHospitalName.setText(hosname);
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

    /**
     * Describe:     获取所有科室
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void initData() {
        pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "department?hosId=" + hosId;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(DepartmentRegistered.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     格式化科室数据
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
                listsAll = JsonUtils.getListMap(object.get("departments").toString());
                if (listsAll.size() > 0) {
                    formatData();
                } else {
                    llContain.setVisibility(View.GONE);
                }

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
        for (int i = 0; i < listsAll.size(); i++) {
            if (TextUtils.isEmpty(listsAll.get(i).get("parentDeptId").toString())) {
                listsOne.add(listsAll.get(i));
            }
        }
        initWidget();
    }

    private void initWidget() {
        DepartmentOneAdapter oneAdapter = new DepartmentOneAdapter();
        departmentOne.setLayoutManager(new LinearLayoutManager(this));
        departmentOne.setAdapter(oneAdapter);
        //右侧数据匹配
        bindRecyclerviewTwoData(listsOne.get(0).get("id").toString());
    }

    /**
     * Describe:     左侧list适配器
     * User:         xiaofan
     * Date:         2016/3/24 10:42
     */
    class DepartmentOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public HashSet<MyViewHolderOne> getAllHolders() {
            return allHolders;
        }

        private HashSet<MyViewHolderOne> allHolders;

        public DepartmentOneAdapter() {
            allHolders = new HashSet<MyViewHolderOne>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(DepartmentRegistered.this).inflate(R.layout.departmnet_one_item, parent, false);
            MyViewHolderOne holder = new MyViewHolderOne(itemView);
            allHolders.add(holder);
            return holder;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolderOne) {
                ((MyViewHolderOne) holder).tv_department_name.setText(listsOne.get(position).get("deptName").toString());
                if (position == 0) {
                    ((MyViewHolderOne) holder).itemView.setBackground(getResources().getDrawable(R.drawable.department_item_one_true));
                }

            }
        }

        @Override
        public int getItemCount() {
            return listsOne.size();
        }

        class MyViewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv_department_name;
            private View itemView;

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public MyViewHolderOne(View itemView) {
                super(itemView);
                tv_department_name = (TextView) itemView.findViewById(R.id.tv_department_name);
                this.itemView = itemView;
                itemView.setOnClickListener(this);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                for (MyViewHolderOne holder : allHolders) {
                    if (holder.getPosition() == getPosition()) {
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.department_item_one_true));
                        bindRecyclerviewTwoData(listsOne.get(getPosition()).get("id").toString());
                    } else {
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.department_item_one_false));
                    }
                }
            }

            public View getItemView() {
                return itemView;
            }

            public void setItemView(View itemView) {
                this.itemView = itemView;
            }
        }
    }

    /**
     * Describe:     绑定右侧列表
     * User:         xiaofan
     * Date:         2016/4/11 15:09
     */
    private void bindRecyclerviewTwoData(String seleid) {
        listsTwo.clear();
        for (int i = 0; i < listsAll.size(); i++) {
            if (listsAll.get(i).get("parentDeptId").toString().equals(seleid)) {
                listsTwo.add(listsAll.get(i));
            }
        }
        departmentTwo.setLayoutManager(new LinearLayoutManager(this));
        departmentTwo.setAdapter(new DepartmentTwoAdapter());
    }

    class DepartmentTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(DepartmentRegistered.this).inflate(R.layout.departmnet_one_item, parent, false);
            MyViewHolderTwo holder = new MyViewHolderTwo(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolderTwo) {
                ((MyViewHolderTwo) holder).tv_department_name.setText(listsTwo.get(position).get("deptName").toString());
            }
        }

        @Override
        public int getItemCount() {
            return listsTwo.size();
        }

        class MyViewHolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv_department_name;

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public MyViewHolderTwo(View itemView) {
                super(itemView);
                tv_department_name = (TextView) itemView.findViewById(R.id.tv_department_name);
                itemView.setOnClickListener(this);
                itemView.setBackground(getResources().getDrawable(R.drawable.department_item_one_true));
            }

            @Override
            public void onClick(View v) {
                ToastUtil.ToastShow(DepartmentRegistered.this, listsTwo.get(getPosition()).get("deptName").toString(), true);
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
}
