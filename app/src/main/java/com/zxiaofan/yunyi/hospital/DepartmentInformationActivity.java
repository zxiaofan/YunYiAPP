package com.zxiaofan.yunyi.hospital;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.adapter.FindDocAdapter;
import com.zxiaofan.yunyi.findDoc.FindDocDetail;

import java.util.ArrayList;
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
import widget.RefreshLayout;

public class DepartmentInformationActivity extends BaseActivity {
    @Bind(R.id.refresh)
    RefreshLayout refresh;
    private List<Map<String, Object>> list = new ArrayList<>();
    List<String> l = new ArrayList<>();
    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;

    TextView hosname;

    TextView keshi;

    TextView keshixiangxi;


    @Bind(R.id.listView9)
    ListView listView9;
    private String hosname1 = "", hosId = "", deptCode = "";
    private int totalCount,startIndex;
    private RequestQueue queue;
    private String hosOrgCode="";
    private String id;
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_department_information);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        queue = Volley.newRequestQueue(this);
        hosname1 = getIntent().getStringExtra("hosname");
        String keshi1 = getIntent().getStringExtra("keshi");
        hosId = getIntent().getStringExtra("hosId");
        deptCode = getIntent().getStringExtra("deptCode");
        hosOrgCode=getIntent().getStringExtra("hosOrgCode");
        initTitle();
        View v = LayoutInflater.from(this).inflate(R.layout.it_keshixinxi, null);
        hosname = (TextView) v.findViewById(R.id.hosname);
        keshi = (TextView) v.findViewById(R.id.keshi);
        keshixiangxi = (TextView) v.findViewById(R.id.keshixiangxi);
        hosname.setText(hosname1);
        keshi.setText(keshi1);

        listView9.addHeaderView(v);
        listView9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Intent intent = new Intent(getBaseContext(), FindDocDetail.class);
                    id = list.get(i - 1).get("id").toString();
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

         //listView9.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, l));
       network();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                totalCount = 0;
                startIndex = 1;
                // lists.clear();
                // initData();
                refresh.setRefreshing(false);
            }
        });
        refresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {

                if (totalCount != 0 && totalCount % Constants.PAGE_SIZE != 0) {
                    refresh.setLoading(false);
                    ToastUtil.ToastShow(getBaseContext(), "没有更多", false);
                } else {
                     //initData();
                    refresh.setLoading(false);
                }
            }
        });


    }
    private void initData(){


    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("科室信息");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void network() {
        if (pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "正在登录...");
            pb.show();
        }
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS + "doctor/recommend?hosOrgCode="+hosOrgCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Log.i("KESHIXINXI",response);
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;

                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());
                            list = JsonUtils.getListMap(data.get("doctors").toString());
                            listView9.setAdapter(new FindDocAdapter(list,getBaseContext()));
                            Log.i("tese", list.size() + "");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Log.i("aaa", error.toString());

                    }
                }
        );
        queue.add(requset);
    }
}
