package com.zxiaofan.yunyi.registered;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.zxiaofan.yunyi.adapter.RecyclerBaseAdapter;
import com.zxiaofan.yunyi.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.DateUtil;
import util.JsonUtils;
import util.SerializableMap;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Describe:     挂号历史
 * User:         xiaofan
 * Date:         2016/3/23 15:25
 */
public class RegisteredHistory extends BaseActivity {

    @Bind(R.id.rl_listview)
    RecyclerView rlListview;
    @Bind(R.id.sr)
    SwipeRefreshLayout sr;

    List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
    Map<String, Object> object = null;

    private Dialog pb;
    private OptsharepreInterface share;
    private SerializableMap transMap = new SerializableMap();
    private MyRegisteredHistory adapter;
    int lastVisibleItem;
    LinearLayoutManager mLayoutManager;

    private int totalCount = 0;
    private int startIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_history);
        ButterKnife.bind(this);
        share = new OptsharepreInterface(this);
        init();
    }


    private void init() {
        initTitle();
        initData();
        initWidget();
    }

    /**
     * Describe:     获取所有预约数据
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void initData() {
        startIndex = totalCount  + 1;
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "appoinment/list?token=" + share.getPres("token")+ "&limit=" + Constants.PAGE_SIZE + "&offset=" + startIndex;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(RegisteredHistory.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     格式化预约列表数据
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void loadData(String json) {
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                if (lists.size() > 0) {
                    lists.addAll(JsonUtils.getListMap(object.get("data").toString()));
                    adapter.notifyDataSetChanged();
                } else {
                    lists.addAll(JsonUtils.getListMap(object.get("data").toString()));
                    Log.e(Constants.TAG,lists.size()+"");
                    mLayoutManager=new LinearLayoutManager(this);
                    adapter=new MyRegisteredHistory();
                    rlListview.setLayoutManager(mLayoutManager);
                    rlListview.setAdapter(adapter);
                }
                totalCount = lists.size();
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

        sr.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //下拉刷新
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lists.clear();
                totalCount = 0;
                startIndex = 1;
                init();
                sr.setRefreshing(false);
            }
        });
        //上拉加载
        rlListview.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()&&totalCount != 0 && totalCount % Constants.PAGE_SIZE == 0) {
                    // 网络请求数据代码，sendRequest .....
                    initData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("我的预约");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class MyRegisteredHistory extends RecyclerBaseAdapter {

        String hosName = "", dealDate = "", appointDate = "", patientname = "", state = "";

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_ITEM){
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RegisteredHistory.this).inflate(R.layout.registered_history_list_item, parent, false));
                return holder;
            }else{//(viewType == TYPE_FOOTER)
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.listview_footer, null);
                view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT));

                return new FooterViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                //医院
                if (TextUtils.isEmpty(lists.get(position).get("hosOrgName").toString())) {
                    hosName = "无";
                } else {
                    hosName = lists.get(position).get("hosOrgName").toString();
                }
                //科室
                if (TextUtils.isEmpty(lists.get(position).get("deptName").toString())) {
                    hosName += "      无";
                } else {
                    hosName += "      " + lists.get(position).get("deptName").toString();
                }
                ((MyViewHolder) holder).tv_hospital_name.setText(hosName);

                //日期
                if (TextUtils.isEmpty(lists.get(position).get("appointDate").toString())) {
                    appointDate = "无";
                } else {
                    appointDate = DateUtil.formatedDateTime("yyyy-MM-dd", Long.parseLong(lists.get(position).get("appointDate").toString()));
                }
                //专家名
                if (TextUtils.isEmpty(lists.get(position).get("expName").toString())) {
                    appointDate += "      无";
                } else {
                    appointDate += "      " + lists.get(position).get("expName").toString();
                }
                ((MyViewHolder) holder).tv_date_and_doctor.setText(appointDate);

                //订单创建日期
                if (TextUtils.isEmpty(lists.get(position).get("createtime").toString())) {
                    dealDate = "无";
                } else {
                    dealDate = DateUtil.formatedDateTime("yyyy/MM/dd hh:mm", Long.parseLong(lists.get(position).get("createtime").toString()));
                }
                ((MyViewHolder) holder).tv_deal_date.setText(dealDate);

                //就诊人
                if (TextUtils.isEmpty(lists.get(position).get("patientname").toString())) {
                    patientname = "无";
                } else {
                    patientname = lists.get(position).get("patientname").toString();
                }
                //性别
                if (!TextUtils.isEmpty(lists.get(position).get("gender").toString())) {
                    patientname += "      " + lists.get(position).get("gender").toString();
                }
                //电话
                if (!TextUtils.isEmpty(lists.get(position).get("phone").toString())) {
                    patientname += "      " + lists.get(position).get("phone").toString();
                }
                ((MyViewHolder) holder).tv_registered_name.setText(patientname);

                //预约状态  0:已结束   1:预约成功  5:已付款   9:已退号
                if (TextUtils.isEmpty(lists.get(position).get("state").toString())) {
                    state = "无";
                } else {
                    switch (lists.get(position).get("state").toString()) {
                        case "0":
                            state = "已结束";
                            ((MyViewHolder) holder).btn_back_no.setVisibility(View.GONE);
                            break;
                        case "1":
                            state = "预约成功";
                            ((MyViewHolder) holder).btn_back_no.setVisibility(View.VISIBLE);
                            break;
                        case "5":
                            state = "已付款";
                            ((MyViewHolder) holder).btn_back_no.setVisibility(View.VISIBLE);
                            break;
                        case "9":
                            state = "已退号";
                            ((MyViewHolder) holder).btn_back_no.setVisibility(View.GONE);
                            break;
                        default:
                            state = "未知";
                            break;
                    }
                }
                ((MyViewHolder) holder).tv_deal_state.setText(state);
            }else{
                if(totalCount != 0 && totalCount % Constants.PAGE_SIZE != 0){
                    ((FooterViewHolder) holder).pb.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).tv_more.setText("没有更多");
                }else{
                    ((FooterViewHolder) holder).pb.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).tv_more.setText("加载中");
                }
            }
        }

        @Override
        public int getItemCount() {
            return lists.size()+1;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_deal_date;//交易日期
            TextView tv_hospital_name;//就诊医院
            TextView tv_date_and_doctor;//诊断医生
            TextView tv_registered_name;//就诊人
            TextView tv_deal_state;//交易状态
            Button btn_deal_detail;//交易详情
            Button btn_back_no;//退号

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_deal_date = (TextView) itemView.findViewById(R.id.tv_deal_date);
                tv_hospital_name = (TextView) itemView.findViewById(R.id.tv_hospital_name);
                tv_date_and_doctor = (TextView) itemView.findViewById(R.id.tv_date_and_doctor);
                tv_registered_name = (TextView) itemView.findViewById(R.id.tv_registered_name);
                tv_deal_state = (TextView) itemView.findViewById(R.id.tv_deal_state);
                btn_deal_detail = (Button) itemView.findViewById(R.id.btn_deal_detail);
                btn_back_no = (Button) itemView.findViewById(R.id.btn_back_no);
                btn_deal_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisteredHistory.this, DealDetail.class);
                        transMap.setMap(lists.get(getPosition()));
                        intent.putExtra("appointmodel", transMap);
                        startActivity(intent);
                    }
                });
                btn_back_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredHistory.this);
                        builder.setMessage("您确定要退号吗？ ");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                backupNum(lists.get(getPosition()).get("id").toString(),lists.get(getPosition()).get("state").toString());
                            }
                        });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }
                });
            }
        }
    }
    /**
    * Describe:     退号
    * User:         xiaofan
    * Date:         2016/4/8 15:15
    */
    private void backupNum(String id,String state){
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        }
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "appoinment/back?id=" + id+ "&state=" + state + "&token=" + share.getPres("token");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadBackupData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(RegisteredHistory.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }
    /**
    * Describe:     获取退号数据
    * User:         xiaofan
    * Date:         2016/4/8 15:34
    */
    private void loadBackupData(String json){
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                totalCount = 0;
                startIndex = 1;
                lists.clear();
                init();
                ToastUtil.ToastShow(this, "已退号", true);
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
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
