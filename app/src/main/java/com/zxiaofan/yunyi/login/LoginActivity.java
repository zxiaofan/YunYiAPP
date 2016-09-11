package com.zxiaofan.yunyi.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;
import com.zxiaofan.yunyi.activity.MainActivity;

import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import util.ValidateUtil;
import widget.ProgressDialogStyle;

/**
 * Describe:     登录页
 * User:         xiaofan
 * Date:         2016/3/29 14:20
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_account, et_pwd;
    private Button btn_submit, btn_register;
    private Dialog pb;

    private String account, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        initTitle();
        initWidget();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("登录");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWidget() {
        et_account = (EditText) findViewById(R.id.et_account);
//        et_account.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_submit.getBackground().setAlpha(100);//0~255透明度值
        btn_submit.setEnabled(false);
        btn_submit.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        et_account.addTextChangedListener(new TextChange());
        et_pwd.addTextChangedListener(new TextChange());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                validation();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void validation() {
        account = et_account.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();


        if (TextUtils.isEmpty(account)) {
            ToastUtil.ToastShow(this, "请输入用户名", true);
        }
//        else if (!ValidateUtil.isMobileNO(account)) {
//            loginMethod();
//            // ToastUtil.ToastShow(this,"手机格式不正确",true);
//        }
        else {
            if (TextUtils.isEmpty(account)) {
                ToastUtil.ToastShow(this, "请输入密码", true);
            } else if (!ValidateUtil.checkPassWord(pwd)) {
                ToastUtil.ToastShow(this, "密码5-15位或出现非法字符", true);
            } else {
                loginMethod();
            }
        }
    }

    private void loginMethod() {
//        pb = ProgressDialogStyle.createLoadingDialog(this, "正在登录...");
//        pb.show();
//        RequestQueue queue = Volley.newRequestQueue(this);
        ServiceAPI api = new ServiceAPI();
         api.login(account, pwd,LoginActivity.this);
//        IStringRequest requset = new IStringRequest(Request.Method.GET,
//                Constants.SERVER_ADDRESS+ "login?username="+account+"&password="+pwd,
//                new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("aaa",response);
//                parseLogin(response);
//                //
//                pb.dismiss();
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        ToastUtil.ToastShow(getBaseContext(),"服务器好像出了点问题",true);
//                     pb.dismiss();
//                    }
//                }
//        );
//        if (result){
//            ToastUtil.ToastShow(getBaseContext(),"登陆成功",true);
//        }else {
//            ToastUtil.ToastShow(getBaseContext(),"登录失败",true);
//        }
//        queue.add(requset);
    }

    private void parseLogin(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        String token = "";
        String msg = "";
        String success = "";
        try {
            object = JsonUtils.getMapObj(response);
            success = object.get("success").toString();
            if (success.equals("0")) {
                ToastUtil.ToastShow(getBaseContext(), "用户手机号或密码错误", true);
            } else {
                data = JsonUtils.getMapObj(object.get("data").toString());
                Log.i("aaa", success);
                token = data.get("token").toString();
                String userId = data.get("userId").toString();
                OptsharepreInterface o = new OptsharepreInterface(this);
                o.putPres("token", token);
                o.putPres("account", account);
                o.putPres("password", pwd);
                o.putPres("guid", userId);
                MyApplication.loginFlag = true;
                o.putPres("loginFlag", "true");
                MyApplication.token = token;
                msg = object.get("msg").toString();
                MyApplication.phone = account;
                ToastUtil.ToastShow(getBaseContext(), msg, false);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("postion", 0);
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

    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (et_account.getText().toString().length() <= 0 || et_pwd.getText().toString().length() <= 5) {
                btn_submit.getBackground().setAlpha(100);//0~255透明度值
                btn_submit.setEnabled(false);

            } else {
                btn_submit.getBackground().setAlpha(255);
                btn_submit.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


}
