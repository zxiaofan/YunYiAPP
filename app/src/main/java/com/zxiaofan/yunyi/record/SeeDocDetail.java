package com.zxiaofan.yunyi.record;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
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
 * Describe:     就诊明细
 * User:         xiaofan
 * Date:         2016/3/25 17:53
 */
public class SeeDocDetail extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_fyd)
    TextView tvFyd;
    @Bind(R.id.tv_cfd)
    TextView tvCfd;
    @Bind(R.id.tv_jyd)
    TextView tvJyd;
    @Bind(R.id.tv_jcd)
    TextView tvJcd;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_dzbl)
    TextView tvDzbl;
    @Bind(R.id.tv_Fjyd)
    TextView tvFjyd;
    @Bind(R.id.tv_Fjcd)
    TextView tvFjcd;
    @Bind(R.id.tv_Fcfd)
    TextView tvFcfd;
    @Bind(R.id.ll_fragment)
    LinearLayout llFragment;
    private FragmentManager mManager;
    //    private  FragmentTransaction trans;
    SerializableMap tmpmap,transMap;
    TextView tv_title;

    private Dialog pb;
    private OptsharepreInterface share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_doc_detail);
        ButterKnife.bind(this);
        mManager = getSupportFragmentManager();
        share = new OptsharepreInterface(this);
        tmpmap = (SerializableMap) getIntent().getSerializableExtra("index");
        transMap=new SerializableMap();
        init();
    }

    private void init() {
        initTitle();
        initWidget();
        initNetData();
        initData();
    }

    private void initNetData() {
        pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
        pb.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.e(Constants.TAG, share.getPres("token"));
        String url = Constants.SERVER_ADDRESS+"medicalRecordsDetail/patient-" + tmpmap.getMap().get("id").toString() + "?token=" + share.getPres("token");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(SeeDocDetail.this, "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    private void loadData(String json) {
        Map<String, Object> object = null;
        String success = "";
        try {
            object = JsonUtils.getMapObj(json);
            success = object.get("success").toString();
            if (success.equals("0")) {
                ToastUtil.ToastShow(this, object.get("msg").toString(), true);
            } else if (success.equals("1")) {
                transMap.setMap(JsonUtils.getMapObj(object.get("data").toString()));
                initFragment();
            } else {
                ToastUtil.ToastShow(this, "登录过期", true);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {

        }
    }

    private void initData() {
        tv_title= (TextView) findViewById(R.id.tv_titles);
        tv_title.setText(tmpmap.getMap().get("diag").toString());
        String content = tmpmap.getMap().get("hospitalName").toString() + "  " + tmpmap.getMap().get("departmentName").toString();
        tvContent.setText(content);
        tvDate.setText(DateUtil.formatedDateTime("yyyy-MM-dd",Long.parseLong(tmpmap.getMap().get("diagDate").toString())));
        //检查单
        if ((int) tmpmap.getMap().get("ckReportCount") == 0) {
            tvJcd.setVisibility(View.GONE);
        } else {
            tvJcd.setText("检查单" + tmpmap.getMap().get("ckReportCount").toString());
        }
        //检验单
        if ((int) tmpmap.getMap().get("ttTestCount") == 0) {
            tvJyd.setVisibility(View.GONE);
        } else {
            tvJyd.setText("检验单" + tmpmap.getMap().get("ttTestCount").toString());
        }
        //费用单
        if ((int) tmpmap.getMap().get("feeCount") == 0) {
            tvFyd.setVisibility(View.GONE);
        } else {
            tvFyd.setText("费用单" + tmpmap.getMap().get("feeCount").toString());
        }
        //处方单
        if ((int) tmpmap.getMap().get("ttPCount") == 0) {
            tvCfd.setVisibility(View.GONE);
        } else {
            tvCfd.setText("处方单" + tmpmap.getMap().get("ttPCount").toString());
        }
    }

    private void initFragment() {
        FragmentTransaction trans = mManager.beginTransaction();
        SeeDocDetailDzbl dzbl=new SeeDocDetailDzbl();
        Bundle bundle=new Bundle();
        bundle.putSerializable("model",transMap);
        dzbl.setArguments(bundle);
        trans.add(R.id.ll_fragment, dzbl);
        trans.commit();
        //设置选中状态
        tvDzbl.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvDzbl.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
    }

    private void seleFragment(int id) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putSerializable("model",transMap);
        switch (id) {
            case 0:
                SeeDocDetailDzbl dzbl=new SeeDocDetailDzbl();
                dzbl.setArguments(bundle);
                trans.replace(R.id.ll_fragment, dzbl);
                break;
            case 1:
                SeeDocDetailJyd jyd=new SeeDocDetailJyd();
                jyd.setArguments(bundle);
                trans.replace(R.id.ll_fragment, jyd);
                break;
            case 2:
                SeeDocDetailJcd jcd= new SeeDocDetailJcd();
                jcd.setArguments(bundle);
                trans.replace(R.id.ll_fragment,jcd);
                break;
            case 3:
                SeeDocDetailCfd cfd=new SeeDocDetailCfd();
                cfd.setArguments(bundle);
                trans.replace(R.id.ll_fragment, cfd);
                break;
        }
        trans.addToBackStack(null);
        trans.commit();
    }

    private void initWidget() {
        tvDzbl.setOnClickListener(this);
        tvFjyd.setOnClickListener(this);
        tvFjcd.setOnClickListener(this);
        tvFcfd.setOnClickListener(this);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("就诊明细");
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

    @Override
    public void onClick(View v) {
        tvDzbl.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvFjyd.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvFjcd.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvFcfd.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvDzbl.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tvFjyd.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tvFjcd.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        tvFcfd.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        switch (v.getId()) {
            case R.id.tv_dzbl://电子病历
                seleFragment(0);
                break;
            case R.id.tv_Fjyd://检验单
                seleFragment(1);
                break;
            case R.id.tv_Fjcd://检查单
                seleFragment(2);
                break;
            case R.id.tv_Fcfd://处方单
                seleFragment(3);
                break;
        }
        if (v instanceof TextView) {
            ((TextView) v).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        }
    }
}
