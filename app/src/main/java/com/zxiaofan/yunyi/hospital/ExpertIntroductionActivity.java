package com.zxiaofan.yunyi.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.zxiaofan.yunyi.adapter.FindDocAdapter1;
import com.zxiaofan.yunyi.findDoc.FindDocDetail;

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

/*
专家介绍Activity
cause by  xiaofan

 */
public class ExpertIntroductionActivity extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.listView4)
    ListView listView4;
    @Bind(R.id.hosname)
    TextView hosname;
    String hosId="";
    String hosCode="";
    String hosname1="";
    private List<Map<String,Object>> list1=new ArrayList<>();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_expert_introduction);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
      initTitle();
        hosname.setText(getIntent().getStringExtra("hosname"));
        hosCode=getIntent().getStringExtra("hosOrgCode");
        hosId=getIntent().getStringExtra("hosId");
        hosname1=getIntent().getStringExtra("hosname");

        ToastUtil.ToastShow(this,hosCode,true);
        initData();

        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(), FindDocDetail.class);
                id=list1.get(i).get("id").toString();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
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

    private void initData(){
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS+"doctor/spec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("zhuanjia",response);
                        parsedoc(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ToastUtil.ToastShow(getBaseContext(),"开发中，敬请期待 . . .",true);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                if (hosCode!=null){
                 map.put("hosCode",hosCode);
                }
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
            list1 = doctors;
            listView4.setAdapter(new FindDocAdapter1(list1, getBaseContext()
            ));



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
