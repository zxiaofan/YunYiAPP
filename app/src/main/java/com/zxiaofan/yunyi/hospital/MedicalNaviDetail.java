package com.zxiaofan.yunyi.hospital;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SerializableMap;
import util.TitleBarUtils;

/**
* Describe:     就医导航详情
* User:         xiaofan
* Date:         2016/4/18 10:37
*/
public class MedicalNaviDetail extends BaseActivity {


    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @Bind(R.id.tv_navi_title)
    TextView tvNaviTitle;
    @Bind(R.id.wv)

    WebView wv;
    Map<String, Object> object = null;
    SerializableMap map = null;
    String hosId,hosname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_navi_detail);
        ButterKnife.bind(this);
        map = (SerializableMap) getIntent().getSerializableExtra("model");
        object = map.getMap();
        hosId=getIntent().getStringExtra("hosId");//医院id
        hosname=getIntent().getStringExtra("hosname");//医院名称
        init();
    }

    private void init() {
        initTitle();
        initWebview();
        initData();
        initHospitalName();
    }

    private void initHospitalName() {
        tvHospitalName.setText(hosname);
    }


    private void initData() {
        if(TextUtils.isEmpty(object.get("navtitle").toString())){
            tvNaviTitle.setText("无");
        }else{
            tvNaviTitle.setText(object.get("navtitle").toString());
        }

        String htmlString = "<p>"+object.get("nav").toString()+"</p>";
        // 载入htmlString
        wv.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        wv.loadData(htmlString, "text/html; charset=UTF-8", null);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("就医导航");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initWebview() {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
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
