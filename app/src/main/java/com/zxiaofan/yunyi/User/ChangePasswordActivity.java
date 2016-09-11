package com.zxiaofan.yunyi.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;

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

public class ChangePasswordActivity extends BaseActivity {
      @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_pwd1)
    EditText etPwd1;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private String s1,s2,s3;
    /**
     * Describe:     修改密码页
     * User:         苗坤
     * Date:         2016/3/25 11:20
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_change_password);
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
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!MyApplication.loginFlag){
                ToastUtil.ToastShow(getBaseContext(),"您需要先登录",false);
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                }
                s1=etAccount.getText().toString();
               s2=etPwd.getText().toString();
                 s3=etPwd1.getText().toString();
                if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)||TextUtils.isEmpty(s3)){
                    ToastUtil.ToastShow(getBaseContext(),"输入内容不能为空",true);

                }else if (s2.length()<6||s2.length()>18){
                    ToastUtil.ToastShow(getBaseContext(),"请输入正确的密码位数",true);
                }

                else if (!s2.equals(s3)){
                    ToastUtil.ToastShow(getBaseContext(),"再次输入的密码不一致",true);
                }else {

                    change();
                }
            }
        });
    }

    private void change() {
        o=new OptsharepreInterface(this);
        String token=o.getPres("token");
        RequestQueue queue = Volley.newRequestQueue(this);
        IStringRequest requset = new IStringRequest(Request.Method.GET,
             Constants.SERVER_ADDRESS+"userinfo/editpwd?oldPwd="+s1+"&newPwd="+s2+"&dupNewPwd="+s3+"&token="+token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa",response);

                        Map<String, Object> object = null;
                        try {
                            object=JsonUtils.getMapObj(response);
                            if (object.get("success").equals("1")){
                                o.putPres("loginFlag","false");
                                ToastUtil.ToastShow(getBaseContext(),"密码修改成功您需要重新登录",true);
                                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(intent);
                            }else {
                                ToastUtil.ToastShow(getBaseContext(),"服务器未知异常",true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.ToastShow(getBaseContext(),"服务器好像出了点错误" ,false);
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
       titleBarUtils.setTitle("修改密码");
       titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               finish();
           }

       });

   }
}
