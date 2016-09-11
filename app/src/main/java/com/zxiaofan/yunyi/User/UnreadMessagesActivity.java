package com.zxiaofan.yunyi.User;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import bean.MessageBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import db.DataBaseManager;
import util.TitleBarUtils;

public class UnreadMessagesActivity extends BaseActivity {


    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.listView6)
    ListView listView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_unread_messages);
        ButterKnife.bind(this);
        initTitle();
        o=new OptsharepreInterface(this);
        o.putPres("unmsg",0+"");

        super.onCreate(savedInstanceState);
    }



    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

        DataBaseManager db=DataBaseManager.getInstance(this);
        List<Map<String,Object>> list1=new ArrayList<>();
        try {
            list1=db.queryyy("select * from "+"m"+MyApplication.phone+";",null,new MessageBean());

        } catch (Exception e) {
            e.printStackTrace();
        }




        MessageAdapter adapter=new MessageAdapter(list1,this);
       //ArrayAdapter adapter=new ArrayAdapter(this,R.layout.item_message,list);
      listView6.setAdapter(adapter);
    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("消息提醒");


        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

}
