package com.zxiaofan.yunyi.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.activity.MainActivity;

import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.SharedPreferenceUtil;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

public class PersonalDetail extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.ed1)
    EditText ed1;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.spinner1)
    AppCompatSpinner spinner1;
    @Bind(R.id.ed2)
    EditText ed2;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private String s1,s2,s3,s4,token;
    private Dialog pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_personal_detail);

        ButterKnife.bind(this);
        init();
        super.onCreate(savedInstanceState);

    }

    private void init() {
        initTitle();
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


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        o=new OptsharepreInterface(this);
        s4=o.getPres("phonenumber");


        token=o.getPres("token");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))){
            ed1.setText( getIntent().getStringExtra("name"));
            ed2.setText( getIntent().getStringExtra("idNo"));
            if (getIntent().getStringExtra("gender").equals("男")){
                spinner1.setSelection(0);
            }else
            {
                spinner1.setSelection(1);
            }
        }


        if (!TextUtils.isEmpty(SharedPreferenceUtil.readString(this,s4+"name",""))){




        }




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=ed1.getText().toString().trim();

                s3=ed2.getText().toString().trim();
                save();
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2=spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void save() {
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS+"userinfo/edit?gender="+s2+"&idNo="+s3+"&name="+s1+"&token="+token,
        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Log.i("aaa",response);
                        Map<String, Object> object = null;
                        try {
                            object = JsonUtils.getMapObj(response);
                            if("1".equals(object.get("success").toString())){
                                ToastUtil.ToastShow(getBaseContext(),"保存成功",true);
                                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                                intent.putExtra("postion",4);


                                startActivity(intent);

                            }else{
                                ToastUtil.ToastShow(getBaseContext(),"保存失败",false);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(),"开发中，敬请期待 . . .",true);

                        Log.i("aaa",error.toString());

                    }
                }
        );
        queue.add(requset);
    }

    @Override
    public void initAction() {

    }
}
