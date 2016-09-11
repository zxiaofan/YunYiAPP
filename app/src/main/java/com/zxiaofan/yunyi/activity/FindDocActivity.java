package com.zxiaofan.yunyi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;
import com.zxiaofan.yunyi.adapter.FindDocAdapter1;
import com.zxiaofan.yunyi.findDoc.FindDocDetail;
import com.zxiaofan.yunyi.hospital.adapter.PupAdapter;

import java.util.ArrayList;
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
import view.SearchView;
import widget.ProgressDialogStyle;
import widget.RefreshLayout;

/**
 * Created by xiaofan on 2016/3/16.
 * 找医生ctivity
 */
public class FindDocActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_find_doc;
    private String str_disease;
    ServiceAPI api=new ServiceAPI();
    @Bind(R.id.finddoc)
    SearchView finddoc;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.l1)
    LinearLayout l1;
    @Bind(R.id.refresh)
    RefreshLayout refresh;
    private PopupWindow popupWindow;
    private List<Map<String, Object>> list1 = new ArrayList<>();
    private String id = "";
    private Dialog pb;
    private int totalCount=0,startIndex=1;
    private FindDocAdapter1 adapter;
    private boolean first=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_doc);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Button bt_findDoc = (Button) findViewById(R.id.bt_find_Doc);
        bt_findDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_find_doc = (EditText) findViewById(R.id.ed_find_doc);
                str_disease = ed_find_doc.getText().toString().trim();
                if ("".equals(str_disease)) {
                    ToastUtil.show(FindDocActivity.this, "您尚未输入疾病名..");
                } else {
                    api.queryDoctorsByDisease(str_disease,FindDocActivity.this);
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return 0;
    }
    private void initWidget() {

        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //下拉刷新
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list1.clear();
                totalCount = 0;
                startIndex = 1;
                hoslist();
                refresh.setRefreshing(false);
            }
        });
        refresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (totalCount > 0 && totalCount % Constants.PAGE_SIZE == 0) {
                    hoslist();
                    refresh.setLoading(false);

                } else {


                    refresh.setLoading(false);
                    ToastUtil.ToastShow(getBaseContext(), "没有更多", false);
                }
            }
        });

    }

    @Override
    public void initView() {
        initTitle();
        finddoc.setHint(this, "医生");
        hoslist();
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getBaseContext(), FindDocDetail.class);
                id = list1.get(i).get("id").toString();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        initWidget();

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("找医生");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initAction() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public void hoslist() {
        if (pb == null) {
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "doctor",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        parsedoc(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("keyWord", "");
                map.put("limit",Constants.PAGE_SIZE+"");
              //  map.put("limit",5+"");
                map.put("offset",totalCount+"");

                return map;
            }
        };


        queue.add(requset);

    }

    private void parsedoc(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        List<Map<String, Object>> doctors = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            doctors = JsonUtils.getListMap(data.get("doctors").toString());
            if (first){
                list1 = doctors;
                first=false;
                adapter=new FindDocAdapter1(list1, getBaseContext());
                listView.setAdapter(adapter);
            }else {

                list1.addAll(doctors);
                adapter.notifyDataSetChanged();
            }
            totalCount = list1.size();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPopWindow(int x) {
        View popView = View.inflate(this, R.layout.pup_listview, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        // popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
        ListView listview = (ListView) popView.findViewById(R.id.listView8);
        List<String> l1 = new ArrayList<>();
        final List<String> l2 = new ArrayList<>();
        final List<String> l3 = new ArrayList<>();
        l2.add("全部科室");
        l2.add("消化内科");
        l2.add("心血管科");
        l2.add("老年病科");
        l2.add("普通外科");

        l2.add("职业病科");
        l2.add("肿瘤放疗科");
        l2.add("神经内科");
        l2.add("骨科门诊");
        l2.add("中医科");


        l3.add("全部");
        l3.add("普通医师");
        l3.add("副主任医师");
        l3.add("主任医师");
        l3.add("特约专家");
        l3.add("其他");



        if (x == 1) {
            listview.setAdapter(new PupAdapter(this, l1));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //tv2.setText(l2.get(i).toString());
                    popupWindow.dismiss();
                }
            });
        } else if (x == 2) {
            listview.setAdapter(new PupAdapter(this, l2));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tv2.setText(l2.get(i).toString() + "▼");


                    popupWindow.dismiss();
                    // hoslist();

                }
            });
        } else {
            listview.setAdapter(new PupAdapter(this, l3));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tv3.setText(l3.get(i).toString() + "▼");
//                    switch (l3.get(i).toString()) {
//                        case "全部":
//                            hosType = "";
//                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
//                            dengji = true;
//                            totalCount = 0;
//                            hoslist();
//                            break;
//                        case "医院":
//                            hosType = "A";
//                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
//                            dengji = true;
//                            totalCount = 0;
//                            hoslist();
//                            break;
//                        case "社区卫生服务中心（站）":
//                            hosType = "B";
//                            dengji = true;
//                            totalCount = 0;
//                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
//                            hoslist();
//                            break;
//                        case "":
//                            hosType = "";
//                            totalCount = 0;
//                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
//                            dengji = true;
//                            hoslist();
//                            break;
//                        default:
//                            hosType = "B";
//                            dengji = true;
//                            totalCount = 0;
//                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
//                            hoslist();
//                            break;
//                    }
                    popupWindow.dismiss();
                }
            });
        }


    }

    public void showPop(View parent, int x, int y, int postion) {
        //设置popwindow显示位置
        // popupWindow.showAtLocation(parent, 0, x, y);
        // popupWindow.showAtLocation(view,5,0,10);
        popupWindow.showAsDropDown(l1);
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        if (popupWindow.isShowing()) {

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                initPopWindow(1);
                showPop(view, 0, 0, 0);
                break;
            case R.id.tv2:
                ToastUtil.ToastShow(getBaseContext(), "科室", true);
                initPopWindow(2);

                showPop(view, 0, 0, 0);
                break;
            case R.id.tv3:
                initPopWindow(3);
                showPop(view, 0, 0, 0);
                break;
        }
    }
}
