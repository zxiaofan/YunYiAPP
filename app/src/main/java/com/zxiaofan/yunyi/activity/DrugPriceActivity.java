package com.zxiaofan.yunyi.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.adapter.DrugPriceAdapter;

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
import widget.RefreshLayout;

/**
 * Created by xiaofan on 2016/3/16.
 * 药品价格activity
 */
public class DrugPriceActivity extends BaseActivity {


    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.textView36)
    TextView textView36;
    @Bind(R.id.textView37)
    TextView textView37;
    @Bind(R.id.listView2)
    ListView listView2;

    List<Map<String, Object>> list = new ArrayList<>();
    @Bind(R.id.search_et_input)
    EditText searchEtInput;
    @Bind(R.id.search_in)
    ImageView searchIn;
    @Bind(R.id.refresh)
    RefreshLayout refresh;
    private String cont = "";//搜索内容
    private RequestQueue queue;
    private int totalCount = 0;
    private int startIndex = 1;
    private boolean first = true;
    private boolean sousuo = false;
    private DrugPriceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_drug_price);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
                initData();
                refresh.setRefreshing(false);
            }
        });
        refresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (totalCount > 0 && totalCount % Constants.PAGE_SIZE == 0) {
                    initData();
                    refresh.setLoading(false);

                } else {

                    ToastUtil.ToastShow(getBaseContext(), "没有更多", false);
                    refresh.setLoading(false);
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

        queue = Volley.newRequestQueue(getBaseContext());
        initData();
        searchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = searchEtInput.getText().toString();
                sousuo=true;
                totalCount=0;
                initData();
            }
        });
        searchEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchEtInput.getText().length() == 0) {
                    cont = "";
                    totalCount=0;
                    sousuo=true;
                    initData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initWidget();

    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("价格查询");

        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData() {

        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "hospital/medicinePrice",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("yaopin", response);
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;
                        List<Map<String,Object>> dugPriceList=null;
                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());
                            dugPriceList = JsonUtils.getListMap(data.get("dugPriceList").toString());

                            if (first||sousuo){
                                list = dugPriceList;
                                first=false;
                                sousuo=false;
                                adapter =new DrugPriceAdapter(list, getBaseContext());
                                listView2.setAdapter(adapter);
                            }else {

                                list.addAll(dugPriceList);
                                adapter.notifyDataSetChanged();
                            }
                            totalCount = list.size();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                map.put("hosId", "1");
                if (cont != null) {
                    map.put("medName", cont);
                }
                map.put("limit",Constants.PAGE_SIZE+"");
                //map.put("limit",1+"");
                map.put("offset",totalCount+"");

                return map;
            }
        };
        queue.add(requset);
    }


}
