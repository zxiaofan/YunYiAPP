package com.zxiaofan.yunyi.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;


public class MyCard extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.listView3)
    ListView listView3;
    private Dialog pb;
    private Button button3;
    List<Map<String,Object>> list;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_my_card);
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
        View v = View.inflate(this, R.layout.addcard, null);
        button3= (Button) v.findViewById(R.id.button3);
        o=new OptsharepreInterface(this);
        token=o.getPres("token");
        listView3.addFooterView(v);
        mycard();
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),MyDocCard.class);
                intent.putExtra("hosOrgName",list.get(i).get("hosOrgName").toString());
                intent.putExtra("cardno",list.get(i).get("cardno").toString());

                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),AddHosCard.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initAction() {

    }
    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("我的就医卡");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void mycard(){
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
       }
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS+"opcard/my",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(),response,false);
                        parsecard(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(),"开发中，敬请期待 . . .",true);

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {


                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
               map.put("token", token);
               return map;
            }
        };



        queue.add(requset);
    }

    private void parsecard(String response) {
        Map<String,Object> object=null;
        List<Map<String,Object>> data=new ArrayList<>();
        try {
            object= JsonUtils.getMapObj(response);
            data=JsonUtils.getListMap(object.get("data").toString());
            list=data;
            CardAdapter adapter=new CardAdapter(getBaseContext(),list);
            listView3.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
