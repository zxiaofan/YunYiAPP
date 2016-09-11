package com.zxiaofan.yunyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.fragment.Frag_Home;
import com.zxiaofan.yunyi.fragment.Frag_Hospitals;
import com.zxiaofan.yunyi.fragment.Frag_News;
import com.zxiaofan.yunyi.fragment.Frag_Talk;
import com.zxiaofan.yunyi.fragment.Frag_User;

import base.BaseActivity;
import base.PushSmsService;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Fragment mHomeFragment;
    private Fragment mHospitalsFragment;
    private Fragment mNewsFragment;
    private Fragment mTalkFragment;
    private Fragment mUserFragment;
    private ImageView image1, image2, image3, image4, docterimage;
    private TextView textView1, textView2, textView3, textView4, doctertextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_main);
    }

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }

    @Override
    public void initView() {
        image1 = (ImageView) findViewById(R.id.tab_home_image);
        image2 = (ImageView) findViewById(R.id.tab_hospital_image);
        image3 = (ImageView) findViewById(R.id.tab_find_image);
        image4 = (ImageView) findViewById(R.id.tab_user_image);
        docterimage = (ImageView) findViewById(R.id.tab_docter);
        textView1 = (TextView) findViewById(R.id.tab_home_text);
        textView2 = (TextView) findViewById(R.id.tab_hospital_text);
        textView3 = (TextView) findViewById(R.id.tab_find_text);
        textView4 = (TextView) findViewById(R.id.tab_user_text);
        doctertextView = (TextView) findViewById(R.id.tab_docter_text);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.tab_home);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.tab_hospitals);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.tab_talk);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.tab_user);
        LinearLayout l5 = (LinearLayout) findViewById(R.id.tab_docters);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        select(0);
        if (MyApplication.loginFlag){
            open();
        }
        if (getIntent().getIntExtra("postion", 0) != 0) {
            select(getIntent().getIntExtra("postion", 0));
        } else if (getIntent().getIntExtra("postion", 1) == 0) {
            select(0);

        }


    }

    @Override
    public void initAction() {

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mNewsFragment != null) {
            transaction.hide(mNewsFragment);
        }
        if (mHospitalsFragment != null) {
            transaction.hide(mHospitalsFragment);
        }
        if (mTalkFragment != null) {
            transaction.hide(mTalkFragment);
        }
        if (mUserFragment != null) {
            transaction.hide(mUserFragment);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:


                select(0);

                break;
            case R.id.tab_hospitals:

                select(1);

                break;
            case R.id.tab_docters:
                select(2);


                break;
            case R.id.tab_talk:
                select(3);


                break;
            case R.id.tab_user:
                select(4);


                break;

            default:
                break;
        }
    }

    long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                // finish();
                MyApplication.getInstance().ExitApp();
            }
        }
        return true;
    }

    private void select(int position) {
        // 获取FragmentManager对象
        // FragmentManager fm=getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        // 获取事务
        FragmentTransaction transaction = fm.beginTransaction();
        // 隐藏事务
        hideFragment(transaction);
        // 根据传递过来的参数 选择显示对应的Fragment
        switch (position) {
            case 0:// HomeFragment
                // 如果此Fragment为空 就新建一个
                if (mHomeFragment == null) {
                    mHomeFragment = new Frag_Home();
                    transaction.add(R.id.cccc, mHomeFragment);
                } else {// 此Fragment已经存在 直接显示
                    transaction.show(mHomeFragment);
                }
                // 改变按下后图片和文字的状态
                changeStatus(0);
                break;
            case 1:
                if (mHospitalsFragment == null) {
                    mHospitalsFragment = new Frag_Hospitals();

                    transaction.add(R.id.cccc, mHospitalsFragment);
                } else {
                    transaction.show(mHospitalsFragment);
                }
                changeStatus(1);
                break;
            case 2:
                if (mTalkFragment == null) {
                    mTalkFragment = new Frag_Talk();
                    transaction.add(R.id.cccc, mTalkFragment);
                } else {
                    transaction.show(mTalkFragment);
                }

                changeStatus(2);


                break;
            case 3:

                if (mNewsFragment == null) {
                    mNewsFragment = new Frag_News();
                    transaction.add(R.id.cccc, mNewsFragment);
                } else {
                    transaction.show(mNewsFragment);
                }
                changeStatus(3);
                break;
            case 4:
                if (mUserFragment == null) {

                    mUserFragment = new Frag_User();

                    transaction.add(R.id.cccc, mUserFragment);
                } else {
                    transaction.show(mUserFragment);
                }
                changeStatus(4);
                break;

            default:
                break;
        }
        // 提交事务
        transaction.commit();
    }

    public void open() {

        Intent intent = new Intent(this, PushSmsService.class);

        // 启动服务

        startService(intent);

    }


    private void changeStatus(int position) {

        // 重置所有图片
        image1.setImageResource(R.mipmap.u011);
        image2.setImageResource(R.mipmap.u012);
        image3.setImageResource(R.mipmap.u014);
        image4.setImageResource(R.mipmap.u015);
        docterimage.setImageResource(R.mipmap.u013);
        // 重置所有文本的颜色
        textView1.setTextColor(Color.parseColor("#999999"));
        textView2.setTextColor(Color.parseColor("#999999"));
        textView3.setTextColor(Color.parseColor("#999999"));
        textView4.setTextColor(Color.parseColor("#999999"));
        doctertextView.setTextColor(Color.parseColor("#999999"));
        // 改变对应图片和文本颜色

        switch (position) {
            case 0:
                image1.setImageResource(R.mipmap.u001);
                textView1.setTextColor(Color.parseColor("#2fad68"));
                break;
            case 1:
                image2.setImageResource(R.mipmap.u002);
                textView2.setTextColor(Color.parseColor("#2fad68"));
                break;
            case 2:
                docterimage.setImageResource(R.mipmap.u003);
                doctertextView.setTextColor(Color.parseColor("#2fad68"));

                break;
            case 3:
                image3.setImageResource(R.mipmap.u004);
                textView3.setTextColor(Color.parseColor("#2fad68"));

                break;
            case 4:
                image4.setImageResource(R.mipmap.u005);
                textView4.setTextColor(Color.parseColor("#2fad68"));
                break;

            default:
                break;
        }
    }
}