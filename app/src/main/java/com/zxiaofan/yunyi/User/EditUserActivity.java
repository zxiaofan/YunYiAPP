package com.zxiaofan.yunyi.User;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
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
import widget.ProgressDialogStyle;

public class EditUserActivity extends BaseActivity {

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
    @Bind(R.id.ed3)
    EditText ed3;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.btn_del)
    Button btnDel;
    private String name,gender,idNo,phone,id;
    private String token;
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_edit_user);
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
        o=new OptsharepreInterface(this);
        token=o.getPres("token");


     name=getIntent().getStringExtra("name");
      gender=getIntent().getStringExtra("gender");
        idNo=getIntent().getStringExtra("idNo");
        phone=getIntent().getStringExtra("phone");



         id=getIntent().getStringExtra("id");


        if (gender.equals("男")){
            spinner1.setSelection(0);
        }else
        {
            spinner1.setSelection(1);
        }
        ed1.setText(name);
        ed2.setText(idNo);
        ed3.setText(phone);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender=spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=ed1.getText().toString();
                idNo=ed2.getText().toString();
                phone=ed3.getText().toString();

                save();
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
                Constants.SERVER_ADDRESS+"patient/edit?name="+name+
                        "&gender="+gender+"&phone="+phone+"&idNo="+idNo+"&id="+id+"&token="+token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Map<String,Object> object=null;
                        Map<String,Object> data=null;

                            try {
                                object= JsonUtils.getMapObj(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String success=object.get("success").toString();

                            if (success.equals("0")){
                                String msg = object.get("msg").toString();
                                ToastUtil.ToastShow(getBaseContext(),msg, true);
                            }else if(success.equals("1")) {
                                ToastUtil.ToastShow(getBaseContext(),"保存成功", true);
                                finish();
                            }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);
                        Log.i("aaa",error.toString());

                    }
                }
        );
        queue.add(requset);
    }

    @Override
    public void initAction() {

    }
    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("编辑就诊人");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
