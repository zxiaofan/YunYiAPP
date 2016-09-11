package com.zxiaofan.yunyi.registered;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;

import java.util.Map;

import base.BaseActivity;
import base.OptsharepreInterface;
import util.Constants;
import util.DateUtil;
import util.JsonUtils;
import util.SerializableMap;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

public class RegisteredSuccess extends BaseActivity implements View.OnClickListener {

    TextView tvHospital;
    TextView tvDate;
    TextView tvDoc;
    TextView tvNameAge;
    TextView tvDealState;
    Button btnDealDetail;
    Button btnBackNum;

    SerializableMap transMap=new SerializableMap();
    private Dialog pb;
    private OptsharepreInterface share;
    RequestQueue queue;
    Map<String, Object> object = null;

    private TextView registerPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_success);
        transMap= (SerializableMap) getIntent().getSerializableExtra("order");
        share=new OptsharepreInterface(this);
        init();
    }

    private void init() {
        initTitle();
        initListener();
        initData();
    }

    /**
     * Describe:     根据订单id获取预约信息
     * User:         xiaofan
     * Date:         2016/4/8 10:19
     * id:           transMap.getMap().get("data").toString()
     */
    private void initData() {
        pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        pb.show();
        queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "appoinment/id-"+transMap.getMap().get("data").toString()+"?token=" + share.getPres("token");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadRegisteredData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(RegisteredSuccess.this, Constants.VOLLEY_ERROR, true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     格式化预约信息
     * User:         xiaofan
     * Date:         2016/4/8 10:32
     */
    private void loadRegisteredData(String json){
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                object=JsonUtils.getMapObj(object.get("data").toString());
                setData();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {}
    }

    String department,appointName,state;
    /**
    * Describe:     订单信息填充
    * User:         xiaofan
    * Date:         2016/4/8 11:25
    */
    private void setData() {
        //预约单号
        if (TextUtils.isEmpty(object.get("appointmentNo").toString())) {
            registerPwd.setText("无");
        } else {
            registerPwd.setText(object.get("appointmentNo").toString());
        }
        //医院
        if (TextUtils.isEmpty(object.get("hosOrgName").toString())) {
            department="无";
        } else {
            department=object.get("hosOrgName").toString();
        }
        //科室
        if (TextUtils.isEmpty(object.get("deptName").toString())) {
            department+="       无";
        } else {
            department+="       "+object.get("deptName").toString();
        }
        tvHospital.setText(department);

        //日期
        if (TextUtils.isEmpty(object.get("appointDate").toString())) {
            tvDate.setText("无");
        } else {
            tvDate.setText(DateUtil.formatedDateTime("yyyy-MM-dd", Long.parseLong(object.get("appointDate").toString())));
        }

        //专家名
        if (TextUtils.isEmpty(object.get("expName").toString())) {
            tvDoc.setText("无");
        } else {
            tvDoc.setText(object.get("expName").toString());
        }
        //就诊人姓名
        if (TextUtils.isEmpty(object.get("patientname").toString())) {
            appointName="无";
        } else {
            appointName=object.get("patientname").toString();
        }
        //就诊人性别
        if (!TextUtils.isEmpty(object.get("gender").toString())) {
            appointName+="      "+object.get("gender").toString();
        }
        tvNameAge.setText(appointName);

        //预约状态  0:已结束   1:预约成功  5:已付款   9:已退号
        if (TextUtils.isEmpty(object.get("state").toString())) {
            state = "无";
            btnBackNum.setVisibility(View.GONE);
        } else {
            switch (object.get("state").toString()) {
                case "0":
                    state = "已结束";
                    btnBackNum.setVisibility(View.GONE);
                    break;
                case "1":
                    state = "预约成功";
                    btnBackNum.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    state = "已付款";
                    btnBackNum.setVisibility(View.VISIBLE);
                    break;
                case "9":
                    state = "已退号";
                    btnBackNum.setVisibility(View.GONE);
                    break;
                default:
                    state = "未知";
                    btnBackNum.setVisibility(View.GONE);
                    break;
            }
        }
        tvDealState.setText(state);
    }

    private void initListener() {
        registerPwd= (TextView) findViewById(R.id.register_pwd);
        tvHospital= (TextView) findViewById(R.id.tv_hospital);
        tvDate= (TextView) findViewById(R.id.tv_date);
        tvDoc= (TextView) findViewById(R.id.tv_doc);
        tvNameAge= (TextView) findViewById(R.id.tv_name_age);
        tvDealState= (TextView) findViewById(R.id.tv_deal_state);
        btnDealDetail= (Button) findViewById(R.id.btn_deal_detail);
        btnBackNum= (Button) findViewById(R.id.btn_back_num);

        btnDealDetail.setOnClickListener(this);
        btnBackNum.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btn_deal_detail:
                Intent intent = new Intent(RegisteredSuccess.this, DealDetail.class);
                transMap.setMap(object);
                intent.putExtra("appointmodel", transMap);
                startActivity(intent);
                break;
            case R.id.btn_back_num:
                backupNum(object.get("id").toString(),object.get("state").toString());
                break;
        }
    }

    /**
     * Describe:     退号
     * User:         xiaofan
     * Date:         2016/4/8 15:15
     */
    private void backupNum(String id,String state){
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        }
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SERVER_ADDRESS + "appoinment/back?id=" + id+ "&state=" + state + "&token=" + share.getPres("token");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadBackupData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(RegisteredSuccess.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }
    /**
     * Describe:     获取退号数据
     * User:         xiaofan
     * Date:         2016/4/8 15:34
     */
    private void loadBackupData(String json){
        try {
            object = JsonUtils.getMapObj(json);
            if (object.get("success").toString().equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (object.get("success").toString().equals("1")) {
                ToastUtil.ToastShow(this, "已退号", true);
                finish();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
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
