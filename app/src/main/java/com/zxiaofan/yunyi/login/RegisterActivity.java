package com.zxiaofan.yunyi.login;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;
import com.zxiaofan.yunyi.User.PersonalDetail;
import com.zxiaofan.yunyi.model.UserInfoBo;

import java.util.Map;
import base.BaseActivity;
import base.OptsharepreInterface;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import util.ValidateUtil;
import widget.ProgressDialogStyle;

/**
* Describe:     注册页
* User:         xiaofan
* Date:         2016/3/18 14:20
*/
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_account,et_pwd,et_yzm,et_pwd_sure;
    private Button btn_yzm,btn_register;

    private TextView tv_pwd_diff_error;

    private String account,yzm,pwd,pwd_sure;
    private Dialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        initTitle();
        initWidget();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("注册");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWidget() {
        et_account = (EditText) findViewById(R.id.et_account);
//        et_yzm= (EditText) findViewById(R.id.et_yzm);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd_sure= (EditText) findViewById(R.id.et_pwd_sure);
//        btn_yzm = (Button) findViewById(R.id.btn_yzm);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_pwd_diff_error= (TextView) findViewById(R.id.tv_pwd_diff_error);
//        btn_yzm.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        //设置弹出数字键
//        et_account.setInputType(EditorInfo.TYPE_CLASS_PHONE);
//        et_yzm.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //判断密码是否相同
        et_pwd_sure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!et_pwd.getText().toString().trim().equals(et_pwd_sure.getText().toString().trim())){
                        tv_pwd_diff_error.setVisibility(View.VISIBLE);
                        btn_register.setClickable(false);
                    }else{
                        tv_pwd_diff_error.setVisibility(View.GONE);
                        btn_register.setClickable(true);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_register:
                account=et_account.getText().toString().trim();
                o=new OptsharepreInterface(this);
                o.putPres("phonenumber",account);
                vaidate();
                break;
//            case R.id.btn_yzm:
//
//                break;
            default:
                break;
        }
    }

    /**
    * Describe:     验证输入是否正确
    * User:         xiaofan
    * Date:         2016/3/18 14:13
    */
    private void vaidate() {
        boolean validateRule=false;
        String errorMsg="";

//        yzm=et_yzm.getText().toString().trim();
        pwd=et_pwd.getText().toString().trim();
        pwd_sure=et_pwd_sure.getText().toString().trim();
//        if(!ValidateUtil.isMobileNO(account)){
//            errorMsg="手机格式不正确";
//        }else{
//            validateRule=true;
//        }
        validateRule=true;
        if(validateRule){
            if(!ValidateUtil.checkPassWord(pwd)){
                errorMsg="密码5-15位字母与数字组成或出现非法字符";
                validateRule=false;
            }
        }
        if(validateRule){
            register();
        }else{
            ToastUtil.ToastShow(this,errorMsg,true);
        }
    }

    /**
    * Describe:     注册
    * User:         xiaofan
    * Date:         2016/3/18 14:19
    */
    private void register() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        IStringRequest requset = new IStringRequest(Request.Method.GET,
//                Constants.SERVER_ADDRESS+ "register?phone="+account+"&password="+pwd_sure+"&verifyCode="+yzm,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i("aaa",response);
//                        parseRegister(response);
//                       // ToastUtil.ToastShow(getBaseContext(), "注册成功", true);
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("aaa",error.toString());
//
//                    }
//                }
//        );
//        queue.add(requset);
        pb = ProgressDialogStyle.createLoadingDialog(this, "正在注册...");
        pb.show();
        ServiceAPI api=new ServiceAPI();
        UserInfoBo bo=new UserInfoBo();
        bo.setUserName(account);
        bo.setPassWord(pwd);
        String param=new Gson().toJson(bo);
        pb.dismiss();
        api.register(param,RegisterActivity.this);
    }

    private void parseRegister(String response) {
        Map<String,Object> object=null;
        Map<String,Object> data=null;
        try {
            object= JsonUtils.getMapObj(response);
            String success=object.get("success").toString();

            if (success.equals("0")){
                String msg = object.get("msg").toString();
                ToastUtil.ToastShow(getBaseContext(),msg, true);
            }else if(success.equals("1")){
                data=JsonUtils.getMapObj(object.get("data").toString());
                String token=data.get("token").toString();
                o=new OptsharepreInterface(this);
                o.putPres("token",token);
                MyApplication.loginFlag=true;
                ToastUtil.ToastShow(getBaseContext(),"注册成功", true);
                MyApplication.phone=account;
                Intent intent=new Intent(this, PersonalDetail.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
