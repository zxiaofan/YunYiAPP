package com.zxiaofan.yunyi.fragment;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.UnreadMessagesActivity;
import com.zxiaofan.yunyi.activity.DrugPriceActivity;
import com.zxiaofan.yunyi.activity.FindDocActivity;
import com.zxiaofan.yunyi.adapter.HomeNewsAdapter;
import com.zxiaofan.yunyi.login.LoginActivity;
import com.zxiaofan.yunyi.activity.FindHospitalActivity;
import com.zxiaofan.yunyi.activity.JBZZActivity;
import com.zxiaofan.yunyi.registered.RegisteredMain;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import base.OptsharepreInterface;
import bean.DataAddDataBase;

import butterknife.ButterKnife;

import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Created by lenovo on 2016/3/16.
 */
public class Frag_Home extends BaseFragment implements View.OnClickListener {

    LinearLayout finddoc;
    LinearLayout findhos1;

    LinearLayout tabHos;
    LinearLayout tabDoc;
    LinearLayout jbzz;
    TextView text1;

    private ListView health_news_lv;
    private List<Map<String,Object>> mLists=new ArrayList<Map<String,Object>>();
    
    private OptsharepreInterface share;
    private Dialog pb;
    private MyReceiver dataReceiver;
    private TitleBarUtils titleBarUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onStart();
        view = inflater.inflate(R.layout.frag_home, null);
        share=new OptsharepreInterface(getActivity());
        init();
        return view;
    }

    private void init(){
        initWidget();
        initView();
        initTitle();
        initNewsData();
        initReceiver();
    }

    private void initNewsData() {
        health_news_lv.setAdapter(new HomeNewsAdapter(getActivity(), mLists));
    }

    private void initReceiver() {
        dataReceiver = new MyReceiver();//动态注册广播
        IntentFilter filter = new IntentFilter();// 创建IntentFilter对象
        filter.addAction("android.intent.action.test");
        getActivity().registerReceiver(dataReceiver, filter);// 注册Broadcast Receiver
    }


    private void initWidget() {
        text1= (TextView) view.findViewById(R.id.msg);
        TextPaint tp = text1.getPaint();
        tp.setFakeBoldText(true);
        tabHos= (LinearLayout) view.findViewById(R.id.tab_hos);
        tabDoc= (LinearLayout) view.findViewById(R.id.ll_yygh);
        finddoc=(LinearLayout) view.findViewById(R.id.finddoc);
        findhos1=(LinearLayout) view.findViewById(R.id.findhos1);
        jbzz=(LinearLayout) view.findViewById(R.id.jbzz);
        health_news_lv= (ListView) view.findViewById(R.id.health_news_lv);
    }

    private void initTitle() {
        titleBarUtils = (TitleBarUtils) view.findViewById(R.id.titleBar);
        titleBarUtils.setTitle("医疗助手");
        titleBarUtils.setLeftImage(R.mipmap.menu);
        titleBarUtils.setRightImageOne(R.mipmap.email);

        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });
        titleBarUtils.setRightButtonOneClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleBarUtils.setRightImageOne(R.mipmap.email);
                text1.setText("    等待新消息");
                Intent intent=new Intent(getActivity(), UnreadMessagesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        finddoc.setOnClickListener(this);
        findhos1.setOnClickListener(this);
        tabHos.setOnClickListener(this);
        tabDoc.setOnClickListener(this);
        jbzz.setOnClickListener(this);


    }

    @Override
    protected void initAction() {

    }
    @Override
    public int getViewId() {
        return 0;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.finddoc:
                intent = new Intent(getActivity(), FindDocActivity.class);
                startActivity(intent);
                break;
            case R.id.findhos1:
                Intent intent1 =new Intent(getActivity(),FindHospitalActivity.class);
                startActivity(intent1);
                break;
            case R.id.tab_hos:
                Intent intent2 =new Intent(getActivity(),DrugPriceActivity.class);
                startActivity(intent2);
                break;
            case R.id.jbzz:
                Intent intent3 =new Intent(getActivity(),JBZZActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_yygh:
                Intent intent4 = new Intent(getActivity(), RegisteredMain.class);
                startActivity(intent4);
                break;

        }
    }

    class MyTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb = ProgressDialogStyle.createLoadingDialog(getActivity(), "首次启动，初始化中...");
            pb.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try{
                DataAddDataBase.setProvinceData(getActivity());
                return 1;
            }catch (Exception e){
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid==0){
                pb.dismiss();
                ToastUtil.ToastShow(getActivity(),"数据加载失败",true);
            }else{
                pb.dismiss();
                share.putPres("isFirstSavaXzqh","1");
                initWidget();
                initView();
                initTitle();
            }
        }
    }



    public class MyReceiver extends BroadcastReceiver {

        //自定义一个广播接收器

        @Override

        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub

            System.out.println("OnReceiver");

            Bundle bundle=intent.getExtras();

            int a=bundle.getInt("i");
            String mag=bundle.getString("msg");
            text1.setText("    "+mag);
            titleBarUtils.setRightImageOne(R.mipmap.message2);

            o=new OptsharepreInterface(getActivity());
            o.putPres("unmsg",a+"");
         

            //处理接收到的内容



        }

        public MyReceiver(){

            System.out.println("MyReceiver");

            //构造函数，做一些初始化工作，本例中无任何作用

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(dataReceiver);
    }
}
