package com.zxiaofan.yunyi.hospital;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;

import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;

/**
 * Created by xiaofan on 2016/3/16.
 * 医院导航activity
 */

public class HospitalNavigationActivity extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.imageView9)
    ImageView imageView9;
    @Bind(R.id.textView38)
    TextView textView38;
    @Bind(R.id.textView39)
    TextView textView39;
    @Bind(R.id.imageView10)
    ImageView imageView10;
    @Bind(R.id.textView40)
    TextView textView40;
    @Bind(R.id.button2)
    Button button2;
    String hosId, hosname;
    @Bind(R.id.texx)
    TextView texx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_hospital_navigation);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        hosname = getIntent().getStringExtra("hosname");
        texx.setText(hosname);
        hosId = getIntent().getStringExtra("hosId");
        initTitle();
        network();
    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("医院导航");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void network() {
        RequestQueue queue = Volley.newRequestQueue(this);
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS + "hospital/" + hosId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;
                        List<Map<String, Object>> hospitalSimples = null;
                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());

                            String hosAddr = data.get("hosAddr").toString() + "test";
                            String hosTel = data.get("hosTel").toString() + "test";
                            textView39.setText("地址：" + hosAddr);
                            textView40.setText(hosTel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("aaa", error.toString());

                    }
                }
        );
        queue.add(requset);
    }
}
