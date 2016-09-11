package com.zxiaofan.yunyi.registered;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.DateUtil;
import util.SerializableMap;
import util.TitleBarUtils;

/**
 * Describe:     订单详情--预约单
 * User:         xiaofan
 * Date:         2016/4/5 14:39
 */
public class DealDetail extends BaseActivity {

    @Bind(R.id.appointmentNo)
    TextView appointmentNo;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_doc_date)
    TextView tvDocDate;
    @Bind(R.id.tv_outpatient_type)
    TextView tvOutpatientType;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_registered_name)
    TextView tvRegisteredName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_idcard)
    TextView tvIdcard;
    @Bind(R.id.tv_deal_date)
    TextView tvDealDate;

    private SerializableMap transMap;
    private Map<String, Object> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);
        ButterKnife.bind(this);
        transMap = (SerializableMap) getIntent().getSerializableExtra("appointmodel");
        object = transMap.getMap();
        init();
    }


    private void init() {
        initTitle();
        initData();
    }

    String appointDate="";
    private void initData() {
        //预约单号
        if (TextUtils.isEmpty(object.get("appointmentNo").toString())) {
            appointmentNo.setText("无");
        } else {
            appointmentNo.setText(object.get("appointmentNo").toString());
        }
        //医院
        if (TextUtils.isEmpty(object.get("hosOrgName").toString())) {
            tvHospital.setText("无");
        } else {
            tvHospital.setText(object.get("hosOrgName").toString());
        }
        //科室
        if (TextUtils.isEmpty(object.get("deptName").toString())) {
            tvDepartment.setText("无");
        } else {
            tvDepartment.setText(object.get("deptName").toString());
        }

        //日期
        if (TextUtils.isEmpty(object.get("appointDate").toString())) {
            appointDate="无";
        } else {
            appointDate= DateUtil.formatedDateTime("yyyy-MM-dd", Long.parseLong(object.get("appointDate").toString()));
        }
        //日期
        if (!TextUtils.isEmpty(object.get("appointDate").toString())) {
            appointDate+="      "+DateUtil.DateToWeek(Long.parseLong(object.get("appointDate").toString()));
        }
        tvDocDate.setText(appointDate);

        //专家名
        if (TextUtils.isEmpty(object.get("expName").toString())) {
            tvOutpatientType.setText("无");
        } else {
            tvOutpatientType.setText(object.get("expName").toString());
        }
        //挂号费用
        if (TextUtils.isEmpty(object.get("reFee").toString())) {
            tvPrice.setText("无");
        } else {
            tvPrice.setText("￥"+object.get("reFee").toString()+"元");
        }
        //就诊人姓名
        if (TextUtils.isEmpty(object.get("patientname").toString())) {
            tvRegisteredName.setText("无");
        } else {
            tvRegisteredName.setText(object.get("patientname").toString());
        }
        //就诊人性别
        if (TextUtils.isEmpty(object.get("gender").toString())) {
            tvSex.setText("无");
        } else {
            tvSex.setText(object.get("gender").toString());
        }
        //就诊人手机号
        if (TextUtils.isEmpty(object.get("phone").toString())) {
            tvPhone.setText("无");
        } else {
            tvPhone.setText(object.get("phone").toString());
        }
        //就诊人身份证号
        if (TextUtils.isEmpty(object.get("idNo").toString())) {
            tvIdcard.setText("无");
        } else {
            tvIdcard.setText(object.get("idNo").toString());
        }

        //订单创建日期
        if (TextUtils.isEmpty(object.get("createtime").toString())) {
            tvDealDate.setText("无");
        } else {
            tvDealDate.setText( DateUtil.formatedDateTime("yyyy/MM/dd hh:mm", Long.parseLong(object.get("createtime").toString())));
        }

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("预约单");
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

    }

    @Override
    public void initAction() {

    }

}
