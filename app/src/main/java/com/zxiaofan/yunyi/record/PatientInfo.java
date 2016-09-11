package com.zxiaofan.yunyi.record;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.MyUserActivity;

import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.SerializableMap;
import util.TitleBarUtils;
/**
* Describe:     就诊人信息
* User:         xiaofan
* Date:         2016/4/6 15:01
*/
public class PatientInfo extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_idcard)
    TextView tvIdcard;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    Map<String,Object> object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTitle();
        initData();
        initListener();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("就诊人信息");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        btnSubmit.setOnClickListener(this);
    }

    private void initData() {
        object= ((SerializableMap)getIntent().getSerializableExtra("patientData")).getMap();
        //名字
        if(TextUtils.isEmpty(object.get("name").toString())){
            tvName.setText("无");
        }else{
            tvName.setText(object.get("name").toString());
        }
        //性别
        if(TextUtils.isEmpty(object.get("gender").toString())){
            tvSex.setText("无");
        }else{
            tvSex.setText(object.get("gender").toString());
        }
        //身份证号
        if(TextUtils.isEmpty(object.get("idNo").toString())){
            tvIdcard.setText("无");
        }else{
            tvIdcard.setText(object.get("idNo").toString());
        }
        //手机号
        if(TextUtils.isEmpty(object.get("phone").toString())){
            tvPhone.setText("无");
        }else{
            tvPhone.setText(object.get("phone").toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Intent intent=new Intent(this, MyUserActivity.class);
                startActivity(intent);
                break;
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
