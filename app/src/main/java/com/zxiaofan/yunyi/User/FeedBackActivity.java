package com.zxiaofan.yunyi.User;

/*
Build.MODEL，得到手机型号，比如我的就是ME865

Build.VERSION.RELEASE，得到系统版本号，比我我的4.0.4

getPackageManager().getPackageInfo(getPackageName(),0).versionName，得到应用程序的当前版本号

 */

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;

import java.util.HashMap;
import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.TitleBarUtils;
import util.ToastUtil;

public class FeedBackActivity extends BaseActivity implements TextWatcher {

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.ed1)
    EditText ed1;
    @Bind(R.id.submit_a)
    Button submitA;
    private Map<String,String> params;
    private StringBuilder adv;
    private String advice="";
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_feed_back);
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
        ed1.addTextChangedListener(this);
        submitA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Model = Build.MODEL;
                String system = Build.VERSION.RELEASE;
                 advice=ed1.getText().toString().trim();
                 adv = new StringBuilder();
                adv.append(Model);
                adv.append(system);
                adv.append(advice);

                o = new OptsharepreInterface(getBaseContext());
                final String token = o.getPres("token");
//                if(pb==null){
//                    pb = ProgressDialogStyle.createLoadingDialog(getBaseContext(), "请求中...");
//                    pb.show();
//                }
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                IStringRequest requset = new IStringRequest(Request.Method.POST,
                        Constants.SERVER_ADDRESS+"useropinion/add?",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               // pb.dismiss();
                                ToastUtil.ToastShow(getBaseContext(),"意见反馈成功",true);
                                finish();
                               // parseuser(response);


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            ///   pb.dismiss();
                               ToastUtil.ToastShow(getBaseContext(),"开发中，敬请期待 . . .",true);

                            }
                        }
                ){
                @Override
                protected Map<String, String> getParams() {
                    //在这里设置需要post的参数
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("info", adv.toString());
                    map.put("token", token);
                    return map;
                }
            };



            queue.add(requset);

            }
        });


    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("意见反馈");


        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (ed1.getText().length()<=9){

            submitA.setEnabled(false);
            submitA.getBackground().setAlpha(100);//0~255透明度值


        }else {
            submitA.setEnabled(true);
            submitA.getBackground().setAlpha(255);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
