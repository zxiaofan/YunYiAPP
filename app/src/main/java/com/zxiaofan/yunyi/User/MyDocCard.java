package com.zxiaofan.yunyi.User;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.adapter.DocCardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import bean.MyRecord;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Describe:     我的就医卡
 * User:         xiaofan
 * Date:         2016/3/25 14:04
 */
public class MyDocCard extends BaseActivity {


    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.rl_lists)
    ListView rlLists;
    private List<MyRecord> lists = new ArrayList<MyRecord>();
    private String hosOrgName = "";
    private String cardno = "";
    private String token = "";
    private Dialog pb;
    private List<Map<String, Object>> list;
    private TextView tvHospitalName;
    private TextView tvCardid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doc_card);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTitle();
        initWidget();
    }

    private void initWidget() {
        View v=View.inflate(this,R.layout.card1,null);
        tvHospitalName= (TextView) v.findViewById(R.id.tv_hospital_name);
        tvCardid= (TextView) v.findViewById(R.id.tv_cardid);
        hosOrgName = getIntent().getStringExtra("hosOrgName");
        cardno = getIntent().getStringExtra("cardno");
        o = new OptsharepreInterface(this);
        token = o.getPres("token");

        tvHospitalName.setText(hosOrgName);
        tvCardid.setText("就医卡号： " + cardno);
        rlLists.addHeaderView(v);

        mycard();


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


    private void mycard() {
        if (pb == null) {
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "opcard/" + cardno + "/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("card", response);
                        pb.dismiss();

                        parsereg(response);


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
                map.put("token", token);
                return map;
            }
        };


        queue.add(requset);
    }

    private void parsereg(String response) {
        Map<String, Object> object = null;
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getListMap(object.get("data").toString());
            list = data;
            Log.i("card---listsize", list.size() + "");
            rlLists.setAdapter(new DocCardAdapter(list, getBaseContext()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
