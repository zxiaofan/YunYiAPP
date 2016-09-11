package com.zxiaofan.yunyi.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.zxiaofan.yunyi.adapter.FindHosAdapter;
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
 * 找医院activity
 */
public class FindHospitalActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_find_hos;
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
    private String hosid;
    private String hosLevel = "", hosType = "";
    List<Map<String, Object>> list = new ArrayList<>();
    private String hosname = "";
    LinearLayoutManager mLayoutManager;
    private int totalCount = 0;
    private int startIndex = 1;

    private FindHosAdapter adapter;
    private Dialog pb;
    private RequestQueue queue;
    private boolean first = true;
    private boolean dengji = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_find_hospital);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Button bt_findHos = (Button) findViewById(R.id.bt_find_Hos);
        bt_findHos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_find_hos = (EditText) findViewById(R.id.ed_find_hos);
                str_disease = ed_find_hos.getText().toString().trim();
                if ("".equals(str_disease)) {
                    ToastUtil.show(FindHospitalActivity.this, "您尚未输入疾病名..");
                } else {
                    api.queryHospitalsByDisease(str_disease,FindHospitalActivity.this);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

        initTitle();
        finddoc.setHint(this, "医院");
        if (getIntent().getStringExtra("hosname") != null) {
            hosname = getIntent().getStringExtra("hosname");
        }
        queue = Volley.newRequestQueue(getBaseContext());
        hoslist();

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("postion", 1);
                intent.putExtra("hosLevel", list.get(i).get("hosLevel").toString());
                intent.putExtra("hosType", list.get(i).get("hosType").toString());
                // intent.putExtra("hosAddr", list.get(i).get("hosAddr").toString());
                intent.putExtra("hosOrgName", list.get(i).get("hosOrgName").toString());
                intent.putExtra("hosOrgCode", list.get(i).get("hosOrgCode").toString());
                intent.putExtra("hosId", list.get(i).get("hosId").toString());
                startActivity(intent);
            }
        });
        initWidget();


    }

    private void initWidget() {

        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //下拉刷新
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
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

                    ToastUtil.ToastShow(getBaseContext(), "没有更多", false);
                    refresh.setLoading(false);
                }
            }
        });

    }

    private void initTitle() {

        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("找医院");
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

        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "hospital?limit=" + Constants.PAGE_SIZE + "&offset=" + totalCount,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsehos(response);
                        pb.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
//                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("hosName", hosname + "");
                map.put("divisioncode", "");
                map.put("hosLevel", hosLevel);
                map.put("hosType", hosType);
                //  map.put("divisioncode", adv.toString());
                //   map.put("token", token);
                return map;
            }
        };


        queue.add(requset);

    }

    private void parsehos(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        List<Map<String, Object>> hospitalSimples = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            hospitalSimples = JsonUtils.getListMap(data.get("hospitalSimples").toString());
            if (first || dengji) {
                list = hospitalSimples;
                first = false;
                dengji = false;
                adapter = new FindHosAdapter(list, getBaseContext());
                listView.setAdapter(adapter);
            } else {

                list.addAll(hospitalSimples);
                adapter.notifyDataSetChanged();
            }
            totalCount = list.size();


        } catch (Exception e) {
            e.printStackTrace();
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
                initPopWindow(2);
                showPop(view, 0, 0, 0);
                break;
            case R.id.tv3:
                initPopWindow(3);
                showPop(view, 0, 0, 0);
                break;
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
        l2.add("全部");
        l2.add("三级");
        l2.add("二级");
        l2.add("一级");
        l2.add("其它");


        l3.add("全部");

        l3.add("医院");
        l3.add("社区卫生服务中心（站）");

        l3.add("卫生院");
        l3.add("门诊部、诊所、医务室、村卫生室");
        l3.add("急救中心（站）");
        l3.add("采供血机构");
        l3.add("妇幼保健院(所、站)");
        l3.add("专科疾病防治院(所、站)");
        l3.add("疾病预防控制中心(防疫站)");
        l3.add("卫生监督检验(监测、检测)所(站)");
        l3.add("医学科学研究机构");
        l3.add("医学教育机构");
        l3.add("健康教育所(站、中心)");
        l3.add("其他卫生机构");
        l3.add("卫生社会团体");

        l3.add("其它");

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
                    switch (l2.get(i).toString()) {
                        case "三级":
                            hosLevel = 3 + "";
                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
                            dengji = true;
                            totalCount = 0;
                            hoslist();
                            break;
                        case "二级":
                            hosLevel = 2 + "";
                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
                            dengji = true;
                            totalCount = 0;
                            hoslist();
                            break;
                        case "一级":
                            hosLevel = 1 + "";
                            dengji = true;
                            totalCount = 0;
                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
                            hoslist();
                            break;
                        case "全部":
                            hosLevel = "";
                            totalCount = 0;
                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
                            dengji = true;
                            hoslist();
                            break;
                    }
                    popupWindow.dismiss();


                }
            });
        } else {
            listview.setAdapter(new PupAdapter(this, l3));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tv3.setText(l3.get(i).toString() + "▼");
                    switch (l3.get(i).toString()) {
                        case "全部":
                            hosType = "";
                            ToastUtil.ToastShow(getBaseContext(), hosLevel.toString(), true);
                            dengji = true;
                            totalCount = 0;
                            hoslist();
                            break;
                        case "医院":
                            hosType = "A";
                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
                            dengji = true;
                            totalCount = 0;
                            hoslist();
                            break;
                        case "社区卫生服务中心（站）":
                            hosType = "B";
                            dengji = true;
                            totalCount = 0;
                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
                            hoslist();
                            break;
                        case "":
                            hosType = "";
                            totalCount = 0;
                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
                            dengji = true;
                            hoslist();
                            break;
                        default:
                            hosType = "B";
                            dengji = true;
                            totalCount = 0;
                            ToastUtil.ToastShow(getBaseContext(), hosType.toString(), true);
                            hoslist();
                            break;
                    }
                    popupWindow.dismiss();


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
}
