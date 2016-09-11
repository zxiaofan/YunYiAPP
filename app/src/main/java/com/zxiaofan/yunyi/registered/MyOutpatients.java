package com.zxiaofan.yunyi.registered;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import bean.MyRecord;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.TitleBarUtils;

/**
* Describe:     我的就诊人
* User:         xiaofan
* Date:         2016/3/23 10:13
*/
public class MyOutpatients extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_listview)
    RecyclerView rlListview;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private RecyclerView rl_listview;
    private List<MyRecord> lists=new ArrayList<MyRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_outpatients);
        init();
    }



    private void init() {
        ButterKnife.bind(this);
        initTitle();
        initListener();
        initData();
        initWidget();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("我的就诊人");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWidget() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rl_listview= (RecyclerView) findViewById(R.id.rl_listview);
        rl_listview.setLayoutManager(layoutManager);
        rl_listview.setAdapter(new OutpatientsListsAdapter());
        rl_listview.setFocusable(false);
    }

    private void initData() {
        MyRecord record;
        for(int i=0;i<3;i++){
            record=new MyRecord();
            record.setTitle("王明明");
            record.setContent("17603867706");
            lists.add(record);
        }
    }

    private void initListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                break;
        }
    }
    /**
    * Describe:     list adapter
    * User:         xiaofan
    * Date:         2016/3/23 9:29
    */
    class OutpatientsListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder=new MyHolder(LayoutInflater.from(MyOutpatients.this).inflate(R.layout.myoutpatients_list_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof MyHolder){
                ((MyHolder) holder).tv_name.setText("王明明");
                ((MyHolder) holder).tv_sex_and_age.setText("女  28岁");
                ((MyHolder) holder).tv_phone.setText(lists.get(position).getContent());
                ((MyHolder) holder).tv_idcard.setText("411215484154256325");
            }
        }

        @Override
        public int getItemCount() {
            Log.e(Constants.TAG,lists.size()+"");
            return lists.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView tv_name,tv_sex_and_age,tv_phone,tv_idcard;
            public MyHolder(View itemView) {
                super(itemView);
                tv_name= (TextView) itemView.findViewById(R.id.tv_name);
                tv_sex_and_age= (TextView) itemView.findViewById(R.id.tv_sex_and_age);
                tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
                tv_idcard= (TextView) itemView.findViewById(R.id.tv_idcard);
            }
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
