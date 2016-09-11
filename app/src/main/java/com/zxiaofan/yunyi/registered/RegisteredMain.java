package com.zxiaofan.yunyi.registered;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.MyUserActivity;
import com.zxiaofan.yunyi.activity.FindHospitalActivity;
import com.zxiaofan.yunyi.activity.TimeSecActivity;
import com.zxiaofan.yunyi.login.LoginActivity;
import com.zxiaofan.yunyi.xzqh.NewProcinceActivity;

import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.DateUtil;
import util.JsonUtils;
import util.SerializableMap;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Describe:     预约挂号首页
 * User:         xiaofan
 * Date:         2016/3/23 15:27
 */
public class RegisteredMain extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.min_hospital)
    RelativeLayout minHospital;
    @Bind(R.id.tv_sjwk)
    TextView tvSjwk;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.min_date)
    RelativeLayout minDate;
    @Bind(R.id.tv_ptmz)
    TextView tvPtmz;
    @Bind(R.id.min_ptmz)
    RelativeLayout minPtmz;

    private RelativeLayout rl_wdjzr, rl_history, min_sjwk, min_city;
    private Button btn_submit;

    String hosId,hosname;
    private Map<String, Object> obj,arrayJob,appoint;
    private Dialog pb;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initTitle();
        initWidget();
        initData();

    }
    /**
    * Describe:     获取默认医院
    * User:         xiaofan
    * Date:         2016/4/12 16:07
    */
    private void initData() {
        pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "arrayJob/default";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(RegisteredMain.this, "等待医院接口授权ing...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }
    /**
     * Describe:     格式化数据
     * User:         xiaofan
     * Date:         2016/4/7 10:28
     */
    private void loadData(String json) {
        try {
            obj = JsonUtils.getMapObj(json);
            if (obj.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, obj.get("msg").toString(), true);
            } else if (obj.get("success").toString().equals("1")) {
                obj = JsonUtils.getMapObj(obj.get("data").toString());
                if(TextUtils.isEmpty(obj.get("arrayJob").toString())){
                    btn_submit.setEnabled(false);
                }else{
                    arrayJob = JsonUtils.getMapObj(obj.get("arrayJob").toString());
                }
                appoint= JsonUtils.getMapObj(obj.get("appoint").toString());
                formatData();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
        }
    }

    private void formatData() {
        if(TextUtils.isEmpty(appoint.get("zonename").toString())){//地区
            tvLoc.setText("无");
        }else{
            tvLoc.setText(appoint.get("zonename").toString());
        }
        if(TextUtils.isEmpty(appoint.get("orgName").toString())){//医院
            tvHospital.setText("无");
        }else{
            tvHospital.setText(appoint.get("orgName").toString());
        }
        if(TextUtils.isEmpty(appoint.get("deptName").toString())){//科室
            tvSjwk.setText("无");
        }else{
            tvSjwk.setText(appoint.get("deptName").toString());
        }
        String date;
        if(TextUtils.isEmpty(appoint.get("appDate").toString())){//时间
            date="无";
        }else{
            date= DateUtil.formatedDateTime("yyyy年MM月dd日",Long.parseLong(appoint.get("appDate").toString()));
        }
        if(!TextUtils.isEmpty(appoint.get("periodName").toString())){//上午下午或晚上
            date+="     "+appoint.get("periodName").toString();
        }
        tvDate.setText(date);
        if(TextUtils.isEmpty(appoint.get("clinicName").toString())){//门诊
            tvPtmz.setText("无");
        }else{
            tvPtmz.setText(appoint.get("clinicName").toString());
        }
    }

    private void initWidget() {
        rl_wdjzr = (RelativeLayout) findViewById(R.id.rl_wdjzr);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rl_history = (RelativeLayout) findViewById(R.id.rl_history);
        min_sjwk = (RelativeLayout) findViewById(R.id.min_sjwk);
        min_city = (RelativeLayout) findViewById(R.id.min_city);

        min_sjwk.setOnClickListener(this);
        rl_history.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rl_wdjzr.setOnClickListener(this);
        min_city.setOnClickListener(this);
        minDate.setOnClickListener(this);
        minPtmz.setOnClickListener(this);
        minHospital.setOnClickListener(this);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("预约挂号");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_wdjzr://我的就诊人
                if(app.loginFlag){
                    intent = new Intent(this, MyUserActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }else{
                    ToastUtil.ToastShow(this,"请先登录",false);
                    jumpLogin();
                }
                break;
            case R.id.btn_submit://预约详情
                if(app.loginFlag){
                    if(arrayJob==null){
                        ToastUtil.ToastShow(this,"不能逾越或预约已满",true);
                    }else{
                        SerializableMap arrayJobMap=new SerializableMap();
                        arrayJobMap.setMap(arrayJob);
                        Intent intent1 = new Intent(this, RegisteredDetail.class);
                        intent1.putExtra("arrayJob",arrayJobMap);
                        startActivity(intent1);
                    }
                }else{
                    ToastUtil.ToastShow(this,"请先登录",false);
                    jumpLogin();
                }
                break;
            case R.id.rl_history://我的预约
                if(app.loginFlag){
                    Intent intent2 = new Intent(this, RegisteredHistory.class);
                    startActivity(intent2);
                }else{
                    ToastUtil.ToastShow(this,"请先登录",false);
                    jumpLogin();
                }
                break;
            case R.id.min_sjwk://科室选择
                intent = new Intent(this, DepartmentRegistered.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                startActivity(intent);
                break;
            case R.id.min_city://地区选择
                intent = new Intent(this, NewProcinceActivity.class);
                startActivity(intent);
                break;
            case R.id.min_date://时间选择
                Intent intent5 = new Intent(this, TimeSecActivity.class);
                startActivityForResult(intent5, 5);
                break;
            case R.id.min_ptmz://普通门诊
                Intent intent6 = new Intent(this, TimeSecActivity.class);
                startActivityForResult(intent6, 6);
                break;
            case R.id.min_hospital://医院选择
                intent =new Intent(this,FindHospitalActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void jumpLogin(){
        ToastUtil.ToastShow(this, "请先登录", true);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4 && resultCode == RESULT_OK) {
            String xzqh = data.getStringExtra(Constants.XZQH_CODE);
            tvLoc.setText(xzqh);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
