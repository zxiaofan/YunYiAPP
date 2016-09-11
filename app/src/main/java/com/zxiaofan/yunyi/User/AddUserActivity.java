package com.zxiaofan.yunyi.User;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;

import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;

public class AddUserActivity extends BaseActivity {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.et_1)
    EditText et1;
    @Bind(R.id.et_2)
    EditText et2;
    @Bind(R.id.et_3)
    EditText et3;
    @Bind(R.id.et_4)
    EditText et4;
    @Bind(R.id.button4)
    Button button4;
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        initTitle();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("添加就诊人");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void add(){
        String s1=et1.getText().toString().trim();
        String s2=et2.getText().toString().trim();
        String s3=et3.getText().toString().trim();
        String s4=et4.getText().toString().trim();
        OptsharepreInterface o = new OptsharepreInterface(this);
        String token = o.getPres("token");


        RequestQueue queue = Volley.newRequestQueue(this);
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS+"patient/add?gender="+s1+"&idNo="+s3+"&name="+s2+"&phone="+s4+"&token="+token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa", response);
                      pb.dismiss();
                        Map<String, Object> object = null;
                        try {
                            object = JsonUtils.getMapObj(response);
                            if("1".equals(object.get("success").toString())){
                                ToastUtil.ToastShow(getBaseContext(),"添加成功",true);
                                finish();
                            }else{
                                ToastUtil.ToastShow(getBaseContext(),"添加失败",false);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("err", error.toString());
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(),"服务器好像出了点问题",true);

                    }
                }
        );
        queue.add(requset);
    }
}
