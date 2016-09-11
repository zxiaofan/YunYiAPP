package com.zxiaofan.yunyi.User;
/*
*        user: xiaofan
*        个人信息Activity
*
* */
import android.os.Bundle;
import android.view.View;

import com.zxiaofan.yunyi.R;

import base.BaseActivity;
import util.TitleBarUtils;

public class PersonalInformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
    initTitle();
    }

    @Override
    public void initAction() {

    }
    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("个人信息");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
