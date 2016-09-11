package com.zxiaofan.yunyi.record;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.TitleBarUtils;

/**
 * Describe:     报告查询
 * User:         xiaofan
 * Date:         2016/4/1 14:35
 */
public class ReportQuery extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_jybg)
    TextView tvJybg;
    @Bind(R.id.tv_jcbg)
    TextView tvJcbg;
    @Bind(R.id.lv_jybg)
    LinearLayout lvJybg;
    @Bind(R.id.lv_jcbg)
    LinearLayout lvJcbg;

    private FragmentManager mManager;
    private Fragment mJybg;
    private Fragment mJcbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_query);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTitle();
        initWidget();
        initFragment();
    }

    private void initFragment() {
        mJybg = new ReportQueryJybg();
        mJcbg = new ReportQueryJcbg();
        mManager = getSupportFragmentManager();
        //设置检验报告被选中
        setScaleAnim(lvJybg);
        tvJybg.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        FragmentTransaction trans = mManager.beginTransaction();
        trans.add(R.id.ll_fragment, mJybg);
        trans.commit();
    }

    private void seleFragment(int id) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case 0:
                trans.replace(R.id.ll_fragment, mJybg);
                break;
            case 1:
                trans.replace(R.id.ll_fragment, mJcbg);
                break;
        }
        trans.addToBackStack(null);
        trans.commit();
    }

    private void initWidget() {
        lvJybg.setOnClickListener(this);
        lvJcbg.setOnClickListener(this);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("报告信息");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        tvJybg.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvJcbg.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvJybg.clearAnimation();
        tvJcbg.clearAnimation();
        switch (v.getId()) {
            case R.id.lv_jybg://检验报告
                seleFragment(0);
                break;
            case R.id.lv_jcbg://检查报告
                seleFragment(1);
                break;
        }
        if (v instanceof LinearLayout) {
            ((TextView)((LinearLayout) v).getChildAt(0)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            setScaleAnim(v);
        }
    }

    private void setScaleAnim(View v){
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f,
                1.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        ((LinearLayout) v).getChildAt(0).clearAnimation();
        ((LinearLayout) v).getChildAt(0).setAnimation(animation);
        animation.start();
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
