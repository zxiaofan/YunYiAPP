package com.zxiaofan.yunyi.hospital;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.TitleBarUtils;
import view.SearchView;

/**
 * Created by xiaofan on 2016/3/16.
 * 价格查询activity
 */
public class PriceSearchActivity extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.textView36)
    TextView textView36;
    @Bind(R.id.textView37)
    TextView textView37;
    @Bind(R.id.search)
    SearchView search;
    @Bind(R.id.search1)
    SearchView search1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_price_search);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
       search1.setHint(this,"医疗服务项目");
        search.setHint(this,"药品");
        initTitle();
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
}
