package com.zxiaofan.yunyi.User;

import android.os.Bundle;
import android.view.View;

import com.zxiaofan.yunyi.R;

import base.BaseActivity;
import util.TitleBarUtils;

/**
* Describe:     关于我们
* User:         xiaofan
* Date:         2016/4/12 11:38
*/
public class AboutUs extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initTitle();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("关于我们");
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
}
